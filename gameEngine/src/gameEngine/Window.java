package gameEngine;
import java.awt.Canvas;
import java.awt.Cursor;
//import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
//import java.awt.Point;
//import java.awt.Toolkit;
//import java.awt.image.BufferedImage;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;



public class Window extends Canvas { //creates the window


	private static final long serialVersionUID = 1486302857761007007L;

	/**
	 * 
	 */
	
	boolean minimized = false;
	
	JFrame frame;
	Game game;
	String title;
	
	BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
	Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
		    cursorImg, new Point(0, 0), "blank cursor");
	
	public Window(int width, int height, String title, Game game) {//create window object
		this.game = game;
		this.title = title;
		
		//Create JFrame
		frame = new JFrame(title);
		
		//frame.setPreferredSize(new Dimension(width, height));
		//frame.setSize(new Dimension(width, height));
		
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		//frame.setUndecorated(true);
		
		//frame.setMaximumSize(new Dimension(width, height));
		//frame.setMinimumSize(new Dimension(width, height));
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);		
		
		frame.add(game);
		
		
		frame.setVisible(true);

		setBlankCursor();

	
		game.start();
	}
	
	public void setBlankCursor() {
		game.setCursor(blankCursor);
	}
	
	public void setDefaultCursor() {
		game.setCursor(Cursor.getDefaultCursor());
	}

	public void changeMinimize() {
		frame.setVisible(false);
		frame.dispose();
		Dimension size;
		if(minimized) {
			frame.setUndecorated(true);
			size = Toolkit.getDefaultToolkit().getScreenSize();

		} else {
			frame.setUndecorated(false);
			size = frame.getContentPane().getSize();
		}
		Game.FRAME_WIDTH = size.width;
		Game.FRAME_HEIGHT = size.height;
		minimized = !minimized;
		frame.setVisible(true);
	}
		
}
