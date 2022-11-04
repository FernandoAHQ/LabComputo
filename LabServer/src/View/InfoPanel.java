package View;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import Controller.ViewController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Model.Computer;
import Packages.ClientRequest;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.net.URL;
import java.time.LocalDateTime;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

public class InfoPanel extends JPanel implements ActionListener{
	
	ViewController parent;
	
	private static final Color COLOR_DISCONNECTED = Color.RED;
	private static final Color COLOR_AVAILABLE = Color.GREEN;
	private static final Color COLOR_OCCUPIED = Color.ORANGE;
							
	
	private static final Color ACCENT1 = Color.decode("#003366");
	private Color COLOR_STATUS = COLOR_DISCONNECTED;
	
	private String[] requestList = {
			"5 minutos", "15 minutos", "30 minutos", "60 minutos", "Ignorar"
	};
	
	JPanel bgPanel, mainPanel, panelLogo, panelComputerData, panelButtons, panelButtonsA, panelSeparator, panelUser, panelRequest, panelDetails;
	JLabel lblIcon, lblComputer, lblStatus, lblTime, lblStart, lblUser, lblControl, lblRequest;
	JTextArea txtDetails;
	JScrollPane scrollDetails;
	public JButton btnCloseSession, btnFreeTime, btnCloseSessionA, btnFreeTimeA, btnSend;
	JComboBox requestCombo;
	
	private String idMaquina = "N/A";
	
	private int pcIndex, connectionIndex;
	private boolean sessionRequested = false;
	private boolean timeRequested = false;
	private int pcStatus = Computer.DISCONNECTED;
	private int time = 0;
	private int start = 0;
	
	private String user = "N/A";
	private String control = "N/A";
	private int request = 0;
	
	private int h = 0, m = 0, s = 0;
	
	private Computer pcData;
	
	
	public InfoPanel(ViewController parent) {
		//System.out.println("Helloooo? Anybody there?");
		//this.setLayout(new BorderLayout());
		this.parent = parent;
		this.setPreferredSize(new Dimension(300, 100));
		this.setBackground(ACCENT1);
		
		//CREATING COMPONENTS
		
		lblIcon = new JLabel();
		//CHANGE ICON ROUTE//
		//DELETE THIS AFTERWARDS//
		System.out.println("ADFDSFGDASFG");
		URL url = getClass().getResource("..//resources//pc_icon.png");
		ImageIcon im = new ImageIcon(url);// ("C:\\Users\\fhern\\eclipse-workspace\\Laboratorio_Server\\src\\Recursos\\pc_icon.png");
		Image icon = im.getImage().getScaledInstance(170, 122, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		lblIcon.setIcon(new ImageIcon(icon));
		
		lblComputer = new JLabel("    Maquina: "+idMaquina);
		lblComputer.setFont(new Font("Roboto", Font.PLAIN, 14));
		lblStatus = new JLabel("    Estado: N/A");
		lblStatus.setFont(new Font("Roboto", Font.PLAIN, 14));
		lblTime = new JLabel("    Tiempo: 00:00:00");
		lblTime.setFont(new Font("Roboto", Font.PLAIN, 14));
		lblStart = new JLabel("    Inicio: 00:00:00");
		lblStart.setFont(new Font("Roboto", Font.PLAIN, 14));
		
		btnCloseSession = new JButton("CERRAR SESIÓN");
		btnCloseSession.setBackground(COLOR_DISCONNECTED);
		btnCloseSession.setForeground(Color.WHITE);
		btnCloseSession.setBorder(BorderFactory.createMatteBorder(0, 10, 0, 5, Color.WHITE));
		btnCloseSession.setEnabled(false);
		btnCloseSession.setFont(new Font("Roboto", Font.PLAIN, 12));
		btnCloseSession.addActionListener(this);
	
		btnFreeTime = new JButton("TIEMPO LIBRE");
		btnFreeTime.setBackground(COLOR_AVAILABLE);
		btnFreeTime.setForeground(Color.WHITE);
		btnFreeTime.setBorder(BorderFactory.createMatteBorder(0, 5, 0, 10, Color.WHITE));
		btnFreeTime.setEnabled(false);
		btnFreeTime.setFont(new Font("Roboto", Font.PLAIN, 12));
		btnFreeTime.addActionListener(this);
		
		
		lblUser = new JLabel("    Usuario: N/A");
		lblUser.setFont(new Font("Roboto", Font.PLAIN, 14));
		lblControl = new JLabel("    Numero de Control: N/A");
		lblControl.setFont(new Font("Roboto", Font.PLAIN, 14));
		lblRequest = new JLabel("    Solicitud: N/A");
		lblRequest.setFont(new Font("Roboto", Font.PLAIN, 14));
		requestCombo = new JComboBox(requestList);
		requestCombo.setFont(new Font("Roboto", Font.PLAIN, 14));
		requestCombo.setSelectedIndex(2);
		requestCombo.setBorder(BorderFactory.createMatteBorder(0, 10, 0, 5, Color.WHITE));

		
		btnSend = new JButton("CONCEDER");
		btnSend.setBackground(ACCENT1);
		btnSend.setForeground(Color.WHITE);
		btnSend.setBorder(BorderFactory.createMatteBorder(0, 5, 0, 10, Color.WHITE));
		btnSend.setEnabled(false);
		btnSend.setFont(new Font("Roboto", Font.PLAIN, 12));
		btnSend.addActionListener(this);
		
		txtDetails = new JTextArea("Mas Detalles:");
		txtDetails.setEditable(false);
		txtDetails.setFont(new Font("Roboto", Font.PLAIN, 14));
		scrollDetails = new JScrollPane(txtDetails);
		scrollDetails.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		
		
		//CREATING PANELS
		//panelComputerData, panelButtons, panelUser, panelDetails
		
		panelComputerData = new JPanel(new GridLayout(0, 1));
		panelComputerData.setBackground(Color.WHITE);
		panelComputerData.add(lblComputer);
		panelComputerData.add(lblStatus);
		panelComputerData.add(lblTime);
		panelComputerData.add(lblStart);
		panelComputerData.setPreferredSize(new Dimension(250, 90));

		panelButtons = new JPanel(new GridLayout(1, 2));
		panelButtons.setPreferredSize(new Dimension(250, 30));
		panelButtons.setBackground(Color.WHITE);
		panelButtons.add(btnCloseSession);
		panelButtons.add(btnFreeTime);
		
		panelSeparator = new JPanel();
		panelSeparator.setPreferredSize(new Dimension(240, 10));
		panelSeparator.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, ACCENT1));
		
