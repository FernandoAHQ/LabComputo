package View;

import javax.swing.ImageIcon;
import java.awt.Image;
import java.net.URL;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;

import java.awt.BorderLayout;
import javax.swing.JPanel;



public class LoadingScreen extends JFrame {
	Container f;
	JPanel panel, panelMsg;
	JLabel bg, msg1, msg2;
	
	
	public LoadingScreen() {
		initComponents();
	}
	

private void initComponents() {
	f = getContentPane();
	bg = new JLabel();
	URL url = getClass().getResource("..//resources//loading_screen//bg.png");
	System.out.println(url);
	ImageIcon im = new ImageIcon(url);
	Image icon = im.getImage().getScaledInstance(380, 380, java.awt.Image.SCALE_SMOOTH); // scale it the
								// smooth way
	bg.setIcon(new ImageIcon(icon));

	msg1 = new JLabel("ITC Laboratorios  ");
	msg1.setFont(new Font("Oswald", Font.BOLD, 45));
	msg2 = new JLabel("Computo");
	
	msg2.setFont(new Font("Bebas Neue", Font.BOLD, 40));
	panel = new JPanel(new BorderLayout());
	
//	panelMsg = new JPanel();
//	panelMsg.add(msg1);
//	panelMsg.add(msg2);
	
	panel.add(bg, BorderLayout.CENTER);
	panel.add(msg1, BorderLayout.EAST);
	//panel.add(msg2);
	panel.setBackground(Color.decode("#e7e7e8"));
	add(panel);
	
	
	
	setSize(600, 400);
	setUndecorated(true);
	setLocationRelativeTo(null);
	setVisible(true);
	setDefaultCloseOperation(EXIT_ON_CLOSE);
}

}
