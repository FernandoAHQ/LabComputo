
package View;

import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.awt.CardLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.TextField;

//import javax.print.DocFlavor.URL;
import javax.swing.*;

import Controller.ViewController;
import Model.Computer;

/**
	 * 
	 */

/**
 *
 * @author Fernando Hernandez
 */

public class DashboardUI extends JPanel {
	
	ViewController parent;

	boolean menuOpen = false;
	boolean isFullscreen = false;
	static String topBarColor = "#176087";
	static String centerColor = "#E7E7E8";
	static String colorMenu = "#5e5e5c";

	int cols;
	int rows;

	JTextField tf;

	Container f;
	JPanel panelPrincipal;
	JPanel panelTop;
	JPanel panelCentro;
	ArrayList<CardPanel> panelList;
	CardLayout cl;
	//JPanel panelEquipos;
	MenuPanel panelMenu;
	InfoPanel panelInfo;
	ArrayList<PanelPCs> panelEquipos;


	int labSelected = 0;

	ArrayList<ArrayList<PC_Panel>> pcPanelList;
	ArrayList <PC_Panel> pcPanelList0, pcPanelList1, pcPanelList2, pcPanelList3, pcPanelList4;

	public DashboardUI(ViewController parent) {
		
		
		this.parent = parent;
		this.cols = cols;
		
		pcPanelList = new ArrayList<ArrayList<PC_Panel>>(); //pcPanelList[parent.getLabSize()];// = new ArrayList<PC_Panel>()[parent.getLabSize()];
		panelList = new ArrayList<CardPanel>();
		
		for(int i = 0; i < parent.getLabSize(); i++) pcPanelList.add(new ArrayList<PC_Panel>());
		
		
		
		//for(int i = 0; i < pcPanelList.length; i++) pcPanelList[i] = new ArrayList<PC_Panel>();
		
		panelEquipos = new ArrayList<PanelPCs>();
		System.out.println("SERVER");

		tf = new JTextField(15);

		initComponents();
	//	setVisible(true);

	}

	private void initComponents() {
	
		this.setLayout(new BorderLayout());
		panelTop = new JPanel();
		panelCentro = new JPanel();


		
			for(int i = 0; i < parent.getLabSize(); i++) {
				panelEquipos.add(new PanelPCs(i, parent.getParent().conf.getAulas().get(i).getCols(), pcPanelList.get(i)));
				
			}
		
		//	panelEquipos[0] = new PanelPCs(0, 6, pcPanelList0);
		//	panelEquipos[1] = new PanelPCs(1, 6, pcPanelList1);
		//	panelEquipos[2] = new PanelPCs(2, 6, pcPanelList2);
		//	panelEquipos[3] = new PanelPCs(3, 6, pcPanelList3);
		//	panelEquipos[4] = new PanelPCs(3, 6, pcPanelList4);

		
		
	//	paintPcPanels();
	//	log("PAINTED??");
		
		
		panelInfo = new InfoPanel(parent);

	
		// PANEL CENTRO

		
		//panel0 = new JPanel(new FlowLayout(FlowLayout.CENTER, 100, 20));
		//panel0.setBackground(Color.decode(centerColor));
		//panel0.add(new JLabel("Aula 0"));
		//panel0.add(panelEquipos[0]);
		cl = new CardLayout();
		
	
		for(int i = 0; i < parent.getLabSize(); i++)
		panelList.add(new CardPanel(cl, panelEquipos.get(i), "Aula " + parent.getParent().conf.getAulas().get(i).getAula(), panelCentro, this));
		//panel0.setBackground(Color.black);
	//	panel1 = new CardPanel(cl, panelEquipos[1], "Aula 1", panelCentro, this);
	//	panel2 = new CardPanel(cl, panelEquipos[2], "Aula 2", panelCentro, this);
	//	panel3 = new CardPanel(cl, panelEquipos[3], "Aula 3", panelCentro, this);
	//	panel4 = new CardPanel(cl, panelEquipos[4], "Aula 4", panelCentro, this);
		
		
		panelCentro.setLayout(cl);
		panelCentro.setBackground(Color.decode(centerColor));
	
		for(int i = 0; i < panelList.size(); i++)
		panelCentro.add(panelList.get(i), i+"");
	//	panelCentro.add(panel1, "1");
	//	panelCentro.add(panel2, "2");
	//	panelCentro.add(panel3, "3");
	//	panelCentro.add(panel4, "4");
		//panelCentro.add(new JButton("||"), "2");
		cl.show(panelCentro, "0");
		//panelCentro.add(new JButton("D"));
		this.add(panelCentro, BorderLayout.CENTER);
		//this.add(new JButton("XD"), BorderLayout.CENTER);
		// panelPrincipal.add(panelTop, BorderLayout.NORTH);

		this.add(panelInfo, BorderLayout.EAST);
		this.setBorder(BorderFactory.createLineBorder(Color.decode("#0A2239")));
		//add(panelPrincipal);


	}

	// btnOnC DE3839 , btnOnH CD2726, btnBG 0A2239 , TopBG 1B334A,

	private void exit() {
		System.exit(0);
	}
	
	public InfoPanel getInfoPanel() {
		return panelInfo;
	}


	public void log(String s) {
		System.out.println(s);
	}

	public void addPC(MouseAdapter listener, Computer pcData) {
	
		pcPanelList.get(pcData.getLab()).add(new PC_Panel(pcData));
		pcData.setPanelIndex(pcPanelList.get(pcData.getLab()).size()-1);
		pcPanelList.get(pcData.getLab()).get(pcData.getPanelIndex()).addMouseListener(listener);
		System.out.println("PCS IN PANEL " + pcData.getLab() + ": " + pcPanelList.get(pcData.getLab()).size());
		

		System.out.println("ADDED COMPUTER: " + pcData.getPanelIndex() + " TO PANEL: " + pcData.getLab());
		//System.out.println(pcPanelList0.size() + " IN LIST 0");		
	}

	public void updatePanel(Computer pcData) {
		int i = pcData.getLab();
	//	System.out.println(pcData.getFolio() +" - " +  i + " is the PANEL - " + pcData.getPanelIndex() + " is the INDEX");
		pcPanelList.get(i).get(pcData.getPanelIndex()).updateData();
		pcPanelList.get(i).get(pcData.getPanelIndex()).updatePanel();
		
	
				
	}

	//REMOVE THIS METHOD
	//=============================================================
	public void paintPcPanels() {
	//	System.out.println(panelEquipos.length + " PCS PAINTED");
		for(int i = 0; i < panelEquipos.size(); i++) {
			panelEquipos.get(i).paintPCs();
			}
		
	}

	public ArrayList<PC_Panel> getPcPanels(int i) {
		
		return pcPanelList.get(i);
	
	}
	
	
	public void setSelectedLab(int s) {
		labSelected = s;
	}
	
	
	public void toggleMenu() {
		System.out.println("MENU: " + menuOpen);
		if(menuOpen)
			panelMenu.setPreferredSize(new Dimension(50, 600));
		else
			panelMenu.setPreferredSize(new Dimension(250, 600));
		
		menuOpen = !menuOpen;
		panelPrincipal.repaint();
		panelPrincipal.revalidate();
	}

	public ViewController getViewController() {
		return parent;
	}
	
	public int getLabSelected() {
		return labSelected;
	}
	
}
