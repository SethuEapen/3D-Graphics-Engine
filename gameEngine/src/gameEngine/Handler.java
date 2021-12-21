package gameEngine;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.annotation.processing.SupportedOptions;


public class Handler { //steps through all the game objects and updates them individually
	
	ArrayList<ItemPair> pairs = new ArrayList<ItemPair>();
	ArrayList<ItemPair> sortedRender = new ArrayList<ItemPair>();
	ArrayList<ItemPair> tempRender = new ArrayList<ItemPair>();
	int sorting = 0;
	public static int numFinished = 0;
	
	
	ExecutorService pool = Executors.newFixedThreadPool(8);
	List<Future<?>> futures = new ArrayList<Future<?>>();
	ExecutorService sortingThread = Executors.newFixedThreadPool(8);


	int i = 1;
	
	public void tick() {
		//Movment Updating

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
		
		//Ended Movment 
		//TickQueuing

		//sortedRender.clear();

		if (sortedRender.isEmpty()) {
			for (int i = 0; i < pairs.size(); i++) {// Set clone items into sorted render and excecute all of the ticks
				ItemPair tempPair = pairs.get(i);
				for(int j = 0; j < tempPair.list.size(); j++){
					GameObject tempObject = tempPair.list.get(j);
					pool.execute(tempObject);
				}
				sortedRender.add(tempPair);
			}
		} else if (sorting == 0) {
			sorting = 1;
			tempRender = new ArrayList<ItemPair>(sortedRender);
			for (int i = 0; i < sortedRender.size(); i++) {
				ItemPair tempPair = sortedRender.get(i);
				for(int j = 0; j < tempPair.list.size(); j++){
					GameObject tempObject = tempPair.list.get(j);
					pool.execute(tempObject);
				}
			}
			for (int i = 0; i < tempRender.size(); i++) {
				ItemPair tempPair = tempRender.get(i);
				Future<?> f = sortingThread.submit(tempPair);
    			futures.add(f);
			}
		} else {
			boolean allDone = true;
			for(Future<?> future : futures){
    			allDone &= future.isDone(); // check if future is done
			}
			if (allDone) {
				sortedRender = tempRender;
				sorting = 0;
			}
			for (int i = 0; i < sortedRender.size(); i++) {
				ItemPair tempPair = sortedRender.get(i);
				for(int j = 0; j < tempPair.list.size(); j++){
					GameObject tempObject = tempPair.list.get(j);
					pool.execute(tempObject);
				}
			}
		}
		
		
		numFinished = 0;

		Game.sprint = Game.sprint * Game.scaling;
	}
	
	
	public void render(Graphics g) {
		for (int i = 0; i < sortedRender.size(); i++) {
			ItemPair tempPair = sortedRender.get(i);
			for(int j = 0; j < tempPair.list.size(); j++){
				GameObject tempObject = tempPair.list.get(j);
				tempObject.render(g);

			}	
		}
		//Collections.sort(sortedRender);
		//for (GameObject tempObject : sortedRender) {
		//	tempObject.render(g);
		//}
	}
	
	public void addPair(ItemPair pair) {
		this.pairs.add(pair);
	}
	
	public void removeObject(GameObject pair) {
		this.pairs.remove(pair);
	}
}