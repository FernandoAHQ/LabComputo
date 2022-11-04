package Model;

public class Computer {

	public boolean isSessionRequested() {
		return sessionRequested;
	}

	public void setSessionRequested(boolean sessionRequested) {
		this.sessionRequested = sessionRequested;
	}

	public boolean isTimeRequested() {
		return timeRequested;
	}

	public void setTimeRequested(boolean timeRequested) {
		this.timeRequested = timeRequested;
		if(!timeRequested) request = -1;
	}

	public static final int AVAILABLE = 0;
	public static final int OCCUPIED = 1;
	public static final int DISCONNECTED = 2;

	int pcIndex, connectionIndex = -1, panelIndex;
	int state = DISCONNECTED;
	int time = 0;
	int start = 0;
	int request = -1;
	String folio;
	int lab;
	String user = "";
	String control = "";
	private boolean sessionRequested = false, timeRequested = false;
	

	public Computer(int index, String folio, int lab_id) {
		this.pcIndex = index;
		this.folio = folio;
		this.lab = lab_id;
	}
	
	public void reset() {
		connectionIndex = -1;
		state = DISCONNECTED;
		time = 0;
		control = "";
		user = "";
		start = 0;
		setSessionRequested(false);
		setTimeRequested(false);
	}
	
	public void setPanelIndex(int i) {
		this.panelIndex = i;
	}
	
	public int getPanelIndex() {
		return panelIndex;
	}
	
	public String getFolio() {
		return folio;
	}
	
	public void setFolio(String folio) {
		this.folio = folio;
	}
	
	public int getPcIndex() {
		return pcIndex;
	}

	public void setPcIndex(int id) {
		this.pcIndex = id;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public String getControl() {
		return control;
	}

	public void setControl(String control) {
		this.control = control;
	}
	
	public int getLab() {
		return lab;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getRequest() {
		return request;
	}

	public void setRequest(int request) {
		System.out.println("1 Computer - request: " + this.request);
		this.request = request;
		System.out.println("2 Computer - request: " + this.request);
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void requestTime() {
		this.timeRequested = true;
	}
	
	public void requestSession() {
		this.sessionRequested  = true;
		
	}
	
	public int getConnectionIndex() {
		return connectionIndex;
	}

	public void setConnectionIndex(int i) {
		connectionIndex = i;
	}

	public int getTimeRequested() {
		// TODO Auto-generated method stub
		return request;
	}

}
