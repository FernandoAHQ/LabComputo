package Controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class ConnectionHandler implements Runnable {

	ServerSocket server;
	Socket newClient;
	ClientHandler clientHandler;
	ArrayList<ClientHandler> clients;
	MainController controller;
	int connectionIndex = 0;

	public ConnectionHandler(MainController controller) {
		startServer();
		this.controller = controller;
		clients = new ArrayList<ClientHandler>();
	}

	@Override
	public void run() {

		while (true) {
		
		
			try {
				System.out.println("ACCEPTING CONNECTION");
				if(!server.isClosed())
				{
					newClient = server.accept();
					System.out.println("CONNECTION ACCEPTED");
					String pc;

				try {

					ObjectOutputStream tempOut = new ObjectOutputStream(newClient.getOutputStream());
					ObjectInputStream tempIn = new ObjectInputStream(newClient.getInputStream());
					pc = (String) tempIn.readObject();
					System.out.println("=======================================================");
					System.out.println("PC ID: "  + pc);
					
					int pcIndex = controller.verifyPc(pc);
					System.out.println("=======================================================");
					System.out.println("PC INDEX: " + pcIndex);

					if (pcIndex != -1) {
						controller.updatePcConnection(pcIndex, connectionIndex);
						tempOut.writeBoolean(true);
						tempOut.flush();
						
						clientHandler = new ClientHandler(
								controller, server, newClient, tempOut, tempIn, pc, connectionIndex, pcIndex, controller.getLab(pcIndex)
								);
						
						clients.add(clientHandler);
						new Thread(clientHandler).start();
						System.out.println("ConnectionHandler - SERVER ACCEPTED CLIENT: Good ID");
						connectionIndex++;

					} else {
						tempOut.writeBoolean(false);
						tempOut.flush();

						System.out.println("ConnectionHandler - SERVER REJECTED CLIENT: Bad ID");
					}
					
					
					
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}else {
				startServer();
				
			}
				
				//HERE
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("Number of connection threads: " + clients.size());
		}

	}

	public void startServer() {
		try {
			server = new ServerSocket(8099);
			System.out.println(server.getInetAddress());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "THERE IS ANOTHER INSTANCE OF THE SERVER RUNNING ALREADY");
			System.exit(0);
		
			e.printStackTrace();
		};
	}
	
	public ArrayList<ClientHandler> getClients() {
		return clients;
	}

}
