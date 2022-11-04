package Model;




import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.swing.JOptionPane;

/**
 *
 * @author Fernando Hernandez
 */
public class DataModel {
	Connection con;
	String driver, connectString, user, password;

	public DataModel() {
		driver = "com.mysql.jdbc.Driver";
		connectString = "jdbc:mysql://localhost/laboratorio_computo";//192.168.0.13:3306/laboratorios_computo";
		user = "root";
		password = "";

		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
		}

		conectar();

	}

	public void conectar() {
		try {
			con = DriverManager.getConnection(connectString, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Ocurrio un error con la base de datos");
			System.exit(0);
		}
	}

	public void desconectar() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private boolean isConnected() {
		if (con == null)
			return false;
		else
			return true;

	}

	public ResultSet consultar(String query) {
		ResultSet rs = null;
		try {
			rs = con.createStatement().executeQuery(query);
		} catch (Exception SEx) {
			System.out.println("CONEXION");
			SEx.printStackTrace();
			JOptionPane.showMessageDialog(null, "Ocurrio un error con la base de datos");
			System.exit(0);
		}
		return rs;
	}

	public void registrarSesion(Time inicio, int aula, String folio, String ip, String estudiante) {
		if(estudiante == null) {
			ejecutar("INSERT INTO Sesiones (hora_inicial, folio, aula, ip, id_estudiante) "
					+ "VALUES('" + inicio+"', '" +folio +"', " +aula+", '" +ip +"', 1)");		
			System.out.println("INSERT INTO Sesiones (hora_inicial, folio, aula, ip, id_estudiante) "
					+ "VALUES('" + inicio+"', '" +folio +"', " +aula+", '" +ip +"', 1"+ ")");
		}else {
			int id = -1;
			try {
				ResultSet tmp = consultar("SELECT id_estudiante FROM Estudiantes WHERE no_control = '" + estudiante+"'");
				tmp.first();
					System.out.println("WE GOT THE RESULT " + tmp.getInt("id_estudiante"));
				id = tmp.getInt("id_estudiante");
				
			} catch (SQLException e) {

			e.printStackTrace();
			}
			
			System.out.println("id = " + id);
			if(id != -1) {
			ejecutar("INSERT INTO Sesiones (hora_inicial, folio, aula, ip, id_estudiante) "
					+ "VALUES('" + inicio+"', '" +folio +"', " +aula+", '" +ip +"', '" + id+ "')");
			System.out.println("INSERT INTO Sesiones (hora_inicial, folio, aula, ip, id_estudiante) "
					+ "VALUES('" + inicio+"', '" +folio +"', " +aula+", '" +ip +"', '" + id+ "')");
			}else {
				System.out.println("WRONG ID");
			}
		}
		
	}
	

	public void registrarSesiones(Time inicio, Time fin, Date fecha, int aula, String folio, String ip, String estudiante) {
		if(true) {
			//ejecutar("INSERT INTO Sesiones (hora_inicial, hora_final, fecha,  folio, aula, ip, id_estudiante hora) "
		//			+ "VALUES('" + inicio+"', '"  + fin+"', '"  + fecha+"', '" +folio +"', " +aula+", '" +ip +"'," + estudiante + ")");		
			System.out.println("INSERT INTO Sesiones (fecha, hora_inicial, hora_final, folio, aula, ip, id_estudiante) "
					+ "VALUES('" + fecha+"', '" + inicio+"', '" + fin+"', '" +folio +"', " +aula+", '" +ip +"'," + estudiante + ");");
		}else {
			int id = -1;
			try {
				ResultSet tmp = consultar("SELECT id_estudiante FROM Estudiantes WHERE no_control = '" + estudiante+"'");
				tmp.first();
					System.out.println("WE GOT THE RESULT " + tmp.getInt("id_estudiante"));
				id = tmp.getInt("id_estudiante");
				
			} catch (SQLException e) {

			e.printStackTrace();
			}
			
			System.out.println("id = " + id);
			if(id != -1) {
			ejecutar("INSERT INTO Sesiones (hora_inicial, folio, aula, ip, id_estudiante) "
					+ "VALUES('" + inicio+"', '" +folio +"', " +aula+", '" +ip +"', '" + id+ "')");
			System.out.println("INSERT INTO Sesiones (hora_inicial, folio, aula, ip, id_estudiante) "
					+ "VALUES('" + inicio+"', '" +folio +"', " +aula+", '" +ip +"', '" + id+ "')");
			}else {
				System.out.println("WRONG ID");
			}
		}
		
	}
	
	public void ejecutar(String statement) {
		try {
			con.createStatement().execute(statement);
		} catch (Exception SEx) {
			System.out.println("STATEMENT");
			SEx.printStackTrace();
			JOptionPane.showMessageDialog(null, "Ocurrio un error con la base de datos");
			//System.exit(0);
		}
	}

	public boolean auth(String user, String pass) {
		ResultSet rs = consultar("SELECT password FROM estudiantes WHERE no_control = '" + user + "'");

		try {
			while (rs.next()) {

				if (pass.equals(rs.getString("password"))) {
					return true;
				}
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}
	
	public int getId(String control) {
		ResultSet rs = consultar ("SELECT id_estudiante FROM Estudiantes WHERE no_control = '" + control + "'");
		 
			try {
				while(rs.next()) return rs.getInt("id_estudiante");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return -1;
	}

	public String getName(String user2) {
		// TODO Auto-generated method stub
		ResultSet rs = consultar("SELECT * FROM estudiantes WHERE no_control = '" + user2 + "'");

		try {
			while (rs.next()) {
				return (rs.getString("nombre") + " " + rs.getString("apellidos"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
}