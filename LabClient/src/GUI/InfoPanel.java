package GUI;

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

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.net.URL;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

public class InfoPanel extends JPanel{
	
	private static final Color COLOR_DISCONNECTED = Color.RED;
	private static final Color COLOR_AVAILABE = Color.GREEN;
	private static final Color COLOR_BUSY = Color.ORANGE;
							
	
	private static final Color ACCENT1 = Color.decode("#003366");
	private Color COLOR_STATUS = COLOR_DISCONNECTED;
	
	private String[] requestList = {
			"15 minutos", "30 minutos", "1 hora", "Tiempo Libre"
	};
	
	JPanel bgPanel, mainPanel, panelLogo, panelComputerData, panelButtons, panelSeparator, panelUser, panelRequest, panelDetails;
	JLabel lblIcon, lblComputer, lblStatus, lblTime, lblStart, lblUser, lblControl, lblRequest;
	JTextArea txtDetails;
	JScrollPane scrollDetails;
	JButton btnCloseSesion, btnFreeTime, btnSend;
	JComboBox requestCombo;
	
	private String idMaquina = "N/A";
	
	private int pcStatus = 0;
	private int time = 0;
	private int inicio = 0;
	
	private String user = "N/A";
	private String control = "N/A";
	private int request = 0;
	
	
	public InfoPanel() {
		//System.out.println("Helloooo? Anybody there?");
		//this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(300, 100));
		this.setBackground(ACCENT1);
		
		//CREATING COMPONENTS
		
		lblIcon = new JLabel();
		//CHANGE ICON ROUTE//
		//DELETE THIS AFTERWARDS//
		URL url = getClass().getResource("..//Recursos//pc_icon.png");
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
		
		btnCloseSesion = new JButton("CERRAR SESSION");
		btnCloseSesion.setBackground(COLOR_DISCONNECTED);
		btnCloseSesion.setForeground(Color.WHITE);
		btnCloseSesion.setBorder(BorderFactory.createMatteBorder(0, 10, 0, 5, Color.WHITE));
		btnCloseSesion.setEnabled(false);
		btnCloseSesion.setFont(new Font("Roboto", Font.PLAIN, 12));
	
		btnFreeTime = new JButton("TIEMPO LIBRE");
		btnFreeTime.setBackground(COLOR_AVAILABE);
		btnFreeTime.setForeground(Color.WHITE);
		btnFreeTime.setBorder(BorderFactory.createMatteBorder(0, 5, 0, 10, Color.WHITE));
		btnFreeTime.setEnabled(false);
		btnFreeTime.setFont(new Font("Roboto", Font.PLAIN, 12));
		
		lblUser = new JLabel("    Usuario: N/A");
		lblUser.setFont(new Font("Roboto", Font.PLAIN, 14));
		lblControl = new JLabel("    Numero de Control: N/A");
		lblControl.setFont(new Font("Roboto", Font.PLAIN, 14));
		lblRequest = new JLabel("    Solicitud: N/A");
		lblRequest.setFont(new Font("Roboto", Font.PLAIN, 14));
		requestCombo = new JComboBox(requestList);
		requestCombo.setFont(new Font("Roboto", Font.PLAIN, 14));
		requestCombo.setBorder(BorderFactory.createMatteBorder(0, 10, 0, 5, Color.WHITE));

		
		btnSend = new JButton("CONCEDER");
		btnSend.setBackground(ACCENT1);
		btnSend.setForeground(Color.WHITE);
		btnSend.setBorder(BorderFactory.createMatteBorder(0, 5, 0, 10, Color.WHITE));
		btnSend.setEnabled(false);
		btnSend.setFont(new Font("Roboto", Font.PLAIN, 12));
		
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
		panelButtons.add(btnCloseSesion);
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

}
