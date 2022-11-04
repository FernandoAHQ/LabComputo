
package View;

import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.CardLayout;
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

public class Window extends JFrame {
	
	public final static String DASHBOARD = "1";
	public final static String REPORTS = "2";
	
	ViewController parent;
	Container f;
	DashboardUI dashboard;
	ReportsUI reports;
	
	MenuPanel sideMenu;
	JPanel panelMain;

	int cols;
	int rows;
	
	CardLayout cl;




	public Window(ViewController parent, DashboardUI dashboard, ReportsUI reports) {
		this.setVisible(false);
		
		this.parent = parent;
		this.dashboard = dashboard;
		this.reports = reports;
		
		
		
		//
		
		//this.setLayout(cl);
		
		initComponents();
	//	setVisible(true);

	}
	
	public void change(String w) {
		cl.show(panelMain, w);
	}

	private void initComponents() {
		f = getContentPane();

		this.setLayout(new BorderLayout());
		
		sideMenu = new MenuPanel(this);
		
		cl = new CardLayout();
				
		panelMain = new JPanel();
		panelMain.setLayout(cl);
		
		panelMain.add(dashboard, DASHBOARD);
		panelMain.add(reports, REPORTS);
		
		this.add(sideMenu, BorderLayout.WEST);
		this.add(panelMain, BorderLayout.CENTER);
		
		cl.show(panelMain, DASHBOARD);
		setSize(1280, 720);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		// setUndecorated(true);
		
	}

	// btnOnC DE3839 , btnOnH CD2726, btnBG 0A2239 , TopBG 1B334A,

	private void exit() {
		System.exit(0);
	}
	
	public void addDashboard(DashboardUI dash) {
		this.dashboard = dash;
	//	add((JPanel)dashboard, DASHBOARD);
		//cl.show(DASHBOARD);
		
	}
	
	public void addReports(ReportsUI rep) {
		this.reports = rep;
	}
}
