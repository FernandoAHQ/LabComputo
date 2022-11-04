package Controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.swing.JOptionPane;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import Model.DataModel;
import Model.Computer;
import Model.ComputerModel;
import Model.ConfigModel;
import View.Window;
import View.LoadingScreen;
import View.PC_Panel;

public class MainController {
	
	DataModel databaseModel;
	
	
	
	String serverName = "Laboratorio Computo";
	
	int pcsAdded = 0;

	public ConfigModel conf;
	
	
	
	ViewController view;
	ComputerModel computerData;

	LoadingScreen loadingScreen;
	
	Thread panelTickerThread;
	GUI_Ticker panelTicker;

	ConnectionHandler connectionHandler;
	//ServerSocket server;

	ArrayList<ClientHandler> clients;

	@SuppressWarnings("unchecked")
	public MainController() {
		loadingScreen = new LoadingScreen();
		databaseModel = new DataModel();
		conf = new ConfigModel();
		
		
		
		
		Time start = new Time(0), end = new Time(0);
		Date date = new Date(2015-03-31);
		date.setYear(2022);
		date.setYear(date.getYear()-1900);
		date.setMonth(2);
		date.setDate(1);
		int labId = 0;
		String pc = "0001";
		String client_ip = "";
		int user = 1;

		String[] pcs = {
		                "0001",
		                "0002",
		                "0003",
		                "0004",
		                "0005",
		                "0006",
		                "0007",
		                "0008",
		                "0009",
		                "0010",
		                "0001",
		                "0002",
		                "0003",
		                "0004",
		                "0005",
		                "0006",
		                "0007",
		                "0008",
		                "0009",
		                "0010",
		                "0011",
		                "0012",
		                "0013",
		                "0014",
		                "0015",
		                "0016",
		                "0017",
		                "0018",
		                "0019",
		                "0020",
		                "0021",
		                "0022",
		                "0023",
		                "0024",
		                "0025",
		                "0026",
		                "0027",
		                "0028",
		                "0029",
		                "0030"
		};

		String[] ips = {
                "10.1.24.121",
                "10.1.24.122",
                "10.1.24.123",
                "10.1.24.124",
                "10.1.24.125",
                "10.1.24.126",
                "10.1.24.127",
                "10.1.24.128",
                "10.1.24.129",
                "10.1.24.130",
		};
		

		
		Random rand = new Random();
		
		
		
		for(int i = 0; i < 1000; i++) {
		//	System.out.println(start);
			long interval = rand.nextInt(10000000);
			start.setTime(start.getTime() + interval);
			
			if(start.getHours() > 20) {
				start.setHours(8);
				if(date.getDate() == 28) {
					date.setMonth(date.getMonth()+1);
					date.setDate(1);
				}else {
					date.setDate(date.getDate()+1);	
				}
			}
			
			end.setTime(start.getTime() + interval);
			
			pc = pcs[rand.nextInt(29)];
			client_ip = ips[rand.nextInt(ips.length-1)];
			user = (rand.nextInt(250) + 10) * 10;
		//	System.out.println("ID::: " + user);
			
			//databaseModel.registrarSesiones(start, end, date, labId, pc, client_ip, String.valueOf(user));


		}
		
		
		//databaseModel.registrarSesiones(start, end, date, labId, pc, client_ip, String.valueOf(user));
		
		//THIS SHOULD PASS ALL THE COMPUTER DATA, AND IT SHOULD HANDLE DIVINDING COMPUTERS
				computerData = new ComputerModel(conf.getComputadoras());

		startServer();
		
		view = new ViewController(this);
		
		view.startWindow();
		//view.startDashboard(conf.getRows(), conf.getCols());
		pcsAdded = view.addPCs(computerData); 

		
		//THIS ADDS COMPUTERS

		

		//UPDATES PANELS
		panelTicker = new GUI_Ticker(view, computerData.getComputerDataArray(), this);
		panelTickerThread = new Thread(panelTicker);
		panelTickerThread.start();
		
		try {
			ServerBroadcast broadcast = new ServerBroadcast(serverName, "225.0.0.1", 8099);
			Thread t = new Thread(broadcast);
			t.start();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public DataModel getDataModel() {
		return databaseModel;
	}
	
	public void hideLoadingScreen() {
		loadingScreen.setVisible(false);
	};

	public void changeState(String pc_id, int newState) {
		// view.changeState(pc_id, newState); //CHANGE PC ID TO INDEX OF JSON FILE
	}

	private void startServer() 
	{
	/*	try {
		//	server = new ServerSocket(8099);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "THERE IS ANOTHER INSTANCE OF THE SERVER RUNNING ALREADY");
			System.exit(0);
			
			e.printStackTrace();
		}
*/
		connectionHandler = new ConnectionHandler(this);
		new Thread(connectionHandler).start();

		clients = connectionHandler.getClients();
	}

	public Computer getPcData(int index) 
	{
		return computerData.getComputerDataArray().get(index);
	}
	
	public void updateDataFromSocket() 
	{
		
		
		for(int i = 0; i < computerData.getComputerDataArray().size(); i++) {
	//		System.out.println(computerData.getComputerDataArray().get(i).getFolio() + " : " + computerData.getComputerDataArray().get(i).getState());
		}
			
		for (int i = 0; i < clients.size(); i++) 
		{
			//System.out.print(i);
			if(clients.get(i).isNull()) continue;
			
			if(clients.get(i).isAlive) 
			{
				//System.out.println(" "+ clients.get(i).getPc() + " " + clients.get(i).getState());

					computerData.getComputerDataArray().get(clients.get(i).getPcIndex()).setState(clients.get(i).getState());
					computerData.getComputerDataArray().get(clients.get(i).getPcIndex()).setTime(clients.get(i).getTime());
					computerData.getComputerDataArray().get(clients.get(i).getPcIndex()).setControl(clients.get(i).getUser());
					computerData.getComputerDataArray().get(clients.get(i).getPcIndex()).setUser(clients.get(i).getName());
					computerData.getComputerDataArray().get(clients.get(i).getPcIndex()).setStart(clients.get(i).getStart());
			
					if (clients.get(i).getState() == Computer.DISCONNECTED)
						computerData.getComputerDataArray().get(i).setTime(0);
			}

		}
	}

	@SuppressWarnings("unchecked")
	public int verifyPc(String pc) {
		int counter = -1;
		System.out.println(conf.getComputadoras().size() + " PCS IN ARRAY");
		for (int i = 0; i < conf.getComputadoras().size(); i++) {
			JSONArray maquinas = ((JSONArray) ((JSONObject)conf.getComputadoras().get(i)).get("maquinas"));

			for(int j = 0; j < maquinas.size(); j++ ) {
				counter++;
				System.out.println("ID: " + ((JSONObject) maquinas.get(j)).get("folio"));
				if (((JSONObject) maquinas.get(j)).get("folio").equals(pc)) {
					//for(int j = 0; j < computerData.getComputerDataArray().size(); j++) 
					String x = (String) ((JSONObject)maquinas.get(j)).get("folio");
					System.out.println(x);
						if(computerData.getComputerDataArray().get(j).getState() != Computer.DISCONNECTED) {
							
							System.out.println("Already Here");
							return -1;
						}
					
					computerData.getComputerDataArray().get(j).setState(Computer.AVAILABLE);
					return counter;
				}
				
				
			}
		}
		return -1;
	}
	
	
	public int getLab(int index) {
		return computerData.getComputerDataArray().get(index).getLab();
	}


	public void answerInitRequest(int index, int time) {
		clients.get(index).respondInit(time);
	}

	

	public void requestSession(int pcIndex) {
		computerData.requestSession(pcIndex);
	}

	public void requestSessionAll() {
		for(int i = 0; i < clients.size(); i++)
		computerData.requestSession(i);
	}

	public void setSessionRequest(int i, boolean b) {
		computerData.setSessionRequest(i, b);
		
	}
	public void setSessionRequestAll(boolean b) {
		for(int i = 0; i < clients.size(); i++)
		computerData.setSessionRequest(i, b);
		
	}

	public void updatePcConnection(int pcIndex, int connectionIndex) {
		// TODO Auto-generated method stub
		computerData.setConnectionIndex(pcIndex, connectionIndex);
		
	}

	public void closeSession(int connectionIndex) {
		// TODO Auto-generated method stub
		clients.get(connectionIndex).closeSession();
		
	}
	public void closeSessionAll() {

		System.out.println("SELECTED LAB" + view.getDashboard().getLabSelected());
// TODO Auto-generated method stub
		for(int i = 0; i < computerData.getComputerDataArray().size(); i++) {
			if((computerData.getComputerDataArray().get(i).getLab() == view.getDashboard().getLabSelected() && 
					computerData.getComputerDataArray().get(i).getConnectionIndex()!=-1)) {
				
				System.out.println("CLOSING: " + computerData.getComputerDataArray().get(i).getFolio() + " ON CONNECTION: " + computerData.getComputerDataArray().get(i).getConnectionIndex());
				clients.get(
						computerData.getComputerDataArray().get(i).getConnectionIndex())
				.closeSession();
		
			}
			
		}
		
	}

	public void setRequest(int pcIndex, int request) {
		// TODO Auto-generated method stub
			computerData.setRequest(pcIndex, request);
		
	}
	public void setRequestAll(int request) {
		// TODO Auto-generated method stub
		for(int i = 0; i < clients.size(); i++)
			computerData.setRequest(i, request);
		
	}

	public void setTimeRequest(int pcIndex, boolean b) {
		// TODO Auto-generated method stub
		computerData.setTimeRequested(pcIndex, b);
	}
	public void setTimeRequestAll(boolean b) {
		// TODO Auto-generated method stub
		for(int i = 0; i < clients.size(); i++)
		computerData.setTimeRequested(i, b);
	}

}
