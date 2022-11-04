package Model;

import org.json.simple.JSONObject;

public class Aula {
	
	String numeroAula;
	int cols;
	int rows;
	
	public Aula(JSONObject aula) {
		
		this.numeroAula = (String) aula.get("aula");
		this.rows = (int)((long) Integer.parseInt((String) aula.get("rows")));
		this.cols = (int)((long) Integer.parseInt((String) aula.get("cols")));
		}

	
	public int getCols() {
		return cols;
	}
	
	public String getAula() {
		return numeroAula;
	}
}
