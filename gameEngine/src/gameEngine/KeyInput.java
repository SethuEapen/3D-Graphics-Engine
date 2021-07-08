package gameEngine;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

	//private Handler handler;
	
	Window window;
	
	public KeyInput(Handler handler, Window window) {
		//this.handler = handler;
		this.window = window;
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		//System.out.println(key);
		
		if(key == KeyEvent.VK_SHIFT) Game.sprint = 2 ;/// (Game.FPS/Game.scaling);
		if(key == KeyEvent.VK_CONTROL) Game.sprint = 0.5 ;/// (Game.FPS/Game.scaling);
		if(key == KeyEvent.VK_W) Game.PvelZ = 5;
		if(key == KeyEvent.VK_S) Game.PvelZ = -5;
		if(key == KeyEvent.VK_A) Game.PvelX = -5;
		if(key == KeyEvent.VK_D) Game.PvelX = 5;
		if(key == KeyEvent.VK_PAGE_UP) Game.PvelY = 5;
		if(key == KeyEvent.VK_PAGE_DOWN) Game.PvelY = -5;
		if(key == KeyEvent.VK_RIGHT) Game.PyawVel = 5;
		if(key == KeyEvent.VK_LEFT) Game.PyawVel = -5;
		if(key == KeyEvent.VK_UP) Game.PpitchVel = 5;
		if(key == KeyEvent.VK_DOWN) Game.PpitchVel = -5;
		
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_SHIFT) Game.sprint = 1 ;/// (Game.FPS/Game.scaling);
		if(key == KeyEvent.VK_CONTROL) Game.sprint = 1 ;/// (Game.FPS/Game.scaling);
		if(key == KeyEvent.VK_W) Game.PvelZ = 0;
		if(key == KeyEvent.VK_S) Game.PvelZ = 0;
		if(key == KeyEvent.VK_A) Game.PvelX = 0;
		if(key == KeyEvent.VK_D) Game.PvelX = 0;
		if(key == KeyEvent.VK_PAGE_UP) Game.PvelY = 0;
		if(key == KeyEvent.VK_PAGE_DOWN) Game.PvelY = 0;
		if(key == KeyEvent.VK_RIGHT) Game.PyawVel = 0;
		if(key == KeyEvent.VK_LEFT) Game.PyawVel = 0;
		if(key == KeyEvent.VK_UP) Game.PpitchVel = 0;
		if(key == KeyEvent.VK_DOWN) Game.PpitchVel = 0;
		if(key == KeyEvent.VK_ESCAPE) {
			if(Game.paused == true) {
				window.setBlankCursor();
			}else {
				window.setDefaultCursor();
			}
			Game.paused = !Game.paused;
		}
		if(key == KeyEvent.VK_F11) {
			Game.dontRender = true;
			window.changeMinimize();
			Game.dontRender = false;
		}
		if(key == KeyEvent.VK_F4) System.exit(0);	
	}
}
