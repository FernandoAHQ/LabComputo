package Controller;

import java.awt.event.MouseAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import Model.Computer;
import Model.ComputerModel;
import View.Window;
import View.DashboardUI;
import View.InfoPanel;
import View.PC_Panel;
import View.ReportsUI;

public class ViewController {
	Window window;
	ReportsUI reports;
	DashboardUI dashboard;
	InfoPanel panelInfo;
	
	int indexSelected, indexHovered;
	int pcs = 0;
	//PC_Panel[] panels;
	MainController parent;
	MouseAdapter panelListener;
	ActionListener btnActionListener;

	public ViewController(MainController parent) {
		this.parent = parent;
	//	window = new Window(this);
		
	}

	public void startWindow() {
		dashboard = new DashboardUI(this);
		panelInfo = dashboard.getInfoPanel();
		//panels = dashboard.getPcPanels();
		reports = new ReportsUI(this, parent.getDataModel());
		window = new Window(this, dashboard, reports);
		//shboard(dashboard);
		
		
	}
	
	
	
	public int getLabSize() {
		return parent.conf.getComputadoras().size();
	}

	@SuppressWarnings("unchecked")
	public int addPCs(ComputerModel computadoras) {
 		//System.out.println(computadoras.getComputerDataArray().size() + "PCS LOADED");
		
		panelListener = new MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent evt) {
		    	
		    	((PC_Panel)evt.getSource()).hover();
		    	
		    	//PROBABLY SHOULD ADD LAB SELECTED
		      	indexHovered = ((PC_Panel)evt.getSource()).getIndex();
		 //       slider.setBackground(TOGGLE_COLOR);
		    	//System.out.println("ENTERED PANEL");
		    }

		    public void mouseExited(java.awt.event.MouseEvent evt) {
				//        slider.setBackground(BUTTON_COLOR);
			//	    	System.out.println("EXITED PANEL");
		    	((PC_Panel)evt.getSource()).unhover();
		    }
				
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
				//        slider.setBackground(BUTTON_COLOR);
			//	    	System.out.println("CLICKED PANEL");
		    	System.out.println(parent.getPcData(indexSelected));
		    	panelInfo.update(((PC_Panel)evt.getSource()).getPcData());
		    	//((PC_Panel)evt.getSource()).set
		    	
	
				    }
			};
		
		//computadoras.forEach(comp -> agregarPC((JSONObject) comp));
		for(int i = 0; i < computadoras.getComputerDataArray().size(); i++) {
			dashboard.addPC(panelListener, computadoras.getComputerDataArray().get(i));
		} 
		
		dashboard.paintPcPanels();
		//dashboard.revalidate();
		parent.hideLoadingScreen();
		window.setVisible(true);
		return pcs;
	}

	private void agregarPC(Computer comp, int index) {
		dashboard.addPC(panelListener, comp);
		pcs++;
	}
/*
	public void changeState(int pcIndex, int state) {
		dashboard.getPcPanels()[pcIndex].setState(state);
	}
*/
	public void updatePanels(ArrayList<Computer> comps) {
		for (int i = 0; i < comps.size(); i++) {
			dashboard.updatePanel(comps.get(i));
		}
	}
	
	public void unselectPanel(int i) {
		
	}
	
	public void refreshInfoPanel() {
		panelInfo.refresh();
	}
	
	public void setSessionRequest(int i, boolean b) {
		parent.setSessionRequest(i, b);
	}
	
	public void answerRequest(int index, int time) {
		parent.answerInitRequest(index, time);

	}
	
	public MainController getParent() {
		return parent;
	}

	public void setTimeRequest(int pcIndex, boolean b) {
		// TODO Auto-generated method stub
		parent.setTimeRequest(pcIndex, b);
		
	}

	public void answerRequestAll(int time) {
		// TODO Auto-generated method stub
		
	}

	public DashboardUI getDashboard() {
		return dashboard;
	}
}