		panelUser = new JPanel(new GridLayout(0, 1));
		panelUser.setBackground(Color.WHITE);
		panelUser.add(lblUser);
		panelUser.add(lblControl);
		panelUser.add(lblRequest);
		panelUser.setPreferredSize(new Dimension(250, 60));
		
		panelRequest = new JPanel(new GridLayout(1, 2));
		panelRequest.setPreferredSize(new Dimension(250, 30));
		panelRequest.setBackground(Color.WHITE);
		panelRequest.add(requestCombo);
		panelRequest.add(btnSend);
		
		panelDetails = new JPanel(new BorderLayout());
		panelDetails.setPreferredSize(new Dimension(230, 100));
		panelDetails.add(scrollDetails);
		

		
		mainPanel = new JPanel();
		mainPanel.setPreferredSize(new Dimension(250, 600));
		mainPanel.setBackground(Color.WHITE);
		mainPanel.setBorder(BorderFactory.createMatteBorder(10, 0, 0, 0, COLOR_STATUS));

		
		
		

		
		
		
		
		
		
		
		mainPanel.add(lblIcon);
		mainPanel.add(panelComputerData);
		mainPanel.add(panelButtons);
		mainPanel.add(panelSeparator);
		mainPanel.add(panelUser);
		mainPanel.add(panelRequest);
		mainPanel.add(panelDetails);

		
		
		
		JPanel filler = new JPanel(); filler.setBackground(ACCENT1); filler.setPreferredSize(new Dimension(250, 15)); add(filler);
		add(mainPanel);
	}
	
	public void update(Computer data) {
		this.pcData = data;
		
		refresh();
	}
	
	
	public void refresh() {
		
		if(pcData!=null) {
			idMaquina = pcData.getFolio();
			pcStatus = pcData.getState();
			time = pcData.getTime();
			start = pcData.getStart();
			user = pcData.getUser();
	//		System.out.println("User: " + user);
			control = pcData.getControl();
			request = pcData.getRequest();
			sessionRequested = pcData.isSessionRequested();
			timeRequested = pcData.isTimeRequested();
			pcIndex = pcData.getPcIndex();
			connectionIndex = pcData.getConnectionIndex();
			
		}
		
		if(request != -1) lblRequest.setFont(new Font("Roboto", Font.BOLD, 14));
		else lblRequest.setFont(new Font("Roboto", Font.PLAIN, 14));
		
		if(sessionRequested) {
			lblControl.setFont(new Font("Roboto", Font.BOLD, 14));
			lblUser.setFont(new Font("Roboto", Font.BOLD, 14));
		}
		else {
			lblControl.setFont(new Font("Roboto", Font.PLAIN, 14));
			lblUser.setFont(new Font("Roboto", Font.PLAIN, 14));
		}
		
		switch(pcStatus) {
			case Computer.AVAILABLE:{
				lblStatus.setText("    Estado: Disponible");
				mainPanel.setBorder(BorderFactory.createMatteBorder(10, 0, 0, 0, COLOR_AVAILABLE));
				btnCloseSession.setEnabled(false);
				btnFreeTime.setEnabled(true);
				btnSend.setEnabled(true);
				
				break;
			}
			case Computer.OCCUPIED:{
				lblStatus.setText("    Estado: En Uso");
				mainPanel.setBorder(BorderFactory.createMatteBorder(10, 0, 0, 0, COLOR_OCCUPIED));
				btnCloseSession.setEnabled(true);
				btnFreeTime.setEnabled(true);
				btnSend.setEnabled(true);
				break;
			}
			case Computer.DISCONNECTED:{
				lblStatus.setText("    Estado: Desconectada");
				mainPanel.setBorder(BorderFactory.createMatteBorder(10, 0, 0, 0, COLOR_DISCONNECTED));
				btnCloseSession.setEnabled(false);
				btnFreeTime.setEnabled(false);
				btnSend.setEnabled(false);
				break;
			}
		}
		
		switch(request) {
			case ClientRequest.REQUEST_QUARTER:{
				lblRequest.setText("    Solicitud: 15 minutos");
	//			requestCombo.setSelectedIndex(0);
				break;
			}
			case ClientRequest.REQUEST_HALF:{
				lblRequest.setText("    Solicitud: 30 minutos");
	//			requestCombo.setSelectedIndex(1);
				break;
			}
			case ClientRequest.REQUEST_HOUR:{
				lblRequest.setText("    Solicitud: 60 minutos");
//				requestCombo.setSelectedIndex(2);
				break;
			}
			default:{
				lblRequest.setText("    Solicitud:");
//				requestCombo.setSelectedIndex(3);
			}
		}
		
		
		lblComputer.setText("    Maquina: ITC-" + idMaquina);
		lblUser.setText("    Usuario: " + user);
		lblControl.setText("    Numero de Control: " + control);
		
		int h = start / 3600;
		int m = (start - (h * 3600)) / 60;
		int s = start - (h * 3600) - (m * 60);
		lblStart.setText("    Inicio: "+ String.format("%02d", h) + ":" + String.format("%02d", m));
		
		
		updateTime(time);
		
		if(sessionRequested) {
		//	requestCombo.setSelectedIndex(2);
			btnSend.setEnabled(true);
		}
		
		
	}
	
	private void updateTime(int time) {

			h = time / 3600;
			m = (time - (h * 3600)) / 60;
			s = time - (h * 3600) - (m * 60);
			lblTime.setText("    Tiempo: " + String.format("%02d", h) + ":" + String.format("%02d", m) + ":" + String.format("%02d", s));

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(btnSend)) {
			
			switch(requestCombo.getSelectedIndex()) {
				case 0:{
					time = 300;
					break;
				}
				case 1:{
					time = 900;
					break;
				}
				case 2:{
					time = 1800;
					break;
				}
				case 3:{
					time = 3600;
					break;
				}
				case 4:{
					time = 0;
					break;
				}
				
			}
			//parent.un
			System.out.println("ANSWERING REQUEST FOR CLIENT " + connectionIndex + ", ON COMPUTER " + pcIndex);
			
			if(requestCombo.getSelectedIndex() != 4) parent.answerRequest(connectionIndex, time);
			
			parent.setSessionRequest(pcIndex, false);
			parent.setTimeRequest(pcIndex, false);
		}else if(e.getSource().equals(btnCloseSession)) {
			parent.getParent().closeSession(pcData.getConnectionIndex());
		}else if(e.getSource().equals(btnFreeTime)) {
			time = Integer.MAX_VALUE;
			parent.answerRequest(connectionIndex, time);
			parent.setSessionRequest(pcIndex, false);
			parent.setTimeRequest(pcIndex, false);
	
		}
		else if(e.getSource().equals(btnCloseSessionA)) {
			parent.getParent().closeSessionAll();
		}
	
	}
	

	
}
