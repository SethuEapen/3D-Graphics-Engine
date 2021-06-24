package gameEngine;

import java.awt.Color;
import java.awt.Graphics;

public class Point extends GameObject {

	int centerX = Game.FRAME_WIDTH/2;
	int centerY = Game.FRAME_HEIGHT/2;
	Color color;
	
	public Point(int x, int y, int z, Color color){
		super(x, y, z);
		this.color = color;
	}
	
	@Override
	public void tick() {
		//x = x - velX;
		//y = y - velY;	
		//z = z - velZ;
	}

	@Override
	public void render(Graphics g) {
		g.setColor(color);
		
		double distXN = x - Game.playerX;
		double distYN = y - Game.playerY;
		double distZN = z - Game.playerZ;
		
		int[] pair = modValuesYaw(distXN, distZN);
		int distX = pair[0];
		int distZ = pair[1];
		
		pair = modValuesPitch(distYN, distZ);
		int distY = pair[0];
		distZ = pair[1];
		
		
		if(distZ > 0) {
			double largeChord = Math.tan(Math.toRadians(Game.FOV/2))*distZ;

			int drawX = (int) (centerX + (Game.FRAME_WIDTH * (distX/largeChord)));
			int drawY = (int) (centerY + (Game.FRAME_WIDTH * (distY/largeChord)));

			drawPoint(g, drawX, drawY);
		}
		
	}
	
	public int[] modValuesYaw(double distDir, double distDepth) {
		double hyp = Math.sqrt(Math.pow(distDir, 2) + Math.pow(distDepth, 2));
		double theta1 = Math.toRadians(Game.Pyaw);
		double theta2 = Math.atan(distDir/distDepth);//find out if this is negitive or positive
		if(distDepth <= 0) {
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
		if(distDepth <= 0) {
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
	
}

/*
int distX = Math.abs(x - Game.playerX);
int distY = Math.abs(y - Game.playerY);
int distZ = Math.abs(z - Game.playerZ);//will need to be calculated differently based on the angles that you look at the object once tilt is taken account

int distXN = y - Game.playerX;
int distYN = y - Game.playerY;
int distZN = z - Game.playerZ;

int distX = Math.abs(x - Game.playerX);
int distY = Math.abs(y - Game.playerY);
int distZ = Math.abs(z - Game.playerZ);//will need to be calculated differently based on the angles that you look at the object once tilt is taken account


double hyp = Math.sqrt(Math.pow(distXN, 2) + Math.pow(distZN, 2));
		double theta1 = Math.toRadians(Game.Pyaw);
		double theta2 = Math.atan(distXN/distZN);
*/		
//double hypX = Math.pow((Math.pow(distX, 2) + Math.pow(distZ, 2)), 0.5);
		
//double thetaX  = Math.acos(distZ/hypX);

//double largeRadius = (1/Math.cos(dTr(Game.FOV/2)))*distZN;

//double largeChord = largeRadius * Math.sin(dTr(Game.FOV/4));//stores the length of half the chord at the 



