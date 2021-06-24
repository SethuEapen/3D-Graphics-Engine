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
	int lastX = 99999;
	int lastY = 99999;
	int currentX;
	int currentY;
	double sensitivity = .5;
	
	public MouseInput(Handler handler, Window window) {
		this.handler = handler;
		this.window = window;
		try {
			robot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public void mouseMoved(MouseEvent e) {
		if(Game.paused == false) {
			if(lastX == 99999) {
				robot.mouseMove(Game.monitorX, Game.monitorY);
				lastX = Game.monitorX;
				lastY = Game.monitorY;
			}
			else {
				currentX = e.getXOnScreen();
				currentY = e.getYOnScreen();
				System.out.println(currentX + ", " + currentY);
				Game.Pyaw += (int) ((currentX - lastX) * sensitivity);
				Game.Ppitch += -(int) ((currentY - lastY) * sensitivity);
				//robot.mouseMove(Game.monitorX, Game.monitorY);
				lastX = currentX;//Game.monitorX;
				lastY = currentY;//Game.monitorY;				
			}
		}
	}


	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
