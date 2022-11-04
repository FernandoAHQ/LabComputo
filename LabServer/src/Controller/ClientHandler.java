package Controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.swing.JOptionPane;

import java.sql.Time;

import Model.DataModel;
import Model.Computer;
import Packages.ClientRequest;
import Packages.ReconnectionData;
import Packages.ServerResponse;
import View.PC_Panel;

public class ClientHandler implements Runnable {
	
	MainController parent;
	ServerSocket server;
	Socket client;
	ObjectOutputStream out;
	ObjectInputStream in;
	
	Time startTime;
	long date;

	int labId;
	String pc, user, name, client_ip;
	
	private int connectionIndex, pcIndex;
	
	private boolean isFreeSession = false;

	DataModel dbCon;

	ClientRequest request;

	private int state = Computer.AVAILABLE;
	private int time = 0, start = 0;

	boolean sessionEnded = false;
	
	boolean isAlive = true;
	int timeout = 0;
	int timeoutLimit = 3;
	
	boolean authed = false;

	public ClientHandler(
			MainController parent, ServerSocket server, Socket client, ObjectOutputStream out, 
			ObjectInputStream in, String pc, int connectioIndex, int pcIndex, int labId
			) {
		this.parent = parent;
		this.server = server;
		this.client = client;
		this.in = in;
		this.out = out;
		this.pc = pc;
		this.connectionIndex = connectionIndex;
		this.pcIndex = pcIndex;
		this.labId = labId;
		this.client_ip = client.getInetAddress().toString().substring(1);
		
		
		dbCon = new DataModel();
		
		new Thread() {
			public void run(){
			     while(isAlive) {
			    	 try {sleep(1000);} catch (InterruptedException e) {}
			    	 timeout++;
			    	 if(timeout == timeoutLimit) {
			    		 killHandler();
			    	 }
			     }
			    }
		}.start();
		
	}

	@Override
	public void run() {

		while (isAlive) {
			//READING PACKAGE
			try {
				request = (ClientRequest) in.readObject();
			} catch (IOException e) {
				System.out.println("CLIENT LOST WHILE READING REQUEST");
				killHandler();
				} catch (ClassNotFoundException e) {
					System.out.println("OBJECT SENT FROM CLIENT NOT IDENTIFIED");
					e.printStackTrace();
			}
			
			restartTimeout();
			
			//HANDLE THE DATA THAT WAS READ
			handleData();
			

		}

	}
	
	private void killHandler() {
		if(isAlive) {
			recordSession();	
			parent.getPcData(pcIndex).reset();
			try {client.close();} catch (IOException e) {e.printStackTrace();}
			isAlive = false;
		}
	}

	private void restartTimeout() {
		timeout = 0;
	}
	private void handleData() {
			switch (request.getType()) {
			case ClientRequest.PING: {
				ping();
				break;
			}
			case ClientRequest.EXTENSION: {
				parent.setRequest(pcIndex, request.getRequest());
				System.out.println("CH - REQUEST: " + request.getRequest());
				break;
			}
			case ClientRequest.LOGIN: {
				login(request);
	
				break;
			}
			case ClientRequest.CLOSE: {
				System.out.println("SESSION CLOSE REQUESTED TO SERVER");
				closeSession();
				break;
			}
			case ClientRequest.RECONNECT: {
				System.out.println("SESSION RECONNECT");
				resumeSession(request.getSession());
				break;
			}
	
			}
	}
	
	private void resumeSession(ReconnectionData session) {
		// TODO Auto-generated method stub
	
		user = session.getCurrentUser();
		if(user!=null) {
			authed = true;
			name = dbCon.getName(user);	
			System.out.println("=USER ID: " + user + "=");
		}
		
		client_ip = client.getInetAddress().toString().substring(1);
		time = session.getLastTime();
		date = LocalDateTime.now().toLocalTime().toSecondOfDay() - time;
		startTime = new Time(System.currentTimeMillis());
		
		start = (int) date - time;
		
		switch(session.getServerState()) {
		case ReconnectionData.STATE_NONE:{
			state = Computer.AVAILABLE;
			break;
		}
		case ReconnectionData.STATE_FREE:{
			isFreeSession = true;
			state = Computer.OCCUPIED;
			break;
		}
		case ReconnectionData.STATE_TIMED:{
			isFreeSession = false;
			state = Computer.OCCUPIED;
			break;
		}case ReconnectionData.STATE_AWAIT:{
			//IDK, YOU HANDLE THIS
			//SCREW U
		}
		
		}
		
	}

