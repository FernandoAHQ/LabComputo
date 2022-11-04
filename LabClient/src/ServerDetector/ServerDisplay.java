package ServerDetector;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import java.awt.Color;

public class ServerDisplay extends JPanel {
	String name, ip;
	JLabel nameLbl, ipLbl;
	MouseAdapter listener;
	Boolean selected = false;
	
	public ServerDisplay(String name, String ip, MouseAdapter listener) {
		this.listener = listener;
		this.name = name;
		this.ip = ip;
		
		this.addMouseListener(listener);
		
		nameLbl = new JLabel();
		ipLbl = new JLabel();
		
		//setBorderPainted(false);
		//setFocusPainted(false);
		//setContentAreaFilled(false);
		
		nameLbl.setText(name + " : ");
		ipLbl.setText(ip);
		
		
		this.setBorder(BorderFactory.createLineBorder(Color.gray, 1));

		this.add(nameLbl);
		this.add(ipLbl);
		
		
		
		this.setPreferredSize(new Dimension(500, 30));
	}

	public void hover() {
		// TODO Auto-generated method stub
		this.setBorder(BorderFactory.createLineBorder(Color.decode("#53A2BE"), 1));
	}

	public void unhover() {
		// TODO Auto-generated method stub
		if(!selected) this.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
	}

	public void click() {
		// TODO Auto-generated method stub
		selected = true;
		this.setBorder(BorderFactory.createLineBorder(Color.decode("#53A2BE"), 2));

	}
	
	public void unselect() {
		selected = false;
		this.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
	}
	
	public String getIp() {
		return ip;
	}
}
