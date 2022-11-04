package Packages;

import java.io.Serializable;

public class ClientRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8597660425526379850L;
	public final static int CLOSE = 0;
	public final static int PING = 1;
	public final static int EXTENSION = 2;
	public final static int LOGIN = 3;
	public final static int RESEND = 4;
	public final static int RECONNECT = 11;

	public final static int REQUEST_QUARTER = 5;
	public final static int REQUEST_HALF = 6;
	public final static int REQUEST_HOUR = 7;
	public static final int REQUEST_REJECTED = 10;

	boolean state;
	int type;
	int request;

	String password;
	String user;
	
	ReconnectionData session;

	public ClientRequest(int type, ReconnectionData session) {
		this.type = type;
		this.session = session;
	}
	
	public ClientRequest(int type, boolean state) {
		this.type = type;
		this.state = state;
	}

	public ClientRequest(int type, boolean state, int request) {
		this.type = type;
		this.state = state;
		this.request = request;
	}

	public ClientRequest(int type, boolean state, String user, String password) {
		this.type = type;
		this.state = state;
		this.user = user;
		this.password = password;
	}

	public boolean getState() {
		return state;
	}

	public int getType() {
		return type;
	}

	public int getRequest() {
		return request;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}
	
	public ReconnectionData getSession() {
		return session;
	}

}
