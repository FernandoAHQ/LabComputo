package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

public class Interface {
	
	public static final Color COLOR_MAIN = Color.decode("#003366");
	public static final Color COLOR_BACKGROUND = Color.decode("#e7e7e8");
	public static final Color COLOR_BLUE = Color.decode("#003366");
	public static final Color COLOR_RED = Color.decode("#cb0000");
	public static final Color COLOR_GREEN = Color.GREEN;//.decode("#53FF45");
	public static final Font FONT1 = new Font("Helvetica", Font.PLAIN, 20);
	public static final Font FONT_TIMER = new Font("Helvetica", Font.BOLD, 40);
	
	static final Border BORDER_BLACK_1 = BorderFactory.createLineBorder(COLOR_BLUE, 1);
	
	static final Dimension SCREEN_DIMENSION = Toolkit.getDefaultToolkit().getScreenSize();

     // Determine the new location of the window
    
	
	
	public Interface() {
		
	}

}
