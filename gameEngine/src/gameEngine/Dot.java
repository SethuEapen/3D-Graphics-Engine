package gameEngine;

import java.awt.Color;
import java.awt.Graphics;

public class Dot extends GameObject implements Runnable {

	int centerX = Game.FRAME_WIDTH/2;
	int centerY = Game.FRAME_HEIGHT/2;
	
	Color color;
	
	double distXN;
	double distYN;
	double distZN;
	
	int distX;
	int distY;
	int distZ;
	
	public Dot(int x, int y, int z, Color color){
		super(x, y, z);
		this.color = color;
	}
	
	@Override
	public void run() {
		tick();
	}
	
	@Override
	public void tick() {
		distXN = x - Game.playerX;
		distYN = Game.playerY - y;
		distZN = z - Game.playerZ;
		
		int[] pair = modValuesYaw(distXN, distZN);
		distX = pair[0];
		distZ = pair[1];
		
		pair = modValuesPitch(distYN, distZ);
		distY = pair[0];
		distZ = pair[1];
		
		if(distZ > 0) {
			normalDistance = (int) Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2) + Math.pow(distZ, 2));
		}else {
			normalDistance = -99999999;
		}
		
	}

	@Override
	public void render(Graphics g) {
		
		
		g.setColor(color);
		
		double largeChord = Math.tan(Math.toRadians(Game.FOV/2))*distZ;

		int drawX = (int) (centerX + (Game.FRAME_WIDTH * (distX/largeChord)));
		int drawY = (int) (centerY + (Game.FRAME_WIDTH * (distY/largeChord)));

		drawPoint(g, drawX, drawY);
		
	}
	
	public int[] modValuesYaw(double distDir, double distDepth) {
		double hyp = Math.sqrt(Math.pow(distDir, 2) + Math.pow(distDepth, 2));
		double theta1 = Math.toRadians(Game.Pyaw);
		double theta2 = Math.atan(distDir/distDepth);//find out if this is negitive or positive
		if(distDepth < 0) {
			theta2 = theta2 + Math.toRadians(180);
		}	
		int[] output = new int[2];
		
		output[0] = (int)(Math.sin(theta1 + theta2) * hyp);
		output[1] = (int)(Math.cos(theta1 + theta2) * hyp);
		
		return output;
	}
	
	public int[] modValuesPitch(double distDir, double distDepth) {
		double hyp = Math.sqrt(Math.pow(distDir, 2) + Math.pow(distDepth, 2));
		double theta1 = Math.toRadians(Game.Ppitch);
		double theta2 = Math.atan(distDir/distDepth);
		if(distDepth < 0) {
			theta2 = theta2 + Math.toRadians(180);
		}	
		int[] output = new int[2];
		
		output[0] = (int)(Math.sin(theta1 + theta2) * hyp);
		output[1] = (int)(Math.cos(theta1 + theta2) * hyp);
		
		return output;
	}
	
	public void drawPoint(Graphics g, int x, int y) {
		g.fillOval(x - 3, y - 3, 5, 5);
	}


	@Override
	public int compareTo(GameObject o) {
		return o.normalDistance - this.normalDistance;
	}

	
	
}


