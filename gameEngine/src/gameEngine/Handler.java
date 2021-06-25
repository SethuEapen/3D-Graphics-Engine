package gameEngine;
import java.awt.Graphics;
import java.util.LinkedList;


public class Handler { //steps through all the game objects and updates them individually
	
	LinkedList<GameObject> object = new LinkedList<GameObject>();
	
	public void tick() {
		Game.sprint = Game.sprint / Game.scaling;
		
		Game.Pyaw = (Game.Pyaw - (Game.PyawVel / Game.scaling));
		
		if(Game.Pyaw >= 360) {
			Game.Pyaw = Game.Pyaw - 360;
		}else if(Game.Pyaw <= -360) {
			Game.Pyaw = Game.Pyaw + 360;
		}
		
		Game.Ppitch = (Game.Ppitch + (Game.PpitchVel / Game.scaling));
		
		if(Game.Ppitch >= 360) {
			Game.Ppitch = Game.Ppitch -360;
		}else if(Game.Ppitch <= -360) {
			Game.Ppitch = Game.Ppitch + 360;
		}
		
				
		double modVelZ = Math.sin(Math.toRadians(90) + Math.toRadians(Game.Pyaw)) * Game.PvelZ;
		double modVelX = Math.cos(Math.toRadians(90) + Math.toRadians(Game.Pyaw)) * Game.PvelZ;
		
		modVelZ = modVelZ + (Math.cos(Math.toRadians(90) - Math.toRadians(Game.Pyaw)) * Game.PvelX);
		modVelX = modVelX + (Math.sin(Math.toRadians(90) - Math.toRadians(Game.Pyaw)) * Game.PvelX);

		//double modVelY = Math.sin(Math.toRadians(Game.Ppitch)) * modVelZ;
		//modVelZ = Math.cos(Math.toRadians(Game.Pyaw)) * modVelZ;
		
		//modVelZ = modVelZ + (Math.sin(Math.toRadians(-Game.Ppitch)) * Game.PvelY);
		//modVelY = modVelY - (Math.cos(Math.toRadians(-Game.Ppitch)) * Game.PvelY);
		
		
		Game.playerX = (Game.playerX + (modVelX * Game.sprint));//Game.PvelX;
		Game.playerY = (Game.playerY + (Game.PvelY * Game.sprint));
		Game.playerZ = (Game.playerZ + (modVelZ * Game.sprint));//Game.PvelZ;
		
		
		for(int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);
			tempObject.tick();
		}
		
		Game.sprint = Game.sprint * Game.scaling;

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
