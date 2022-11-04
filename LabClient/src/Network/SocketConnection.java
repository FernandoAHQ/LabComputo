package Network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

import GUI.Bloqueo;
import GUI.TimerGUI;
import Packages.ClientRequest;
import Packages.ReconnectionData;
import Packages.ServerResponse;

public class SocketConnection implements Runnable {

	Bloqueo parent;
	String ip;
	String pc;
	int port;

	int ping = 0;

	Socket socket = null;
	Socket pingSocket;

	ObjectOutputStream out;
	ObjectInputStream in;

	LoginPackage loginData;
	TimerGUI timer;

	Thread pingerThread;
	
	private String currentUser = null;

	boolean isConnected = false;
	boolean isLogged;
	boolean pcAuth;
	boolean pingComplete = true;
	ServerResponse pingResponse;
	ServerResponse response;

	public SocketConnection(Bloqueo parent, String ip, int port, String pc) {
		this.parent = parent;
		this.ip = ip;
		this.port = port;
		this.pc = pc;

		establishConnection();


	}

	@Override
	public void run() {

	
		pingerThread = new Thread(new Runnable() {
			@Override
			public void run() {
				ping();
			}
		});
		pingerThread.start();

		while (isConnected) {

			try {
				response = (ServerResponse) in.readObject();

				switch (response.getRequestType()) {
				case ServerResponse.PING: {
					if (response.getConfirmation()) {
				//		log("PING CONFIRMED");
					} else {
						closeSession();
					}
					pingComplete = true;

					break;
				}

				case ServerResponse.LOGIN: {
					boolean l = response.getConfirmation();
					parent.login(l);
					System.out.println("SocketConnection - Login Response: " + l);
					isLogged = l;
		
					break;
				}

				case ServerResponse.INIT: {
					System.out.println("SocketConnection - Recieving initialization Answer");
					parent.startClock(response.getRequestAnswer());
					break;
				}
				}

			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
				isConnected = false;
				parent.setDisconnected();
				System.out.println("CONNECTION CUT - RESTARTING FROM SOCKET");
				closeConnection();
				establishConnection();
			}
		}

	}

	private void ping() {
		ping++;
		while (true) {
		//	System.out.println("Entered Ping Thread");
			if(isConnected) {
			

				if (pingComplete) {
					pingComplete = false;
					try {
					//	log("PINGING");
						out.writeObject(new ClientRequest(1, true));
					//	out.flush();
						log("PINGED");
						
					} catch (IOException e) {
						log("CANT REACH SERVER");
						isConnected = false;
					}

					// log("HEREEEEEEEEEE");

				}
			}else {
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	private boolean pcAuth() {
		pcAuth = false;

		try {
			out.writeObject(pc);
			out.flush();
			pcAuth = (Boolean) in.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			log("PROBLEM AUTHING PC NUMBER");
		}

		return pcAuth;
	}

	public boolean isPinging() {
		return !pingComplete;
	}

	public void login(String user, String pass) {

		isLogged = false;
		ClientRequest loginRequest = new ClientRequest(ClientRequest.LOGIN, true, user, pass);
		ServerResponse loginResponse;

		try {
			out.writeObject(loginRequest);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public synchronized int requestStart(TimerGUI timer) {
		this.timer = timer;

		ServerResponse response = new ServerResponse(ServerResponse.INIT, -1);

		try {
			log("trying");
			response = (ServerResponse) in.readObject();
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		log("RETURN RESPONSE");
		// notifyAll();
		return response.getRequestAnswer();

	}

	public void requestClose() {
		try {
					log("REQUESTING CLOSE");
					out.writeObject(new ClientRequest(ClientRequest.CLOSE, true));
					out.flush();
			//		log("PINGED");

				} catch (IOException e) {
					log("CANT REACH SERVER");
					isConnected = false;
				}
	}
	
	private void closeSession() {
		log("closeSession()");
		log("CLOSED TIMER");
		
		
		
		parent.lockScreen();
	}

	public void passTimer(TimerGUI timer) {
		this.timer = timer;
	}

	public void requestTime(int request) {
		try {
			log("socket REQUESTING MORE TIME - REQUEST: " + request);
			out.writeObject(new ClientRequest(ClientRequest.EXTENSION, true, request));
			out.flush();
	//		log("PINGED");

		} catch (IOException e) {
			log("CANT REACH SERVER");
			isConnected = false;
		}
	}
	
	
	public void closeConnection() {
		socket = null;
	}
	
	public void establishConnection(){
		
		
		do {
			try {
				Thread.sleep(1000);
				socket = new Socket(ip, port);
				socket.getOutputStream().flush();
			} catch (IOException e) {
				// e.printStackTrace();
				log("PROBLEM CONNECTING THE SOCKET");
				try {
					log("RECONNECTING IN:");
					log("3");
					Thread.sleep(1000);
					log("2");
					Thread.sleep(1000);
					log("1");
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} while (socket == null);
		
		isConnected = true;
		System.out.println("SocketConnection - Connected to Server");

		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			System.out.println("SocketConnection - IO Streams established 1 RENEWED");

			in = new ObjectInputStream(socket.getInputStream());
			System.out.println("SocketConnection - IO Streams established 2 RENEWED");
			
			System.out.println("PC ID: " + pc);
			try {
				System.out.println("INTO THE TRY : " + pc);
				out.writeObject(pc);
				System.out.println("SENT PC : " + pc);
				if (!in.readBoolean()) {
					out.flush();
					in.close();
					out.close();
					
					socket.close();
					
					System.out.println("FAILED");
					JOptionPane.showMessageDialog(parent, "PROBLEMA CONECTANDO AL SERVIDOR: VERIFIQUE LA CONEXIÓN A LA RED Y EL ID DE LA MÁQUINA", "ERROR", JOptionPane.ERROR_MESSAGE);
					System.exit(0);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			log("NO ERROR");
			parent.setConnected();
			pingComplete=true;
		} catch (IOException e) {
			e.printStackTrace();
			log("PROBLEM WITH THE STREAMS");
		}
		
		if(parent.getSessionData().getServerState() != ReconnectionData.STATE_NONE) {
			try {
				out.writeObject(new ClientRequest(ClientRequest.RECONNECT, parent.getSessionData()));
				out.flush();
				System.out.println("RECONNECTED");
				isConnected = true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
	}
	
	public void logout() {
		isLogged = false;
	}
	
	private void log(String msg) {
		System.out.println(msg);
	}
}
