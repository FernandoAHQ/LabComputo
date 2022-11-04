package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TestFrame extends JFrame{
	Container f;
	JPanel panelMain, panelEast;
	InfoPanel panel;
	
	public TestFrame() {
		initComponents();
	}

	private void initComponents() {
		f = getContentPane();
		

		
		panelMain = new JPanel(new BorderLayout());
	//	panelMain.add(new InfoPanel(), BorderLayout.CENTER);
		
		panel = new InfoPanel();
		
		
	//	panelEast.add(panel);
		//panel.
		//panel.setBackground(Color.decode("#003366"));
		panelMain.add(panel, BorderLayout.EAST);
		add(panelMain);
		
		setSize(1280, 720);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	
	}
}
