package Network;

import java.io.Serializable;

public class InitResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5046256649807355761L;
	public final static int HOUR = 1;
	public final static int HALF_HOUR = 2;
	public final static int QUARTER_HOUR = 3;
	public final static int FREE = 4;
	public final static int REJECTED = 0;

	int time;
	boolean accepted = false;

	public InitResponse(int time) {
		this.time = time;

		if (time != REJECTED)
			accepted = true;

	}

	public boolean isAccepted() {
		return accepted;
	}

	public int getTime() {
		return time;
	}
}
