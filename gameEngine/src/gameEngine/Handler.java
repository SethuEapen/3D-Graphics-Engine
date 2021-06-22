package gameEngine;
import java.awt.Graphics;
import java.util.LinkedList;


public class Handler { //steps through all the game objects and updates them individually
	
	LinkedList<GameObject> object = new LinkedList<GameObject>();
	int count = 0;
	
	public void tick() {
		Game.playerX = Game.playerX + Game.PvelX;
		Game.playerY = Game.playerY + Game.PvelY;
		Game.playerZ = Game.playerZ + Game.PvelZ;
		Game.Pyaw = Game.Pyaw + Game.PyawVel;
		Game.Ppitch = Game.Ppitch + Game.PpitchVel;
		
		
		
		for(int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);
			tempObject.tick();
		}
	}
	
	
	public void render(Graphics g) {
		for(int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);
			tempObject.render(g);
		}
	}
	
	public void addObject(GameObject object) {
		this.object.add(object);
	}
	
	public void removeObject(GameObject object) {
		this.object.remove(object);
	}

}
