package GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Properties;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;

import Network.InitResponse;
import Network.SocketConnection;
import Packages.ReconnectionData;

public class Bloqueo extends JFrame implements ActionListener {

	//WindowsSecurity window;
	private boolean disconnected = true;
	private int serverState = ReconnectionData.STATE_NONE;
	private int lastTime = 0;
	private String currentUser = null;
	
	String ip, serverName;// = "192.168.0.9";//"192.168.1.207";
	int port;// = 8099;
	String id_pc;
	JPanel panel, panelLogin, panelMain, panelTop, panelBot;
	JButton btn;
	Container f;
	JTextField user, password;
	String color1 = "#003366";

	int timerType;
	
	JLabel imagen, serverLbl, messageLbl, userLbl, passwordLbl;
	SocketConnection socketCon = null;
	InitResponse initResponse;
	TimerGUI timer;

	Properties prop;

	public Bloqueo(String ip, String serverName) {
	//	window = new WindowsSecurity(this);
		this.serverName = serverName;
		this.ip = ip;
		prop = new Properties();
		loadProperties(prop);

		System.out.println(prop.getProperty("pc-id"));

		initComponents();

		initSocket();
		
		

	}

	private void initComponents() {
		
		messageLbl = new JLabel("Bienvenido");
		messageLbl.setFont(new Font("Helvetica", Font.BOLD, 30));
		
		userLbl = new JLabel("Número de Control");
		userLbl.setFont(new Font("Helvetica", Font.PLAIN, 25));
		passwordLbl = new JLabel("Contraseña");
		passwordLbl.setFont(new Font("Helvetica", Font.PLAIN, 25));
	
		serverLbl = new JLabel("Conectando al Servidor...");

		InputStream is = Bloqueo.class.getResourceAsStream("../Recursos/fonts/Gobold.otf");
		Font font, montserrat = null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, is);
			montserrat = font.deriveFont(Font.BOLD, 48f);
			
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		
		
		f = getContentPane();
		
		panelMain = new JPanel(new BorderLayout());
		panelTop = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
		panelBot = new JPanel(new BorderLayout());
		
		JLabel topLbl = new JLabel("Instituto Tecnologico de Cancun");
		topLbl.setFont(montserrat);
		panelTop.add(topLbl);		
		panelTop.setBackground(Interface.COLOR_BACKGROUND);

		JLabel botLbl = new JLabel("v1.0 ");
		botLbl.setFont(new Font("Helvetica", Font.BOLD, 15));
		panelBot.add(serverLbl, BorderLayout.WEST);		
		panelBot.add(botLbl, BorderLayout.EAST);		
		panelBot.setBackground(Interface.COLOR_BACKGROUND);

		
		

		panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEADING, 50, 0));
		panel.setBackground(Interface.COLOR_BACKGROUND);

		panelLogin = new JPanel();

		GridLayout gl = new GridLayout(0, 1);
		gl.setHgap(5);
		gl.setVgap(2);

		panelLogin.setLayout(gl);
		panelLogin.setBackground(Interface.COLOR_BACKGROUND);

		user = new JTextField(20);
		 user.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5, true));
		user.setFont(new Font("Helvetica", Font.PLAIN, 25));
		user.setBorder(BorderFactory.createLineBorder(Color.decode(color1)));
		//user.setText("Numero de Control");

		password = new JPasswordField(15);
		password.setFont(new Font("Helvetica", Font.BOLD, 25));
		password.setBorder(BorderFactory.createLineBorder(Color.decode(color1)));
		
		
		user.addActionListener(this);
		password.addActionListener(this);

		imagen = new JLabel();
		URL url = getClass().getResource("..//Recursos//bg.png");
		ImageIcon im = new ImageIcon(url);// ("C:\\Users\\fhern\\eclipse-workspace\\Laboratorio_Server\\src\\Recursos\\pc_icon.png");
		Image icon = im.getImage().getScaledInstance(650, 650, java.awt.Image.SCALE_SMOOTH); // scale it the
									// smooth way
		imagen.setIcon(new ImageIcon(icon));

		btn = new JButton("Ingresar");
		btn.addActionListener(this);
		btn.setBackground(Interface.COLOR_MAIN);
		btn.setForeground(Color.WHITE);
		btn.setFocusPainted(false);
		btn.setFont(new Font("Tahoma", Font.BOLD, 20));

		panelLogin.add(messageLbl);		
		panelLogin.add(new JLabel());
		panelLogin.add(new JLabel());
		panelLogin.add(userLbl);
		panelLogin.add(user);
		panelLogin.add(new JLabel());
		panelLogin.add(passwordLbl);
		panelLogin.add(password);
		panelLogin.add(new JLabel());
		panelLogin.add(btn);

		panel.add(imagen);
		panel.add(panelLogin);
		
		panelMain.add(panelTop, BorderLayout.NORTH);
		panelMain.add(panelBot, BorderLayout.SOUTH);
		panelMain.add(panel, BorderLayout.CENTER);
		
		f.add(panelMain);
		
		
		//setSize(new Dimension(1280, 720));
		 setUndecorated(true);
		 setExtendedState(JFrame.MAXIMIZED_BOTH);
	//	 setAlwaysOnTop(true);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void initSocket() {
		socketCon = new SocketConnection(this, ip, port, id_pc);
		
		timer = new TimerGUI(this, socketCon, -1);
		timer.setVisible(false);
		
		Thread connectionThread = new Thread(socketCon);
		System.out.println("NEW SOCKET FROM CLIENT");

		connectionThread.start();
	}

	public void login(boolean loginResponse) {

		if (loginResponse) {
			currentUser = user.getText();
			messageLbl.setText("Por Favor Espere");
			user.setText("");
			password.setText("");
			btn.setEnabled(false);

			// initWait();

		} else {
			messageLbl.setText("Datos Incorrectos");
			user.setText("");
			password.setText("");
		}

		/*
		 * if(socketCon.login(user.getText()) == SocketConnexion.LOGIN_PASSED) { //
		 * System.out.print("PASSED"); try {
		 * Runtime.getRuntime().exec("explorer.exe").waitFor(); } catch
		 * (InterruptedException e1) { e1.printStackTrace(); } catch (IOException e1) {
		 * e1.printStackTrace(); } //System.exit(0);
		 * 
		 * }
		 */
	}

	public void startClock(int time) {

		btn.setEnabled(true);

		if(time == Integer.MAX_VALUE) {
			
		}
		
		if (time == -1) {
			System.out.print("SERVER DIDNT RESPOND");
		} else if (time == 0) {
			initRejected();
		} else {
			// this.dispose();
			this.setVisible(false);
		//	window.unlock();
			timer.start(time);

		}
	}

	public void lockScreen() {
		serverState = ReconnectionData.STATE_NONE;
		lastTime = 0;
		currentUser = null;
		messageLbl.setText("Bienvenido");
		user.setText("");
		password.setText("");
		this.setVisible(true);
	//	window.lock();
		timer.setTime(-1);
		timer.setVisible(false);
	}
	
	public void unlockScreen() {
		this.setVisible(false);
		timer.setVisible(true);
	}

	public void initAccept() {
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				startClock(500);
			}
		});
		t1.start();
	}

	public void initRejected() {
		// new Bloqueo(id_pc);
		// this.dispose();
		messageLbl.setText("Acceso Denegado");
		user.setText("");
		password.setText("");
		// reset();
	}

	private void initWait() {
		user.setText("GOOD, WAIT");

	}

	private void reset() {
		initComponents();
		// repaint();
		// initSocket();
	}
	
	public ReconnectionData getSessionData() {
		return new ReconnectionData(serverState, lastTime, currentUser);
	}
	
	public void setLastTime(int time) {
		this.lastTime=time;
	}
	
	public void setCurrentUser(String id) {
		this.currentUser = id;
	}
	
	public void setServerState(int state) {
		this.serverState = state;
	}

	private void loadProperties(Properties prop) {
		try {
			prop.load(new FileInputStream(new File("config.properties")));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		id_pc = prop.getProperty("pc-id");
	//	ip = prop.getProperty("server-id");
		port = Integer.parseInt(prop.getProperty("port"));

	}
	
	public void setDisconnected() {
		if(!disconnected) {
			serverLbl.setForeground(Color.red);
			serverLbl.setText("   DESCONECTADO");
			disconnected = true;
		}
	}
	
	public void setConnected() {
		if(disconnected) {
			serverLbl.setForeground(Color.green);
			serverLbl.setText("  " + serverName);
			disconnected = false;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btn)) {
			socketCon.login(user.getText(), password.getText());

		}else if(e.getSource().equals(user)) {
			
			if(user.getText().equals("Numero de Control")) {
				user.setText("");
			}
			
			
			
		}else if(e.getSource().equals(password)) {
			
			
			
			
		}
	}

}
