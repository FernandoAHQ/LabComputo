package ServerDetector;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;

public class ServerCollector extends Thread{

	ArrayList<ServerData> serverList;
	
	private boolean isOn = false;
	
	MulticastSocket socket;
	final String IP;
	int PORT;
	
	DatagramPacket packet;
	byte[] message;
	
	public ServerCollector(String ip, int port) throws IOException {
		this.IP = ip;
		this.PORT = port;
		socket = new MulticastSocket(PORT);
        socket.joinGroup(InetAddress.getByName(IP));
        
        serverList = new ArrayList<ServerData>();
	//	serverList.add(new DatagramPacket("xd", 80, port));
	}
	
	private void addServer(DatagramPacket serverPacket) {
		for(int i = 0; i < serverList.size(); i++) {
			if(serverList.get(i).getIp().equals(serverPacket.getAddress().toString())) {
				return;
			}
		}
		serverList.add(new ServerData(serverPacket));
		
	}
	
	public ArrayList<ServerData> getServerList() {
		return serverList;
	}
	
	public void turnOn() {
		isOn = true;
	}
	
	public void turnOff() {
		isOn = false;
		serverList = new ArrayList<ServerData>();
	}
	
	Runnable refresher =
		    new Runnable(){
		        public void run()
		        {
		        	while(true) {
			            try {Thread.sleep(8000);} catch (InterruptedException e) {}
			    		serverList = new ArrayList<ServerData>();
			    	
		        	}
		        }
		    };
	
	
	
	@Override
	public void run() {
		
		new Thread(refresher).start();
		
		
		while(true) {
			if(isOn) {
				//COLLECT SERVER INFO AND SEND TO ORG HERE
				  	message = new byte[256];
			        packet = new DatagramPacket(message, message.length);
			        
			        // recieve the packet
			        try {
						socket.receive(packet);
						addServer(packet);
					} catch (IOException e) {
						e.printStackTrace();
					}
			    
				
			}else {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	
	}

}