	private void ping() {
		try {// REDUCE TIME HERE?
			
			
			
			out.writeObject(new ServerResponse(ServerResponse.PING, !sessionEnded));
		//	System.out.println("PINGER NAME: "+ name + "CONTROL: " + user);
			out.flush();
			if(sessionEnded) {
		//		logout();
				sessionEnded = false;
			}
			if(time<=0 && state == Computer.OCCUPIED) {
				System.out.println("TIME <= 0: " + time);
				logout();
			}
			if(isFreeSession) time++;
			else if (time > 0) time -= 1;
			
		//	System.out.println("CLIENT PING");
		} catch (IOException e) {
			killHandler();
			System.out.println("LOST CLIENT");
			//state = Computer.DISCONNECTED;
			// e.printStackTrace();
		}
	}

	private void login(ClientRequest request) {
		// VALIDATE LOGIN INFO
		System.out.println("ClientHandler - login function");
		user = request.getUser();
		String pass = request.getPassword();
		

		authed = dbCon.auth(user, pass); // CALL DB BY REQUESTING IT TO MAIN CONTROLLER
		//System.out.println("ClientHandler - Login authed: " + authed);

		try {
			out.writeObject(new ServerResponse(ServerResponse.LOGIN, authed));
			out.flush();
			System.out.println("ClientHandler - Server response SENT");
		} catch (IOException e) {
			killHandler();//removeClient();
			e.printStackTrace();

		}

		if (authed) {
			// HERE WE ASK FOR PERMISSION TO START
			name = dbCon.getName(user);
			System.out.println("HANDLER NAME: "+ name + " CONTROL: " + user);
			parent.requestSession(pcIndex);
			

		}

	}
	
	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public void respondInit(int timeGrant) {

		if (timeGrant > 0) {
			if(time == 0 ) {
				date = LocalDateTime.now().toLocalTime().toSecondOfDay();
				startTime = new Time(System.currentTimeMillis());
				
				start = (int) date;
			}
			try {
				
				if(timeGrant == Integer.MAX_VALUE) {
					isFreeSession = true;
					time = 1;
				}else {
					isFreeSession = false;
					time = timeGrant;
				}
				
				
				//time = 10;
				out.writeObject(new ServerResponse(ServerResponse.INIT, timeGrant));
				out.flush();
				state = Computer.OCCUPIED;
				authed = true;
				System.out.println("Time in respondIniti: " + time);
			} catch (IOException e) {
				killHandler();//removeClient();
				e.printStackTrace();

			}

		} else {
			user = "";
			name = "QWERTTY";
			time = 0;
			authed = false;
			try {
				
				out.writeObject(new ServerResponse(ServerResponse.INIT, 0));
				out.flush();
				System.out.println("ClientHandler - Server response SENT");
			} catch (IOException e) {
				killHandler();//removeClient();
				e.printStackTrace();

			}

		}
	}
	
	public void removeClient() {
	
	
		closeSession();	
		//isConnected = false;
		client = null;
		state = Computer.DISCONNECTED;
		System.out.println("REMOVED CLIENT: " + pc);

	}
	
	
	private void logout() {
		//ads
		this.client_ip = client.getInetAddress().toString().substring(1);
		recordSession();
		System.out.println("LOGOUT FUNCTION");
		user = null;
		time = 0;
		start = 0;
		name = "";
		authed = false;
		client_ip = null;
		
		isFreeSession = false;
		state = Computer.AVAILABLE;
		parent.setTimeRequest(pcIndex, false);
		parent.setSessionRequest(pcIndex, false);
	}
	
	private void recordSession() {
		if(time > 0 || state == Computer.OCCUPIED) dbCon.registrarSesion(startTime, labId, pc, client_ip, user);
	}
	
	
	public void closeSession() {
		logout();
		sessionEnded = true;
	}
	
	private void setOccupied() {
		state = Computer.OCCUPIED;
	}

	public int getState() {
		return state;
	}

	public String getPc() {
		return pc;
	}

	public void setPc(String pc) {
		this.pc = pc;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public boolean isAuthed() {
		return authed;
	}

	public void setAuthed(boolean authed) {
		this.authed = authed;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getPcIndex() {
		return pcIndex;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isNull() {
		return client == null;
	}


}
