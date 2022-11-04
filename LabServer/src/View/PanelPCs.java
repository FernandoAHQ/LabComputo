package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import Controller.MainController;

public class PanelPCs extends JPanel{
	
	int labId;
	int cols;
	ArrayList<PC_Panel> pcPanels;
	
	public PanelPCs(int labId, int cols, ArrayList<PC_Panel> pcPanels) {
		this.labId = labId;
		this.cols = cols;
		this.pcPanels = pcPanels;
		
		initComponents();
		//this.setVisible(true);
		
	}
	
	
	private void initComponents() {
		GridLayout gridLayout = new GridLayout(0, cols, 5, 5);
		this.setLayout(gridLayout);
		//Interface.CENTER_COLOR = Color.decode("#E7E7E8");
		this.setBorder(BorderFactory.createLineBorder(Interface.CENTER_COLOR, 3, false));
		this.setBackground(Interface.CENTER_COLOR);


	}
	
	public void paintPCs() {	
		//	System.out.println(pcPanels.size() + " PCS PAINTED");
		for (int i = 0; i < pcPanels.size(); i++) {
			add(pcPanels.get(i));
		}
	//	System.out.println("PAINTED " + pcPanels.size() + "PANELS");
		//this.repaint();
		this.revalidate();
	}
	

	
	/*
	
	public void enterPanel(int index) {
		pcPanels.get(index).hover();
	}
	
	public void exitPanel(int index) {
		pcPanels.get(index).unhover();	
	}
	
	public void selectPanel(int index) {
	//	pcArray[index].select();
	}
	
	public void unselectPanel(int index) {
	//	pcArray[index].unselect();
	}
	*/
}
