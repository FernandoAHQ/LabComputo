package Model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ConfigModel {
	JSONParser jsonParser;
	JSONArray computadoras;
	Properties prop;
	int dash_rows;
	int dash_cols;
	
	ArrayList<Aula> aulas;

	public ConfigModel() {

		prop = new Properties();

		try {
			FileInputStream fis = new FileInputStream(new File("dashboard.properties"));
			prop.load(fis);
			// fis.close();
			// retrieveFile("dashboard.properties"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// System.out.println(prop.getProperty("rows"));
		dash_rows = Integer.parseInt(prop.getProperty("rows"));
		dash_cols = Integer.parseInt(prop.getProperty("cols"));

		jsonParser = new JSONParser();

		FileReader reader;
		try {
			reader = new FileReader("computers.json");
			Object obj;
			try {
				obj = jsonParser.parse(reader);
				computadoras = (JSONArray) obj;
				System.out.println(computadoras);
				aulas = new ArrayList<Aula>();
				
				for(int i = 0; i < computadoras.size(); i++) {
					aulas.add(new Aula((JSONObject)computadoras.get(i)));
				}

			} catch (IOException | ParseException e) {
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
	
	public ArrayList<Aula> getAulas(){
		return aulas;
	}

	private String retrieveFile(String s) {
		String url = String.valueOf(getClass().getResource("../" + s));
		String url2 = "";
		for (int ind = 6; ind < url.length(); ind++)
			url2 += url.charAt(ind);
		System.out.println(url2);
		return url2;
	}

	public JSONArray getComputadoras() {
		return computadoras;
	}

	public int getRows() {
		return dash_rows;
	}

	public int getCols() {
		return dash_cols;
	}
}
