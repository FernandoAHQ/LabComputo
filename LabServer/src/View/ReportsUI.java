package View;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;


import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.TimePicker;

import Controller.ViewController;
import Model.DataModel;

import java.awt.event.MouseAdapter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class ReportsUI extends JPanel implements ActionListener{
	
	ActionListener al;
	Font fontLbl, fontTitulo, fontTF;
	
	JLabel lblTitulo, lblFecha, lblFecha2, lblHora, lblHora2, lblAula, lblFolio, lblControl, lblCheck1, lblCheck2;
	JTextField tfFolio, tfControl;
	JComboBox cAula;
	JCheckBox checkIp, checkEstudiante;
	
	ViewController view;
	DataModel database;
	
	DatePicker dateA, dateB;
	TimePicker timeA, timeB;
	
	JPanel filterPanel, panelFecha, panelHora, panelEquipo, panelControl, panelIp, panelBuscar, panelTop, panelBot;
	
	
	JButton btnBuscar, btnPrint;
	
	JTable table;
	DefaultTableModel modelo;
	
	public ReportsUI(ViewController view, DataModel database) {
		this.view = view;
		this.database = database;
		//this.al = al;
		initComponents();
	}
	
	private void initComponents() {
		 
		this.setBackground(Color.WHITE);
		
		Color color = Color.decode("#eeeeee");
		panelFecha = new JPanel();
		panelFecha.setLayout(new FlowLayout(FlowLayout.LEADING, 20, 10));
		panelFecha.setBackground(color);
		panelHora= new JPanel();
		panelHora.setLayout(new FlowLayout(FlowLayout.LEADING, 20, 10));
		panelHora.setBackground(color);
		panelEquipo= new JPanel();
		panelEquipo.setLayout(new FlowLayout(FlowLayout.LEADING, 20, 10));
		panelEquipo.setBackground(color);
		panelControl = new JPanel();
		panelControl.setLayout(new FlowLayout(FlowLayout.LEADING, 20, 10));
		panelControl.setBackground(color);
		panelIp= new JPanel();
		panelIp.setLayout(new FlowLayout(FlowLayout.LEADING, 20, 10));
		panelIp.setBackground(color);
		panelBuscar = new JPanel();
		panelBuscar.setBackground(color);
		//panelBuscar.setLayout(new GridLayout(3, 2));
		
		filterPanel = new JPanel();
		filterPanel.setBackground(Interface.ACCENT1);
		filterPanel.setLayout(new GridLayout(2, 2));//3
		//filterPanel.setPreferredSize(new Dimension(800, 40));
		
		fontTitulo = new Font("Arial", Font.BOLD, 16);
		fontLbl = new Font("Arial", Font.BOLD, 15);
		fontTF = new Font("Arial", Font.PLAIN, 16);

		
		lblTitulo = new JLabel("Titulo");
		lblTitulo.setFont(fontTitulo);
		
		lblFecha = new JLabel("Fecha");
		lblFecha.setFont(fontLbl);
	
		lblFecha2 = new JLabel("-");
		lblFecha2.setFont(fontTitulo);
	
		lblHora = new JLabel("Hora");
		lblHora.setFont(fontLbl);
		
		lblHora2 = new JLabel("-");
		lblHora2.setFont(fontTitulo);
		
		lblAula = new JLabel("Aula");
		lblAula.setFont(fontLbl);
		
		lblFolio = new JLabel("Folio");
		lblFolio.setFont(fontLbl);
		
		lblControl = new JLabel("No. Control");
		lblControl.setFont(fontLbl);
		
		lblCheck1 = new JLabel("IP");
		lblCheck1.setFont(fontLbl);
		
		lblCheck2 = new JLabel("Estudiante");
		lblCheck2.setFont(fontLbl);
		
		String aulas[]={"Todas","Aula 0","Aula 1","Aula 2","Aula 3", "Aula 4"};        
		cAula=new JComboBox(aulas);  
	    cAula.setFont(new Font("Arial", Font.PLAIN, 12));
	   
		
		tfFolio = new JTextField(4);
		tfFolio.setFont(fontTF);
		
		tfControl = new JTextField(9);
		tfControl.setFont(fontTF);

		checkIp = new JCheckBox("Dirección IP");
		checkEstudiante = new JCheckBox("Estudiante");
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.setFont(fontTF);
		btnBuscar.addActionListener(this);
	
		
		dateA = new DatePicker();
		dateA.setDateToToday();
		dateB = new DatePicker();
		dateB.setDateToToday();
		
		timeA = new TimePicker();
		timeA.setTime(LocalTime.MIN);
		timeB = new TimePicker();
		timeB.setTime(LocalTime.MAX);
		
		
		panelFecha.add(dateA);
		panelFecha.add(lblFecha2);
		panelFecha.add(dateB);
		//filterPanel.add(new JLabel("          "));
		panelHora.add(timeA);
		panelHora.add(lblHora2);
		panelHora.add(timeB);
		//
		panelEquipo.add(lblAula);
		panelEquipo.add(cAula);
		panelEquipo.add(new JLabel("    "));
		panelEquipo.add(lblFolio);
		panelEquipo.add(tfFolio);
		
		panelControl.add(lblControl);
		panelControl.add(tfControl);
		
		panelHora.add(checkIp);
		panelControl.add(checkEstudiante);
		
		panelBuscar.add(btnBuscar);
		
		panelTop = new JPanel();
		panelTop.setLayout(new FlowLayout(FlowLayout.LEADING, 40, 10));
		
		filterPanel.add(panelFecha);
		filterPanel.add(panelHora);

		filterPanel.add(panelEquipo);
		filterPanel.add(panelControl);
		panelTop.add(filterPanel);
		panelTop.add(panelBuscar);
		panelTop.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.decode("#b4b4b4")));
		
		
		this.setLayout(new BorderLayout());
		this.add(panelTop, BorderLayout.NORTH);
		
	
		
	        // Column Names
	        String[] columnNames = { "Fecha", "Inicio", "Final", "Computadora", "Dirección IP", "No. Control", "Alumno" };
	     
	     modelo = new DefaultTableModel();
	     modelo.addColumn("Fecha");
	     modelo.addColumn("Inicio");
	     modelo.addColumn("Final");
	     modelo.addColumn("Aula");
	     modelo.addColumn("Computadora");
	     modelo.addColumn("Dirección IP");
	     modelo.addColumn("No. Control");
	     modelo.addColumn("Usuario");
	
		 table = new JTable(modelo);
	
	 
		 table.setEnabled(false);
		 //table.setAutoResizeMode(JTable.);
	    // table.setBounds(30, 40, 1600, 300);
	 
	        // adding it to JScrollPane
		 

	     btnPrint = new JButton("CREAR PDF");
	     btnPrint.addActionListener(this);
		 panelBot = new JPanel(new FlowLayout(FlowLayout.LEADING, 20, 20));
		 panelBot.add(btnPrint);
		 
		this.add(new JScrollPane(table), BorderLayout.CENTER);
		
		this.add(panelBot, BorderLayout.SOUTH);
		
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(btnBuscar)) {
			modelo.setRowCount(0);
				//System.out.println("Search xd");
			String fechaI = dateA.getDate().toString();
			String fechaF = dateB.getDate().toString();
			String horaI = timeA.getTime().toString();
			String horaF = timeB.getTime().toString();
			int aula = cAula.getSelectedIndex()-1;
			String folio = tfFolio.getText().strip();
			String control = tfControl.getText().strip();
			boolean ip = checkIp.isSelected();
			boolean ctrl = checkEstudiante.isSelected();
			int id = -1;
			
			String queryId;
			if(!control.equals("")) {
				id = database.getId(control);
				if(id==-1) {
					System.out.println("INVALID CONTROL");
					return;
				}
			}

			
			
			String query = "SELECT Sesiones.fecha, Sesiones.hora_inicial, Sesiones.hora_final, Sesiones.folio, Sesiones.aula, Sesiones.ip, Sesiones.id_estudiante, "
					+ "Estudiantes.no_control, Estudiantes.nombre, Estudiantes.apellidos"
					+ " FROM Sesiones ";
			query += "JOIN Estudiantes ON Sesiones.id_estudiante = Estudiantes.id_estudiante";

					query += " WHERE fecha >= '" + fechaI+ "' AND fecha <= '" + fechaF + "' AND "
					+ "hora_inicial > '" + horaI + "' AND hora_inicial < '" + horaF + "' ";
			
			if(aula != -1) query = query + " AND aula = " + aula + " ";
			if(!folio.equals("")) query += " AND folio = '" + folio + "' ";
			if(!control.equals(" ".trim()) && id != -1) query += "AND Sesiones.id_estudiante = " + id;
			
			
			System.out.println(query);
			  
			ResultSet res = database.consultar(query);
			System.out.println(res);
		   //  if(id!=-1) control = database.getControl(id);
		     
		try {
			int c = 0;
			while(res.next()) {
				
				
				String[] data = {
						res.getString("fecha"),
						res.getString("hora_inicial"),
						res.getString("hora_final"),
						res.getInt("aula")+"",
						"ITC - " + res.getString("folio"),
						"" + res.getString("ip"),
						""+res.getString("no_control"),
						""+res.getString("nombre") + " " + res.getString("apellidos")
				};
				
				

				
				modelo.addRow(data);
				System.out.println("");
			}
			
			
		}catch(SQLException sql) {
			sql.printStackTrace();
		}
			
			
			
			
		}else if(e.getSource().equals(btnPrint)) {
			PDFGenerator printer = new PDFGenerator();
			printer.generarPDF(modelo);
			
			//Viewer viewer = new Viewer();
			//viewer.setupViewer();
			//viewer.executeCommand(Commands.OPENFILE, new Object[]{"reportes.pdf"});
			//new pdfViewer("reportes.pdf");
				
		}
		
	}

}
