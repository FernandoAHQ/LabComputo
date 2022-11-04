package Packages;

import java.io.Serializable;

public class ReconnectionData implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2731669039545221435L;
	public final static int STATE_NONE = 500;
	public final static int STATE_AWAIT = 501;
	public final static int STATE_TIMED = 502;
	public final static int STATE_FREE = 503;
	
	private int serverState = STATE_NONE;
	private int lastTime = 0;
	private String currentUser = null;
	public ReconnectionData(int state, int time, String user) {
		this.serverState = state;
		this.lastTime = time;
		this.currentUser = user;
	}
	public int getServerState() {
		return serverState;
	}
	public void setServerState(int serverState) {
		this.serverState = serverState;
	}
	public int getLastTime() {
		return lastTime;
	}
	public void setLastTime(int lastTime) {
		this.lastTime = lastTime;
	}
	public String getCurrentUser() {
		return currentUser;
	}
	public void setCurrentUser(String currentUser) {
		this.currentUser = currentUser;
	}
	
	
	
}
