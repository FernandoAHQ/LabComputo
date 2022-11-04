package View;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.GridLayout;
import java.awt.CardLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class MenuPanel extends JPanel implements ActionListener{
	
	private final Color MAIN_COLOR = Color.decode("#c8c8c8");
	private final Color BUTTON_COLOR = Color.decode("#c8c8c8");
	private final Color BUTTON_HOVER = new Color(131, 133, 131);
	private Color TOGGLE_COLOR = Color.GREEN;
	
	//DashboardUI parent;
	JButton slider, btnConsole, btnSettings, btnUsers, btnReports;
	BufferedImage slideOutIcon, slideInIcon;
	ImageIcon close, open, history, settings, users, reports;
	
	boolean isOpen = false;
	
	CardLayout cardLayout, cl;
	Window window;
	JPanel panelMain;
	
	
	public MenuPanel(Window window){
		this.window = window;
		//this.parent = dashboardUI;
		initComponents();

	}
	
	private void initComponents() {
		
		cardLayout = new CardLayout();
		this.setBackground(MAIN_COLOR);
		this.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.decode("#b4b4b4")));
		
		
		//
		//MAIN MENU
		//
		
		panelMain = new JPanel();
		panelMain.setBackground(MAIN_COLOR);
		panelMain.setLayout(new GridLayout(14,1));

		close = new ImageIcon(
				new ImageIcon(
					getClass().getResource("..//resources//menuIcons//menu.png"))
						.getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));
		
		open = new ImageIcon(
				new ImageIcon(
					getClass().getResource("..//resources//menuIcons//menu.png"))
						.getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));
		
		history = new ImageIcon(
				new ImageIcon(
					getClass().getResource("..//resources//menuIcons//history.png"))
						.getImage().getScaledInstance(32, 28, java.awt.Image.SCALE_SMOOTH));
			
		settings = new ImageIcon(
				new ImageIcon(
					getClass().getResource("..//resources//menuIcons//settings.png"))
						.getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));
			
		users = new ImageIcon(
				new ImageIcon(
					getClass().getResource("..//resources//menuIcons//users.png"))
						.getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));
			
		reports = new ImageIcon(
				new ImageIcon(
					getClass().getResource("..//resources//menuIcons//reports.png"))
						.getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));
			
		
		
		
		
		
		this.setLayout(cardLayout);
		
		slider = new JButton();
		slider.setBackground(BUTTON_COLOR);
		slider.setIcon(open);
		slider.setBorderPainted(false);
		slider.setFocusPainted(false);
		slider.addActionListener(this);
		slider.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent evt) {
		        slider.setBackground(TOGGLE_COLOR);
		    }

		    public void mouseExited(java.awt.event.MouseEvent evt) {
		        slider.setBackground(BUTTON_COLOR);
		    }
		});
		
		btnConsole = new JButton("");
		btnConsole.setBackground(BUTTON_COLOR);
		btnConsole.setIcon(history);
		btnConsole.setBorderPainted(false);
		btnConsole.setFocusPainted(false);
		btnConsole.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent evt) {
		    	btnConsole.setBackground(BUTTON_HOVER);
		    }

		    public void mouseExited(java.awt.event.MouseEvent evt) {
		    	btnConsole.setBackground(BUTTON_COLOR);
		    }
		    
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		    	window.change(Window.DASHBOARD);
		    }
		    
		});
		btnConsole.addActionListener(this);

		btnSettings = new JButton("");
		btnSettings.setBackground(BUTTON_COLOR);
		btnSettings.setIcon(settings);
		btnSettings.setBorderPainted(false);
		btnSettings.setFocusPainted(false);
		btnSettings.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent evt) {
		    	btnSettings.setBackground(BUTTON_HOVER);
		    }

		    public void mouseExited(java.awt.event.MouseEvent evt) {
		    	btnSettings.setBackground(BUTTON_COLOR);
		    }
		});
		btnSettings.addActionListener(this);

		btnUsers = new JButton("");
		btnUsers.setBackground(BUTTON_COLOR);
		btnUsers.setIcon(users);
		btnUsers.setBorderPainted(false);
		btnUsers.setFocusPainted(false);
		btnUsers.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent evt) {
		    	btnUsers.setBackground(BUTTON_HOVER);
		    }

		    public void mouseExited(java.awt.event.MouseEvent evt) {
		    	btnUsers.setBackground(BUTTON_COLOR);
		    }
		});
		btnUsers.addActionListener(this);

		btnReports = new JButton("");
		btnReports.setBackground(BUTTON_COLOR);
		btnReports.setIcon(reports);
		btnReports.setBorderPainted(false);
		btnReports.setFocusPainted(false);
		btnReports.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent evt) {
		    	btnReports.setBackground(BUTTON_HOVER);
		    }

		    public void mouseExited(java.awt.event.MouseEvent evt) {
		    	btnReports.setBackground(BUTTON_COLOR);
		    }

		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		    	window.change(Window.REPORTS);
		    }
		});
		btnReports.addActionListener(this);
		
		slider.setFont(new Font("Roboto", Font.BOLD, 16));
		btnConsole.setFont(new Font("Roboto", Font.BOLD, 16));
		btnSettings.setFont(new Font("Roboto", Font.BOLD, 16));
		btnUsers.setFont(new Font("Roboto", Font.BOLD, 16));
		btnReports.setFont(new Font("Roboto", Font.BOLD, 16));

		panelMain.add(slider);
		panelMain.add(btnConsole);
		panelMain.add(btnSettings);
	//	panelMain.add(btnUsers);
		panelMain.add(btnReports);
		
		panelMain.setAlignmentX(LEFT_ALIGNMENT);
		//cardLayou.setHorizontalAlignment(JLabel.CENTER)
		
		this.add(panelMain, "1");
		cardLayout.show(this, "1");
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(slider)) {
		//	parent.toggleMenu();
			isOpen = !isOpen;
			
			if(isOpen) {
				TOGGLE_COLOR = new Color(191, 0, 3);
				slider.setIcon(close);
				slider.setText("Cerrar");
				btnReports.setText("Reportes");
				btnUsers.setText("Usuarios");
				btnSettings.setText("Configuración");
				btnConsole.setText("Aulas");
			}else {
				TOGGLE_COLOR = new Color(14, 102, 0);
				slider.setIcon(open);
				slider.setText("");
				btnReports.setText("");
				btnUsers.setText("");
				btnSettings.setText("");
				btnConsole.setText("");
			}

		}
		panelMain.repaint();
		panelMain.revalidate();
	}
}
