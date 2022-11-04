package View;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class pdfViewer extends JFrame{
	
	String file;
	public pdfViewer(String file) {
		this.file = file;
		

		setSize(800, 600);
		//setUndecorated(true);
		setLocationRelativeTo(null);
		
		openpdf(file);
		
		setVisible(true);
		setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		
		
	}
	

	 void openpdf(String file){

	 }

}
