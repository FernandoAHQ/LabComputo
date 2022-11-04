package Network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Pinger implements Runnable {

	Socket socket;
	ObjectOutputStream out;
	ObjectInputStream in;

	boolean pingResponse;

	public Pinger(Socket socket, ObjectOutputStream out, ObjectInputStream in) {
		this.socket = socket;
		this.out = out;
		this.in = in;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.out.println("PROBLEM WITH THREAD SLEEPING");
		}

		try {
			pingResponse = (Boolean) in.readObject();

			if (!pingResponse) {

			}

		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
