package Network;

import java.io.Serializable;

public class LoginPackage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1614195227723368824L;
	private String id;
	private String password;

	public LoginPackage(String id, String password) {
		this.id = id;
		this.password = password;
	}

	public String getID() {
		return id;
	}

	public String getPassword() {
		return password;
	}
}
