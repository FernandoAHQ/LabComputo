package Model;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ComputerModel {

	ArrayList<Computer> computerDataArray;

	public ComputerModel(JSONArray computadoras) {
		computerDataArray = new ArrayList<Computer>();
		
		int counter = 0;
		
		for(int i = 0; i < computadoras.size(); i++) {
			
			for(int j = 0; j < ((JSONArray)((JSONObject)computadoras.get(i)).get("maquinas")).size(); j++) {
				importPc(
						counter, (JSONObject)((JSONArray)((JSONObject)computadoras.get(i)).get("maquinas")).get(j), 
						Integer.parseInt((String) ((JSONObject)computadoras.get(i)).get("aula")));
			}
			

		//	importPc(i, (JSONObject)computadoras.get(i));
		}
		
	//	computadoras.forEach(comp -> importPc((JSONObject) comp));
	}

	private void importPc(int index, JSONObject comp, int lab) {
		computerDataArray.add(new Computer(index, (String)comp.get("folio"), lab));
//		computerDataArray.add(new Computer((String)comp.get("pc_id"));
	}

	public ArrayList<Computer> getComputerDataArray() {
		return computerDataArray;
	}

	public void requestSession(int pcIndex) {
		computerDataArray.get(pcIndex).requestSession();
		
	}

	public void setSessionRequest(int i, boolean b) {
		// TODO Auto-generated method stub
		computerDataArray.get(i).setSessionRequested(b);
		
	}

	public void setConnectionIndex(int pcIndex, int connectionIndex) {
		// TODO Auto-generated method stub
		computerDataArray.get(pcIndex).setConnectionIndex(connectionIndex);
		
	}

	public void setRequest(int pcIndex, int request) {
		// TODO Auto-generated method stub
		computerDataArray.get(pcIndex).setRequest(request);
		
	}

	public void setTimeRequested(int pcIndex, boolean b) {
		// TODO Auto-generated method stub
		computerDataArray.get(pcIndex).setTimeRequested(b);
	}

}
