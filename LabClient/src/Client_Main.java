import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MulticastSocket;
import java.time.LocalDateTime;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import GUI.Bloqueo;
import GUI.ConfigWindow;
import GUI.TestFrame;
import GUI.TimerGUI;
import GUI.WindowsSecurity;
import ServerDetector.ServerCollector;
import ServerDetector.ServerDetectorGUI;

public class Client_Main {

	private MulticastSocket socket;
    static ServerCollector serverCollector;
    static ServerDetectorGUI servers;
    
    static Properties prop;
    
	static String id_pc;
	static String aula = "";
	static String port;
	
	public static void main(String[] args) {
		
		//Bloqueo b = new Bloqueo("d", "");
		
		
		prop = new Properties();
		loadProperties(prop);

		System.out.println(prop.getProperty("pc-id"));

		
		//WindowsSecurity lock = null;
		//lock = new WindowsSecurity(new Bloqueo(lock));
	//	new Bloqueo("192.168.0.15", "Aula 3");
		try {
			serverCollector = new ServerCollector("225.0.0.1", 8099);
			serverCollector.start();
			serverCollector.turnOn();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	//ADD BUTTON ACTIONLISTENER HERE
		
		
    
		
    	ActionListener listener = new ActionListener() {
    	
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(serverCollector.getServerList().size() != 0) {
					String ip = serverCollector.getServerList().get(servers.getSelectedServerId()).getIp();
					String name = serverCollector.getServerList().get(servers.getSelectedServerId()).getName();
					System.out.println("CONNECTING TO SERVER: " + ip.substring(1));
				
					serverCollector.turnOff();
					servers.setVisible(false);
					new Bloqueo(ip.substring(1), name);	
					
				
				}
				else System.out.println("NO SERVERS DETECTED");
			}  
    	};
    	
    	servers = new ServerDetectorGUI(serverCollector, prop);
    	Thread t = new Thread(servers);
    	t.start();
	
	}

	
	private static void loadProperties(Properties prop) {
		try {
			prop.load(new FileInputStream(new File("config.properties")));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			id_pc = prop.getProperty("pc-id");
			aula = prop.getProperty("aula");
		//	port = Integer.parseInt(prop.getProperty("port"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	
}
