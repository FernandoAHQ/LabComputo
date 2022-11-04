package Timer;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import GUI.TimerGUI;

public class TimerThread extends Thread {

	TimerGUI parent;

	public TimerThread(TimerGUI parent) {
		this.parent = parent;

	}

	@Override
	public void run() {
		while (true) {
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			parent.updateTime();
		}
	}
}
