package gameEngine;

import java.awt.Color;
import java.awt.Graphics;

public class Triangle extends GameObject implements Runnable {

	int centerX = Game.FRAME_WIDTH/2;
	int centerY = Game.FRAME_HEIGHT/2;
	
	int x2, y2, z2, x3, y3, z3; 
	
	Color color;
	
	boolean monitor;
	
	double distXN, distYN, distZN;
	
	double distX, distY, distZ;
	
	double distX2N, distY2N, distZ2N;
	
	double distX2, distY2, distZ2;
	
	double distX3N, distY3N, distZ3N;
	
	double distX3, distY3, distZ3;
	
	double[] normal;
	
	double colorValue;
			
	public Triangle(int x, int y, int z, int x2, int y2, int z2, int x3, int y3, int z3, Color color, boolean monitor) {
		super(x, y, z);
		this.x2 = x2;
		this.y2 = y2;
		this.z2 = z2;
		this.x3 = x3;
		this.y3 = y3;
		this.z3 = z3;
		this.color = color;
		this.monitor = monitor;
		
		double[] p1 = {x-x2, -(y-y2), z-z2};
		double[] p2 = {x2-x3, -(y2-y3), z2-z3};
		double[] p3 = {x3-x, -(y3-y), z3-z};

		double[] U = subVectors(p2, p1);
		double[] V = subVectors(p3, p1);

		normal = crossProduct(U, V);	
		
		shadingFromLights();
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
			
			/*
			int nD1 = (int) Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2) + Math.pow(distZ, 2));
			int nD2 = (int) Math.sqrt(Math.pow(distX2, 2) + Math.pow(distY2, 2) + Math.pow(distZ2, 2));
			int nD3 = (int) Math.sqrt(Math.pow(distX3, 2) + Math.pow(distY3, 2) + Math.pow(distZ3, 2));
			
			normalDistance = Math.min(nD1, nD2);
			normalDistance = Math.min(normalDistance, nD3);*/
			
		}else {
			normalDistance = -99999999;
		}
	
	}
	
	
	
	private double calculateNormals(double dX, double dY, double dZ) {
		double[] playerVector = {dX, dY, dZ};
		
		double solution = dotProduct(normal, playerVector);
		
		//System.out.println(Arrays.toString(normal));
		
		return solution;
	}
	
	private double[] subVectors(double vect_A[], double vect_B[]) {
		double[] temp = new double[3];
		
		temp[0] = vect_A[0] - vect_B[0];
		temp[1] = vect_A[1] - vect_B[1];
		temp[2] = vect_A[2] - vect_B[2];

		return temp;		
	}

	private double dotProduct(double vect_A[], double vect_B[])
    {
 
		double product = 0;
 
        // Loop for calculate cot product
        for (int i = 0; i < 3; i++)
            product = product + vect_A[i] * vect_B[i];
        return product;
    }
 
    private double[] crossProduct(double vect_A[], double vect_B[]){
    	double[] temp = new double[3];
        
		temp[0] = vect_A[1] * vect_B[2]
                     - vect_A[2] * vect_B[1];
		temp[1] = vect_A[2] * vect_B[0]
                     - vect_A[0] * vect_B[2];
		temp[2] = vect_A[0] * vect_B[1]
                     - vect_A[1] * vect_B[0];
		
		return temp;
    }
	
    private void shadingFromLights() {
		double lightInfluence = (calculateNormals((x - Game.lights[0].x), (Game.lights[0].y - y), (z - Game.lights[0].z))) * (Game.lights[0].intensity);
		
		double lightDistance =  Math.sqrt(Math.pow(mean(new double[] {x, x2, x3}) - Game.lights[0].x, 2) + 
				Math.pow(Game.lights[0].y - mean(new double[] {y, y2, y3}), 2) + 
				Math.pow(mean(new double[] {z, z2, z3}) - Game.lights[0].z, 2));
		
		
		//lightInfluence = Math.max(lightInfluence, 10000000);
		
		colorValue = (int) (255 * (lightInfluence/1700000000));
		
		
		colorValue = Math.min(255, colorValue);
		colorValue = Math.max(0, colorValue);
		
		colorValue = colorValue - (lightDistance/60);
				
	}
    
    @Override
	public void render(Graphics g) {
		double playerInfluence = calculateNormals(distXN, distYN, distZN);
		
		//calculate light interation with player distance
		double distVal = (double)(normalDistance) / 30;
		

		//int adjColorValue = (int) colorValue;
		int adjColorValue = (int) (colorValue - distVal);
		

		
		adjColorValue = Math.min(255, adjColorValue);
		adjColorValue = Math.max(0, adjColorValue);
		
		color = new Color(adjColorValue, adjColorValue, adjColorValue);
		g.setColor(color);
		
		if(playerInfluence > 0) {
		
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


