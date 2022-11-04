package ServerDetector;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import GUI.Bloqueo;
import GUI.ConfigWindow;
import GUI.Interface;
import java.awt.event.MouseAdapter;
import java.io.IOException;
import java.io.InputStream;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ServerDetectorGUI extends JFrame implements Runnable, ActionListener{

	Container f;
	JPanel panel, panelMsg, panelServers, panelOptions;
	JButton btnConnect, btnConf;
	JLabel bg, msg1, msg2;
	ArrayList<ServerDisplay> serverButtons;
	 final DefaultComboBoxModel model;
	 
	 boolean search = true;

	
	int selectedServerId;
	
	private int serverCounter = 0;
	
	ServerCollector serverCollector;
	
	MouseAdapter serverListener;
	
	JLabel lblAula, lblMaquina, lblServer;
	JLabel txtAula, txtMaquina;
	
	JPanel panelInfo;
	JPanel panelMaquina, panelAula, panelServidor;
	JPanel mainPanel;
	

	Vector serverList;
	JComboBox comboServers;
	
	CardLayout cl;
	ConfigWindow config;
	
	String folio, aula;
	
	Properties prop;
	
	public ServerDetectorGUI(ServerCollector collector, Properties prop) {
		
		

		
		
		InputStream is = ServerDetectorGUI.class.getResourceAsStream("../Recursos/fonts/Montserrat.ttf");
		Font font, montserrat = null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, is);
			montserrat = font.deriveFont(Font.BOLD, 20f);
			
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		
		
		
		is = ServerDetectorGUI.class.getResourceAsStream("../Recursos/fonts/Gobold.otf");
		Font  oswald = null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, is);
			oswald = font.deriveFont(Font.BOLD, 24f);
			
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		
		
		setResizable(false);
		
		this.prop = prop;
		
		folio = prop.getProperty("pc-id");
		aula = prop.getProperty("aula");
		
		
		cl = new CardLayout();
		
		config = new ConfigWindow(prop);
		config.saveBtn.addActionListener(this);
		config.backBtn.addActionListener(this);
		
		
		
		
		
		
		
		
		this.serverCollector = collector;
		
		f = getContentPane();
		bg = new JLabel();
		//URL url = getClass().getResource("..//resources//loading_screen//bg.png");
	//	ImageIcon im = new ImageIcon(url);// ("C:\\Users\\fhern\\eclipse-workspace\\Laboratorio_Server\\src\\Recursos\\pc_icon.png");
		//Image icon = im.getImage().getScaledInstance(380, 380, java.awt.Image.SCALE_SMOOTH); // scale it the
									// smooth way
	//	bg.setIcon(new ImageIcon(icon));
		
	//	Font lblFont1 = new Font("Montserrat", Font.BOLD, 20);
	//	Font lblFont2 = new Font("Montserrat", Font.PLAIN, 20);
	//	Font lblFont3 = new Font("Montserrat", Font.BOLD, 13);
		
		
		
		serverList=new Vector();
	   
	    model = new DefaultComboBoxModel(serverList);
	    JComboBox comboServers = new JComboBox(model);
	    comboServers.setPreferredSize(new Dimension(170, 30));
	    comboServers.setMaximumSize(new Dimension(20, 5));
		
		model.addElement("Sin Servidor");
		
		comboServers.setFont(montserrat);
		//comboServers.setEnabled(false);
		
		
		panelMaquina = new JPanel(new FlowLayout(FlowLayout.LEADING));
		panelAula = new JPanel(new FlowLayout(FlowLayout.LEADING));
		panelServidor = new JPanel(new FlowLayout(FlowLayout.LEADING));
		
		
		lblMaquina = new JLabel("   Maquina: ");
		lblMaquina.setForeground(Interface.COLOR_MAIN);
		lblMaquina.setFont(montserrat);
		lblAula = new JLabel("   Aula: ");
		lblAula.setForeground(Interface.COLOR_MAIN);
		lblAula.setFont(montserrat);
		txtMaquina = new JLabel("ITC-"+folio);
		txtMaquina.setForeground(Interface.COLOR_MAIN);
		txtMaquina.setFont(montserrat);
		txtAula = new JLabel(aula);
		txtAula.setForeground(Interface.COLOR_MAIN);
		txtAula.setFont(montserrat);
		lblServer = new JLabel("   Servidor: ");
		lblServer.setForeground(Interface.COLOR_MAIN);
		lblServer.setFont(montserrat);
		
		panelMaquina.setBackground(Interface.COLOR_BACKGROUND);
		panelMaquina.add(lblMaquina);
		panelMaquina.add(txtMaquina);
		panelAula.setBackground(Interface.COLOR_BACKGROUND);
		panelAula.add(lblAula);
		panelAula.add(txtAula);
		panelServidor.setBackground(Interface.COLOR_BACKGROUND);
		panelServidor.add(lblServer);
		panelServidor.add(comboServers);
		
		
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		
		serverButtons = new ArrayList<ServerDisplay>();
		
		
		panelServers = new JPanel(new FlowLayout(FlowLayout.LEADING));
		panelServers.setBackground(Interface.COLOR_BACKGROUND);
		
