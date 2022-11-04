package Controller;

import java.util.ArrayList;

import Model.Computer;

public class GUI_Ticker implements Runnable {

	ViewController view;
	ArrayList<Computer> data;
	MainController parent;

	public GUI_Ticker(ViewController view, ArrayList<Computer> data, MainController parent) {
		this.view = view;
		this.data = data;
		this.parent = parent;
	}

	@Override
	public void run() {
		int in = 0;
		boolean sec = true;
		while (true) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			parent.updateDataFromSocket();
			view.updatePanels(data);
			view.refreshInfoPanel();

			sec = !sec;

			// FIX TIME PRECISION
/*
			if (sec) {
				for (int i = 0; i < data.size(); i++) {
					if (data.get(i).getState() == Computer.OCCUPIED) {
					//	data.get(i).setTime(data.get(i).getTime() + 1);
					}
				}
			}
*/
		}

	}

}
