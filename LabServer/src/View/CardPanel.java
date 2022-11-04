package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Controller.MainController;

public class CardPanel extends JPanel implements ActionListener{

	PanelPCs pcPanel;

	String aula = "Aula";

	CardLayout cl;
	JPanel panelButtonsA, panelButtonsCard, parent;
	JButton btnCloseSessionA, btnFreeTimeA;
	JButton btn[];
	JLabel lblAula;
	DashboardUI dash;
	MainController main;
	
	int selected;
	
	public CardPanel(CardLayout cl, PanelPCs pcPanel, String aula, JPanel parent, DashboardUI dashboardUI) {
		this.parent = parent;
		this.cl = cl;
		this.pcPanel = pcPanel;
		this.aula = aula;
		this.dash = dashboardUI;
		
		this.main = dash.getViewController().getParent();
		
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 20));
		this.setBackground(Interface.CENTER_COLOR);
		
		lblAula = new JLabel(aula);
		lblAula.setFont(new Font("Helvetica", Font.BOLD, 20));
		
		panelButtonsCard = new JPanel();
		panelButtonsCard.setBackground(Interface.CENTER_COLOR);
		btn = new JButton[5];
		for(int i = 0; i < 5; i++) {
			btn[i] = new JButton(""+ i);
			btn[i].setBackground(Interface.ACCENT1);
			btn[i].setForeground(Color.WHITE);
			btn[i].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE));
			btn[i].setEnabled(true);
			btn[i].setFont(new Font("Roboto", Font.PLAIN, 14));
			btn[i].setPreferredSize(new Dimension(30, 30));
			btn[i].addActionListener(this);
			panelButtonsCard.add(btn[i]);
				
		}
		
		
		
		
		btnCloseSessionA = new JButton("CERRAR SESIONES");
		btnCloseSessionA.setBackground(Interface.COLOR_DISCONNECTED);
		btnCloseSessionA.setForeground(Color.WHITE);
		//btnCloseSessionA.setBorder(BorderFactory.createMatteBorder(0, 10, 0, 5, Color.WHITE));
		btnCloseSessionA.setEnabled(true);
		btnCloseSessionA.setFont(new Font("Roboto", Font.PLAIN, 12));
		btnCloseSessionA.setPreferredSize(new Dimension(150, 30));
		btnCloseSessionA.addActionListener(this);
	
		btnFreeTimeA = new JButton("HABILITAR");
		btnFreeTimeA.setBackground(Interface.COLOR_AVAILABLE);
		btnFreeTimeA.setForeground(Color.WHITE);
		btnFreeTimeA.setBorder(BorderFactory.createMatteBorder(0, 5, 0, 10, Color.WHITE));
		btnFreeTimeA.setEnabled(true);
		btnFreeTimeA.setFont(new Font("Roboto", Font.PLAIN, 12));
		btnFreeTimeA.setPreferredSize(new Dimension(150, 30));
		btnFreeTimeA.addActionListener(this);
		
		

		panelButtonsA = new JPanel(new FlowLayout(FlowLayout.LEFT, 100, 20));
		panelButtonsA.setPreferredSize(new Dimension(150, 30));
		panelButtonsA.setBackground(Interface.CENTER_COLOR);
		panelButtonsA.add(btnCloseSessionA);
		panelButtonsA.add(btnFreeTimeA);


		this.add(lblAula);
		this.add(panelButtonsCard);
		this.add(btnCloseSessionA);
		this.add(this.pcPanel);
		//this.add(btnFreeTimeA);
		//this.setPreferredSize(new Dimension(400, 400));
		this.setVisible(true);
	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(btnCloseSessionA)) {
			main.closeSessionAll();
		}else if(e.getSource().equals(btnFreeTimeA)) {
			
		}else {
			
			for(int i = 0; i < 5; i++) {
				if(e.getSource().equals(btn[i])) {
					cl.show(parent, ""+i);
					dash.setSelectedLab(i);
				}
			}
			
		}
		
	}
	
}