//		panelMsg = new JPanel();
//		panelMsg.add(msg1);
//		panelMsg.add(msg2);
		JLabel title = new JLabel("  Laboratorio de Computo");
		title.setFont(oswald);
	
		title.setForeground(Interface.COLOR_BLUE);
		//title.setBackground(Color.red);
		
		panelInfo = new JPanel(new GridLayout(0, 1));
		panelInfo.setBackground(Interface.COLOR_BACKGROUND);

	//	panelInfo.add(new JLabel());
		panelInfo.add(panelMaquina);
	//	panelInfo.add(new JLabel());
		panelInfo.add(panelAula);
	//	panelInfo.add(new JLabel());
		panelInfo.add(panelServidor);
		
		panelServers.add(title);
		panelServers.add(panelInfo);
		
	//	panelServers.add(msg1);
		
		
		
		panelOptions = new JPanel();
		panelOptions.setBackground(Interface.COLOR_BACKGROUND);
		btnConnect = new JButton("CONECTAR A SERVIDOR");
		btnConnect.setBackground(Interface.COLOR_MAIN);
		btnConnect.setForeground(Color.WHITE);
		btnConnect.setFocusPainted(false);
		btnConnect.setFont(new Font("Montserrat", Font.PLAIN, 18));
		btnConnect.setEnabled(false);
		btnConnect.addActionListener(this);
		
		Icon gear = new ImageIcon(
				new ImageIcon(
						getClass().getResource("..//Recursos//icons//settings.png"))
							.getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));
		
		btnConf = new JButton(gear);
		btnConf.setBackground(Color.WHITE);
		btnConf.setForeground(Interface.COLOR_MAIN);
		btnConf.setPreferredSize(new Dimension(32, 32));
		btnConf.addActionListener(this);
//		btnConf.setFont(new Font("Montserrat", Font.PLAIN, 14));

		
		panelOptions.add(btnConnect);
		panelOptions.add(btnConf);
	//	panelOptions.setBackground(Color.white);

		
		
		
		panel.add(panelServers, BorderLayout.CENTER);
		panel.add(panelOptions, BorderLayout.SOUTH);
		//panel.add(msg1, BorderLayout.EAST);
		//panel.add(msg2);
		panel.setBackground(Color.decode("#e7e7e8"));
		panel.setBackground(Color.blue);
		//add(panel);
		
		
		mainPanel = new JPanel();
		mainPanel.setLayout(cl);
		
		mainPanel.add(panel, "MAIN");
		mainPanel.add(config, "CONFIG");
		
		
		cl.show(mainPanel, "MAIN");
		
	
		add(mainPanel);
		
		setSize(350, 300);
		//setUndecorated(true);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	
	public int getSelectedServerId() {
		return selectedServerId;
	}

	
	public void clearServerPanel() {
		//panelServers.removeAll();
		//panelServers.add(new JLabel("Servers:                   "));	
		panelServers.repaint();
		panelServers.revalidate();
		
	}
	
 

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(search) 
		{
			
			//System.out.println("Detecting Servers...");
			try {Thread.sleep(500);} catch (InterruptedException e) {}
			
			int serverAmountInList = serverCollector.getServerList().size();
			
			if(serverCounter != serverAmountInList) 
			{
				//serverButtons = new ArrayList<ServerDisplay>();
				if(serverAmountInList!=0) 
				{
					btnConnect.setEnabled(true);
					System.out.println("SERVER DETECTED");
					
					model.removeAllElements();
					
					for(int i = 0; i < serverAmountInList; i++) 
					{
						model.addElement(serverCollector.getServerList().get(i).name);

					//	serverArray[i] = serverCollector.getServerList().get(i).name;
						//addServer(serverCollector.getServerList().get(i).getName(), 
							//	serverCollector.getServerList().get(i).getIp());
					}
					

					//comboServers.setFont(lblFont1);
					//comboServers.setEnabled(true);
					
					
				}else 
				{
					btnConnect.setEnabled(false);	
				//	comboServers.setFont(lblFont1);
					model.removeAllElements();
					model.addElement("Sin Servidor");
					//comboServers.setEnabled(false);
					
					//clearServerPanel();
				}
				
				//clearServerPanel();
				
				//comboServers.repaint();
				//comboServers.revalidate();
				serverCounter = serverAmountInList; 
			}
			
			
		}
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		
		//WHEN TIMER GETS TO 079
		// TODO Auto-generated method stub
		
		if(e.getSource().equals(btnConnect)) {
			
			int selectedServer = 0;
			
			try {
				selectedServer = comboServers.getSelectedIndex();
			}catch(NullPointerException ex) {
				
			}
			
			if(!serverCollector.getServerList().isEmpty()) {
				System.out.println("HEEEY");
				System.out.println(serverCollector.getServerList().get(selectedServer).ip.substring(1));
					new Bloqueo(serverCollector.getServerList().get(selectedServer).ip.substring(1),
							serverCollector.getServerList().get(selectedServer).name);
				search = false;
				serverCollector.turnOff();
				this.dispose();
			}else {
				System.out.println("NO SERVER");
			}
		}else if(e.getSource().equals(btnConf)) {
			cl.show(mainPanel, "CONFIG");
		}else if(e.getSource().equals(config.backBtn)) {
			cl.show(mainPanel, "MAIN");
			
		
		}else if(e.getSource().equals( config.saveBtn)) {
			config.guardarConfig();
			txtMaquina.setText("ITC-" + config.prop.getProperty("pc-id"));
			txtAula.setText("ITC-" + config.prop.getProperty("aula"));
			cl.show(mainPanel, "MAIN");
			
			
		}
		
	}
	
}
