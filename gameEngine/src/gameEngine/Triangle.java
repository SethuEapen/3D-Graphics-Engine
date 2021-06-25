package gameEngine;

import java.awt.Color;
import java.awt.Graphics;

public class Triangle extends GameObject {

	int x2, y2, z2, x3, y3, z3; 
	int centerX = Game.FRAME_WIDTH/2;
	int centerY = Game.FRAME_HEIGHT/2;
	
	Color color;
	
	double distXN;
	double distYN;
	double distZN;
	
	int distX;
	int distY;
	int distZ;
	
	double distX2N;
	double distY2N;
	double distZ2N;
	
	int distX2;
	int distY2;
	int distZ2;
	
	double distX3N;
	double distY3N;
	double distZ3N;
	
	int distX3;
	int distY3;
	int distZ3;
	
	public Triangle(int x, int y, int z, int x2, int y2, int z2, int x3, int y3, int z3, Color color) {
		super(x, y, z);
		this.x2 = x2;
		this.y2 = y2;
		this.z2 = z2;
		this.x3 = x3;
		this.y3 = y3;
		this.z3 = z3;
		this.color = color;
	}

	@Override
	public void tick() {
		distXN = x - Game.playerX;
		distYN = Game.playerY - y;
		distZN = z - Game.playerZ;
		distX2N = x2 - Game.playerX;
		distY2N = Game.playerY - y2;
		distZ2N = z2 - Game.playerZ;
		distX3N = x3 - Game.playerX;
		distY3N = Game.playerY - y3;
		distZ3N = z3 - Game.playerZ;
		
		//point 1
		int pair[] =  modValuesYaw(distXN, distZN);
		distX = pair[0];
		distZ = pair[1];
		
		pair = modValuesPitch(distYN, distZ);
		distY = pair[0];
		distZ = pair[1];
		
		//point 2
		pair =  modValuesYaw(distX2N, distZ2N);
		distX2 = pair[0];
		distZ2 = pair[1];
		
		pair = modValuesPitch(distY2N, distZ2);
		distY2 = pair[0];
		distZ2 = pair[1];
		
		//point 3
		pair =  modValuesYaw(distX3N, distZ3N);
		distX3 = pair[0];
		distZ3 = pair[1];
		
		pair = modValuesPitch(distY3N, distZ3);
		distY3 = pair[0];
		distZ3 = pair[1];
	
		if(distZ >= 0 || distZ2 >= 0 || distZ3 >= 0) {
			normalDistance = (int) Math.sqrt(Math.pow(mean(new double[] {distX, distX2, distX3}), 2) + Math.pow(mean(new double[] {distY, distY2, distY3}), 2) + Math.pow(mean(new double[] {distZ, distZ2, distZ3}), 2));
		}else {
			normalDistance = -99999999;
		}
		
	
	}
	

	@Override
	public void render(Graphics g) {
		g.setColor(color);
		
				
			double largeChord;
			if(distZ >= 0) {
				largeChord = Math.tan(Math.toRadians(Game.FOV/2))*distZ;
			} else {
				largeChord = Math.tan(Math.toRadians(90) + Math.toRadians(Game.FOV/2))*+distZ;
			}
			
			double largeChord2;
			if(distZ2 >= 0) {
				largeChord2 = Math.tan(Math.toRadians(Game.FOV/2))*distZ2;
			} else {
				largeChord2 = Math.tan(Math.toRadians(90) + Math.toRadians(Game.FOV/2))*+distZ2;
			}
			
			double largeChord3;
			if(distZ3 >= 0) {
				largeChord3 = Math.tan(Math.toRadians(Game.FOV/2))*distZ3;
			} else {
				largeChord3 = Math.tan(Math.toRadians(90) + Math.toRadians(Game.FOV/2))*+distZ3;
			}
			
			
			int drawX = (int) (centerX + (Game.FRAME_WIDTH * (distX/largeChord)));
			int drawY = (int) (centerY + (Game.FRAME_WIDTH * (distY/largeChord)));
			int drawX2 = (int) (centerX + (Game.FRAME_WIDTH * (distX2/largeChord2)));
			int drawY2 = (int) (centerY + (Game.FRAME_WIDTH * (distY2/largeChord2)));
			int drawX3 = (int) (centerX + (Game.FRAME_WIDTH * (distX3/largeChord3)));
			int drawY3 = (int) (centerY + (Game.FRAME_WIDTH * (distY3/largeChord3)));
			
			g.fillPolygon(new int[] {drawX, drawX2, drawX3}, new int[] {drawY, drawY2, drawY3}, 3);			
		
		
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

	public static double mean(double[] m) {
	    double sum = 0;
	    for (int i = 0; i < m.length; i++) {
	        sum += m[i];
	    }
	    return sum / m.length;
	}
	
	@Override
	public int compareTo(GameObject o) {
		return o.normalDistance - this.normalDistance;
	}
	

}


/*double largeChord = Math.tan(Math.toRadians(Game.FOV/2))*distZ;
double largeChord2 = Math.tan(Math.toRadians(Game.FOV/2))*distZ2;
double largeChord3 = Math.tan(Math.toRadians(Game.FOV/2))*distZ3;

		x = x - velX;
		y = y - velY;	
		z = z - velZ;
		x2 = x2 - velX;
		y2 = y2 - velY;	
		z2 = z2 - velZ;
		x3 = x3 - velX;
		y3 = y3 - velY;	
		z3 = z3 - velZ;
*/

			
