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
	
	JFrame frame;
	
	Game game;
	
	BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
	Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
		    cursorImg, new Point(0, 0), "blank cursor");
	
	public Window(int width, int height, String title, Game game) {//create window object
		this.game = game;
		
		//Create JFrame
		frame = new JFrame(title);
		
		frame.setPreferredSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);		
		
		frame.setVisible(true);

		
		frame.add(game);
		
		
		setBlankCursor();

	
		game.start();
	}
	
	public void setBlankCursor() {
		game.setCursor(blankCursor);
	}
	
	public void setDefaultCursor() {
		game.setCursor(Cursor.getDefaultCursor());
	}
		
}
