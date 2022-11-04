package Packages;

import java.io.Serializable;

public class ServerResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1705900292698488568L;
	public final static int REQUEST_QUARTER = 5;
	public final static int REQUEST_HALF = 6;
	public final static int REQUEST_HOUR = 7;
	public static final int REQUEST_REJECTED = 8;

	public static final int ACCESS_GRANTED = 10;
	public final static int WRONG_CREDENTIALS = 11;
	public final static int NOT_REGISTERED = 12;

	public final static int CLOSE = 100;
	public final static int PING = 101;
	public final static int REQUEST = 102;
	public final static int LOGIN = 103;
	public final static int RESEND = 104;
	public final static int INIT = 105;

	int type;
	boolean confirmation;
	int requestAnswer;

	public ServerResponse(int type, boolean confirmation) {
		this.type = type;
		this.confirmation = confirmation;
	}

	public ServerResponse(int type, int request) {
		this.type = type;
		this.requestAnswer = request;
	}

	public int getRequestType() {
		return type;
	}

	public boolean getConfirmation() {
		return confirmation;
	}

	public int getRequestAnswer() {
		return requestAnswer;
	}

}
