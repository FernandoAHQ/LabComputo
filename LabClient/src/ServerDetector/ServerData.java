package ServerDetector;
import java.net.DatagramPacket;
import java.net.InetAddress;

public class ServerData {
	
	String ip;
	String name;
	
	public ServerData(DatagramPacket data) {
		ip = data.getAddress().toString();

		name = new String(data.getData()).trim();
	}
	
	public String getIp() {
		return ip;
	}
	
	public String getName() {
		return name;
	}
	
}
