package gameEngine;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class Handler { //steps through all the game objects and updates them individually
	
	ArrayList<GameObject> object = new ArrayList<GameObject>();
	ArrayList<GameObject> sortedRender = new ArrayList<GameObject>();
	public static int numFinished = 0;
	
	
	ExecutorService pool = Executors.newFixedThreadPool(100);

	int i = 1;
	
	public void tick() {
		//System.out.println(i);
		//i++;
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

				
		Game.playerX = (Game.playerX + (modVelX * Game.sprint));//Game.PvelX;
		Game.playerY = (Game.playerY + (Game.PvelY * Game.sprint));
		Game.playerZ = (Game.playerZ + (modVelZ * Game.sprint));//Game.PvelZ;
		
		sortedRender.clear();


		Handler.numFinished = 0;


		
		for (int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);
			//tempObject.tick();
			pool.execute(tempObject);
		}
		
		while(numFinished < object.size()){
			try{
			 Thread.sleep(2);
			} 
			catch(Exception e) {

			}
		}
		numFinished = 0;

		//while(Handler.numFinished <= (object.size()-1)) {
			//System.out.println(numFinished + " v.s. " + (object.size()-1));
			

		//}
		
		
		Handler.numFinished = 0;
		
		int asdf = object.size();
	//	System.out.println("Objects size: " + asdf);

		
		for (int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);
			if(tempObject.normalDistance != -99999999) {
				sortedRender.add(tempObject);
			}	
		}
		
		
		Game.sprint = Game.sprint * Game.scaling;
	}
	
	
	public void render(Graphics g) {
		//Collections.sort(sortedRender);
		for (GameObject tempObject : sortedRender) {
			tempObject.render(g);
		}
	}
	
	public void addObject(GameObject object) {
		this.object.add(object);
	}
	
	public void removeObject(GameObject object) {
		this.object.remove(object);
	}

/*
	public ExecutorService getPool() {
		return pool;
	}


	public void setPool(ExecutorService pool) {
		this.pool = pool;
	}
*/
}

//double modVelY = Math.sin(Math.toRadians(Game.Ppitch)) * modVelZ;
		//modVelZ = Math.cos(Math.toRadians(Game.Pyaw)) * modVelZ;
		
		//modVelZ = modVelZ + (Math.sin(Math.toRadians(-Game.Ppitch)) * Game.PvelY);
		//modVelY = modVelY - (Math.cos(Math.toRadians(-Game.Ppitch)) * Game.PvelY);
