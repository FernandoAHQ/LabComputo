/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.EventListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Model.Computer;

/**
 *
 * @author Fernando Hernandez
 */
public class PC_Panel extends JPanel{

	private static final long serialVersionUID = -1970822092797548864L;

	JPanel panelDatos;
	JPanel panelIcono;
	JPanel panelTitulo;
	JLabel icono, titulo, tiempo, id, circleLbl;

	ImageIcon greenIcon, orangeIcon, redIcon;
	private Color COLOR_HOVER1 = Color.decode("#536dbe"), 
					COLOR_HOVER2 = Color.decode("#cccccc"),
						COLOR_HOVER3 = Color.decode("#eeeeee");
	private int state = Computer.DISCONNECTED;
	private boolean sessionRequested;
	int timeRequested;
	String color1;
	String color2;
	String pc, control;
	
	Computer pcData;
	
	int index;
	int click = 0;
	int time;
	int h, m, s;

	public PC_Panel(Computer pcData) {
		this.pcData = pcData;

		
		updateData();
		
		this.control = control;
		panelDatos = new JPanel(new GridLayout(1, 2));
		panelIcono = new JPanel(new FlowLayout());
		panelTitulo = new JPanel(new FlowLayout());
		

		titulo = new JLabel(pc);
		titulo.setForeground(Color.decode("#ffffff"));
		titulo.setFont(new Font("Roboto", Font.BOLD, 12));


		tiempo = new JLabel("00:00:00");
		tiempo.setFont(new Font("Roboto", Font.PLAIN, 12));
		tiempo.setBackground(Color.decode("#dddddd"));

		id = new JLabel("Inactivo");// + control);
		id.setFont(new Font("Roboto", Font.PLAIN, 12));
		id.setBackground(Color.decode("#dddddd"));

		icono = new JLabel();
		URL url = getClass().getResource("..//resources//pc_icon.png");
		ImageIcon im = new ImageIcon(url);// ("C:\\Users\\fhern\\eclipse-workspace\\Laboratorio_Server\\src\\Recursos\\pc_icon.png");
		Image icon = im.getImage().getScaledInstance(70, 50, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		icono.setIcon(new ImageIcon(icon));
		
		
		greenIcon = new ImageIcon(
				new ImageIcon(
					getClass().getResource("..//resources//circle-green.png"))
						.getImage().getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH));

		orangeIcon = new ImageIcon(
				new ImageIcon(
					getClass().getResource("..//resources//circle-orange.png"))
						.getImage().getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH));
		
		redIcon = new ImageIcon(
				new ImageIcon(
					getClass().getResource("..//resources//circle-red.png"))
						.getImage().getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH));
	

		circleLbl = new JLabel();
		circleLbl.setIcon(redIcon);
		
		panelIcono.add(icono);
		panelTitulo.add(circleLbl);
		panelTitulo.add(titulo);
		panelDatos.add(tiempo);
		panelDatos.add(id);
		panelDatos.setBackground(Color.decode("#dddddd"));
		
		panelIcono.setBackground(Color.decode("#ffffff"));
		panelTitulo.setBackground(Color.decode("#003366"));

		this.setBorder(BorderFactory.createLineBorder(Color.decode("#53A2BE"), 1, false));
		setPreferredSize(new Dimension(130, 105));
		setLayout(new BorderLayout());
		updatePanel();

		add(panelIcono, BorderLayout.CENTER);
		add(panelTitulo, BorderLayout.NORTH);
		add(panelDatos, BorderLayout.SOUTH);

	}

	

	public int getIndex() {
		return index;
	}
	
	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public void setTime(int time) {

		h = time / 3600;
		m = (time - (h * 3600)) / 60;
		s = time - (h * 3600) - (m * 60);
		tiempo.setText(String.format("%02d", h) + ":" + String.format("%02d", m) + ":" + String.format("%02d", s));

	}

	public void updatePanel() {

		setTime(time);
		titulo.setText("ITC-" + pc);
		id.setText(control);

		switch (state) {
		case Computer.AVAILABLE: {
			color1 = "#5eba7d";
			color2 = "#2F5D3E";
			circleLbl.setIcon(greenIcon);
			break;
		}
		case Computer.OCCUPIED: {
			color1 = "#f58225";
			color2 = "#AB5B19";
			circleLbl.setIcon(orangeIcon);
			break;
		}
		case Computer.DISCONNECTED: {
			color1 = "#EF0000";
			color2 = "#84100F";
			circleLbl.setIcon(redIcon);
			break;
		}
		}
		//panelTitulo.setBackground(Color.decode(color1));
		panelTitulo.setBorder(BorderFactory.createMatteBorder(0, 0, 4, 0, Color.decode(color1)));
		
		if(sessionRequested || timeRequested!=-1) {
			this.setBorder(BorderFactory.createLineBorder(Color.decode(color1), 6, false));
	//		panelIcono.setBackground(Color.decode(color2));
		}else {
			this.setBorder(BorderFactory.createLineBorder(Color.decode("#53A2BE"), 1, false));
	//		panelIcono.setBackground(Color.WHITE);
		}

		repaint();
	}

	public void updateData() {
		this.pc = pcData.getFolio();
		this.state = pcData.getState();
		this.time = pcData.getTime();
		this.control = pcData.getControl();
		this.sessionRequested = pcData.isSessionRequested();
		this.timeRequested = pcData.getTimeRequested();
		
	}

	public String getPC() {
		return pc;
	}

	public void hover() {
		// TODO Auto-generated method stub
		if(sessionRequested || (timeRequested!=-1)) {
			this.setBorder(BorderFactory.createLineBorder(COLOR_HOVER1, 6));
		}
		else this.setBorder(BorderFactory.createLineBorder(COLOR_HOVER1, 1));
		panelIcono.setBackground(COLOR_HOVER2);	
		
	}
	
	public void unhover() {
		// TODO Auto-generated method stub
		
		if(sessionRequested || (timeRequested!=-1)) {
			this.setBorder(BorderFactory.createLineBorder(Color.decode(color1), 6));
		//	panelIcono.setBackground(Color.decode(color2));
		}
		else {
			this.setBorder(BorderFactory.createLineBorder(Color.decode("#53A2BE"), 1));
		}

		panelIcono.setBackground(Color.WHITE);

	}
	
	public Computer getPcData() {
		return pcData;
	}

	
	public void update() {
		// TODO Auto-generated method stub
		
		
	}
	

}
