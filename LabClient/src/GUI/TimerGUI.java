package GUI;

import GUI.TimerGUI;
import Network.SocketConnection;
import Packages.ClientRequest;
import Packages.ReconnectionData;
import Timer.TimerThread;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class TimerGUI extends JFrame implements ActionListener {

	private static final int TIMER_FREE = 0, TIMER_TIMED = 1;
	
	Bloqueo mainController;
	SocketConnection socket;
	
	int time, timerType;
	int s = 60, m = 0, h = 0;

	int width = 380, height = 76;
	
	Container f;
	JPanel panelCentro, panelButtons, panelFiller;
	JLabel lblTime, lblImage;
	
	  Object[] requestOptions = {"15 minutos", "30 minutos", "60 minutos"};
      
	
	JButton btnClose, btnRequest;
	TimerThread thread;

	public TimerGUI() {
		initComponents();
	}
	
	public TimerGUI(Bloqueo mainController, SocketConnection socketCon, int time) {
		this.mainController = mainController;
		//this.mainController.setTimerType(TIMER_TIMED);
		this.socket = socketCon;
		this.time = time - 2;

		initComponents();
		thread = new TimerThread(this);
		thread.start();
	}

	public TimerGUI(Bloqueo mainController, SocketConnection socketCon) {
		timerType = TIMER_FREE;
		this.mainController = mainController;
		this.socket = socketCon;
		this.time = 1;

		initComponents();
		thread = new TimerThread(this);
		thread.start();
	}

	private void initComponents() {

		f = getContentPane();
		panelCentro = new JPanel(new FlowLayout(FlowLayout.CENTER));
	//	panelCentro.setBackground(Interface.COLOR_BACKGROUND);
//		panelCentro.setBorder(Interface.BORDER_BLACK_1);
		
		lblImage = new JLabel();
		lblImage = new JLabel();
		URL url = getClass().getResource("..//Recursos//bg.png");
		ImageIcon im = new ImageIcon(url);// ("C:\\Users\\fhern\\eclipse-workspace\\Laboratorio_Server\\src\\Recursos\\pc_icon.png");
		Image icon = im.getImage().getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH); // scale it the
									// smooth way
		lblImage.setIcon(new ImageIcon(icon));
		
		lblTime = new JLabel("00:00:00");
		lblTime.setFont(Interface.FONT_TIMER);
		lblTime.setForeground(Interface.COLOR_BLUE);
		
		panelFiller = new JPanel();
		//panelFiller.setBackground(Interface.COLOR_BACKGROUND);
		panelFiller.setPreferredSize(new Dimension(20, 20));
		
		btnClose = new JButton("Cerrar Sesión");
		btnClose.addActionListener(this);
		btnClose.setBackground(Interface.COLOR_RED);
		btnClose.setForeground(Color.WHITE);
		btnClose.setFocusPainted(false);
		btnClose.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnClose.setBorder(BorderFactory.createLineBorder(Interface.COLOR_BACKGROUND, 2));
	//	btnClose.addActionListener(this);
		
		btnRequest = new JButton("Más Tiempo");
		btnRequest.addActionListener(this);
		btnRequest.setBackground(Interface.COLOR_BLUE);
		btnRequest.setForeground(Color.WHITE);
		btnRequest.setFocusPainted(false);
		btnRequest.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnRequest.setBorder(BorderFactory.createLineBorder(Interface.COLOR_BACKGROUND, 2));
	//	btnRequest.addActionListener(this);
		
		panelButtons = new JPanel(new GridLayout(0, 1));
		panelButtons.setPreferredSize(new Dimension(120, 60));
		//SDFASGSDFGDFSGDS//panelButtons.setBackground(Interface.COLOR_BACKGROUND);
		panelButtons.add(btnClose);
		panelButtons.add(btnRequest);

		panelCentro.add(lblImage);
		panelCentro.add(lblTime);
		panelCentro.add(panelFiller);
		panelCentro.add(panelButtons);

		f.add(panelCentro);

	

	        // Move the window
	        this.setLocation((int)Interface.SCREEN_DIMENSION.getWidth()-width-10, (int)Interface.SCREEN_DIMENSION.getHeight()-height-50);
	//        System.out.println("Width: " + Interface.SCREEN_DIMENSION.getWidth() + "\n Height: " + Interface.SCREEN_DIMENSION.getHeight());
	        
		setSize(width, height);
	//	setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setUndecorated(true);
		setOpacity(0.50f);
	//	getContentPane().setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
	//	setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
		//setBackground(new Color(1.0f,1.0f,1.0f,0.5f));
		setVisible(true);
	//	socket.passTimer(this);
	}

	public void updateTime() {
		// s--;
		if (time == 0) {
			System.out.println("CLOSING SOCKET FROM GUI");
			time=-1;
			socket.closeConnection();
			socket.logout();
			//new Bloqueo();
			//socket//HERE WE NEED TO HIDE THE TIMER GUI AND SHOW THE LOCK GUI
			//this.dispose();
			//thread.stop();
			mainController.setServerState(ReconnectionData.STATE_NONE);
			mainController.setCurrentUser(null);
			mainController.lockScreen();
		}else if(time>0) {
			h = time / 3600;
			m = (time - (h * 3600)) / 60;
			s = time - (h * 3600) - (m * 60);
			lblTime.setText(String.format("%02d", h) + ":" + String.format("%02d", m) + ":" + String.format("%02d", s));
			
			if(timerType == TIMER_TIMED) {
				time--;
				mainController.setServerState(ReconnectionData.STATE_TIMED);
			}
			else {
				time++;
				mainController.setServerState(ReconnectionData.STATE_FREE);
			}
		}
		mainController.setLastTime(time);

	}
	
	public void start(int time) {
		
		lblTime.setText("IT CANCUN");
		if(time == Integer.MAX_VALUE) {
			timerType = TIMER_FREE;
			this.time = 1;
		}else {			
			timerType = TIMER_TIMED;
			this.time = time;
		}
		this.setVisible(true);
	}
	
	public void setTime(int t) {
		time = t;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
//		System.out.println("Count of listeners: " + ((JButton) e.getSource()).getActionListeners().length);

		if(e.getSource().equals(btnClose)) {

			if(JOptionPane.showConfirmDialog(this,"¿Desea cerrar la sesión?", "Cerrar Sesión",
		               JOptionPane.YES_NO_OPTION,
		               JOptionPane.CLOSED_OPTION) == JOptionPane.YES_OPTION){
			  // Saving code here			System.out.println("BUTTON CLOSE");
				socket.requestClose();
			}
			
			

		}else if(e.getSource().equals(btnRequest)) {
		    //...and passing `frame` instead of `null` as first parameter
	        Object selectionObject = JOptionPane.showInputDialog(this, "¿Cuánto tiempo deseas solicitar?", "Solicitar Más Tiempo", JOptionPane.PLAIN_MESSAGE, null, requestOptions, requestOptions[0]);
	        //System.out.println(selectionObject + ": " + selectionString);
	        
	        int timeRequest = ClientRequest.REQUEST_QUARTER;
	        if(selectionObject != null) {
	        	System.out.println("NULL SELECTION OBJECT");

	        	if(selectionObject.equals(requestOptions[1])) {
	        	timeRequest = ClientRequest.REQUEST_HALF;
	        } else if(selectionObject.equals(requestOptions[2])) {
	        	timeRequest = ClientRequest.REQUEST_HOUR;
	        }
	        
	        socket.requestTime(timeRequest);
	        
	        }
		}
	}

}
