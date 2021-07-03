package gameEngine;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;



public class MouseInput extends MouseAdapter {
	
	Handler handler;
	Window window;
	Robot robot;
	int lastX;
	int lastY;
	int currentX;
	int currentY;
	double sensitivity = .05;
	
	public MouseInput(Handler handler, Window window) {
		this.handler = handler;
		this.window = window;
		try {
			robot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		robot.mouseMove(Game.monitorX, Game.monitorY);
		lastX = Game.monitorX;
		lastY = Game.monitorY;
	}


	@Override
	public void mouseMoved(MouseEvent e) {
		checkMove(e);
	}


	@Override
	public void mouseDragged(MouseEvent e) {
		checkMove(e);
	}
	
	private void checkMove(MouseEvent e) {
		if(Game.paused == false) {
			currentX = e.getXOnScreen();
			currentY = e.getYOnScreen();

			Game.Pyaw -= ((currentX - lastX) * sensitivity);
			Game.Ppitch -= ((currentY - lastY) * sensitivity);
			robot.mouseMove(Game.monitorX, Game.monitorY);
			
			lastX = Game.monitorX;//Game.monitorX;
			lastY = Game.monitorY;//Game.monitorY;		
		}
	}

}
