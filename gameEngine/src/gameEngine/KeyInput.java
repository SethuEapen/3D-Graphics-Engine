package gameEngine;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

	private Handler handler;
	
	public KeyInput(Handler handler) {
		this.handler = handler;
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		//System.out.println(key);
		
		
		if(key == KeyEvent.VK_W) Game.PvelY = -5;
		if(key == KeyEvent.VK_S) Game.PvelY = 5;
		if(key == KeyEvent.VK_A) Game.PvelX = -5;
		if(key == KeyEvent.VK_D) Game.PvelX = 5;
		if(key == KeyEvent.VK_PAGE_UP) Game.PvelZ = 15;
		if(key == KeyEvent.VK_PAGE_DOWN) Game.PvelZ = -15;
		if(key == KeyEvent.VK_RIGHT) Game.PyawVel = 5;
		if(key == KeyEvent.VK_LEFT) Game.PyawVel = -5;
		if(key == KeyEvent.VK_UP) Game.PpitchVel = 5;
		if(key == KeyEvent.VK_DOWN) Game.PpitchVel = -5;
		
		/*
		for(int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			if(key == KeyEvent.VK_W) tempObject.setVelY(-5);
			if(key == KeyEvent.VK_S) tempObject.setVelY(5);
			if(key == KeyEvent.VK_A) tempObject.setVelX(-5);
			if(key == KeyEvent.VK_D) tempObject.setVelX(5);
			if(key == KeyEvent.VK_UP) tempObject.setVelZ(5);
			if(key == KeyEvent.VK_DOWN) tempObject.setVelZ(-5);
			if(key == KeyEvent.VK_RIGHT) Game.PyawVel = 5;
			if(key == KeyEvent.VK_LEFT) Game.PyawVel = -5;
		}*/
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_W) Game.PvelY = 0;
		if(key == KeyEvent.VK_S) Game.PvelY = 0;
		if(key == KeyEvent.VK_A) Game.PvelX = 0;
		if(key == KeyEvent.VK_D) Game.PvelX = 0;
		if(key == KeyEvent.VK_PAGE_UP) Game.PvelZ = 0;
		if(key == KeyEvent.VK_PAGE_DOWN) Game.PvelZ = 0;
		if(key == KeyEvent.VK_RIGHT) Game.PyawVel = 0;
		if(key == KeyEvent.VK_LEFT) Game.PyawVel = 0;
		if(key == KeyEvent.VK_UP) Game.PpitchVel = 0;
		if(key == KeyEvent.VK_DOWN) Game.PpitchVel = 0;
		

		/*for(int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			if(key == KeyEvent.VK_W) tempObject.setVelY(0);
			if(key == KeyEvent.VK_S) tempObject.setVelY(0);
			if(key == KeyEvent.VK_A) tempObject.setVelX(0);
			if(key == KeyEvent.VK_D) tempObject.setVelX(0);
			if(key == KeyEvent.VK_UP) tempObject.setVelZ(0);
			if(key == KeyEvent.VK_DOWN) tempObject.setVelZ(0);

		}*/
		
	}
	
}
