package GUI;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;


public class ConfigWindow extends JPanel{
	
	
	public JButton saveBtn, backBtn;
	JLabel lblMaquina, lblAula;
	JTextField txtMaquina, txtAula;
	JPanel panelOptions, panelMaquina, panelAula, panelInfo, panelServers;
	
	public Properties prop;


	public ConfigWindow(Properties prop) {
		this.prop = prop;
		initComponents(prop.getProperty("aula"), prop.getProperty("pc-id"));
	}
	
	
	private void initComponents(String a, String f) {
		
		

		Font lblFont1 = new Font("Montserrat", Font.BOLD, 20);
		Font lblFont2 = new Font("Montserrat", Font.PLAIN, 20);
		Font lblFont3 = new Font("Montserrat", Font.BOLD, 13);
		
		panelOptions = new JPanel();
		
		
		saveBtn = new JButton("GUARDAR LOS CAMBIOS");
		saveBtn.setBackground(Interface.COLOR_MAIN);
		saveBtn.setForeground(Color.WHITE);
		saveBtn.setFocusPainted(false);
		saveBtn.setFont(new Font("Montserrat", Font.PLAIN, 18));
		
		Icon gear = new ImageIcon(
				new ImageIcon(
						getClass().getResource("..//Recursos//icons//settings.png"))
							.getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));
		
		backBtn = new JButton(gear);
		backBtn.setBackground(Color.WHITE);
		backBtn.setForeground(Interface.COLOR_MAIN);
		backBtn.setPreferredSize(new Dimension(32, 32));
		
		panelOptions.add(saveBtn);
		panelOptions.add(backBtn);
		
		
		
		
		
		
		
		
		
		panelMaquina = new JPanel(new FlowLayout(FlowLayout.LEADING));
		panelAula = new JPanel(new FlowLayout(FlowLayout.LEADING));
		
		
		lblMaquina = new JLabel("   Maquina: ");
		lblMaquina.setForeground(Interface.COLOR_MAIN);
		lblMaquina.setFont(lblFont1);
		lblAula = new JLabel("  Aula: ITC -");
		lblAula.setForeground(Interface.COLOR_MAIN);
		lblAula.setFont(lblFont1);
		txtMaquina = new JTextField(10);
		txtMaquina.setText(f);
		txtMaquina.setForeground(Interface.COLOR_MAIN);
		txtMaquina.setFont(lblFont1);
		txtAula = new JTextField(10);
		txtAula.setText(a);
		txtAula.setForeground(Interface.COLOR_MAIN);
		txtAula.setFont(lblFont1);
	
		
		panelMaquina.setBackground(Interface.COLOR_BACKGROUND);
		panelMaquina.add(lblMaquina);
		panelMaquina.add(txtMaquina);
		panelAula.setBackground(Interface.COLOR_BACKGROUND);
		panelAula.add(lblAula);
		panelAula.add(txtAula);
	
		
		
		JLabel title = new JLabel("  Configuración");
		title.setFont(new Font("Oswald", Font.BOLD, 28));
		title.setForeground(Interface.COLOR_BLUE);
		//title.setBackground(Color.red);
		
		panelInfo = new JPanel(new GridLayout(0, 1));
		panelInfo.setBackground(Interface.COLOR_BACKGROUND);

	//	panelInfo.add(new JLabel());
		panelInfo.add(panelMaquina);
		panelInfo.add(new JLabel());
		panelInfo.add(panelAula);

		panelServers = new JPanel(new FlowLayout(FlowLayout.LEADING));
		panelServers.setBackground(Interface.COLOR_BACKGROUND);
	//	
		panelServers.add(title);
		panelServers.add(panelInfo);
		
		
		
		
		
		
		
		
		
		
		
		this.setLayout(new BorderLayout());
		this.add(panelServers, BorderLayout.CENTER);
		this.add(panelOptions, BorderLayout.SOUTH);
		
		
	}

	
	
	public boolean guardarConfig() {
		
		prop.setProperty("pc-id", txtMaquina.getText());
		prop.setProperty("aula", txtAula.getText());

		try (OutputStream outputStream = new FileOutputStream(new File("config.properties"))){
			prop.store(outputStream, null);
		}catch(IOException e) {
			
		}
		
		return false;
	}
	
	
	
	
}
