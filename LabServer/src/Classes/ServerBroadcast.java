package Classes;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class ServerBroadcast implements Runnable{
	
	final String serverName;
	final String ip;
	final int port;
	
	DatagramSocket broadcastSocket;
	
	
	public ServerBroadcast(String serverName, String ip, int port) throws SocketException {
		this.serverName = serverName;
		this.ip = ip;
		this.port = port;
		broadcastSocket = new DatagramSocket();
		
	}
	
	public void send(String msg) throws IOException{
        // make datagram packet
        byte[] message = msg.getBytes();
        DatagramPacket packet = new DatagramPacket(message, message.length, 
            InetAddress.getByName(ip), port);
        // send packet
        broadcastSocket.send(packet);
    }
	
	@Override
	public void run() {

		while(true) {
		
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				send(serverName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
		
	}

}
