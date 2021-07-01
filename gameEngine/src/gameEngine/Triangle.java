package gameEngine;

import java.awt.Color;
import java.awt.Graphics;

public class Triangle extends GameObject implements Runnable {

	int x2, y2, z2, x3, y3, z3; 
	int centerX = Game.FRAME_WIDTH/2;
	int centerY = Game.FRAME_HEIGHT/2;
	
	Color color;
	
	double distXN;
	double distYN;
	double distZN;
	
	double distX;
	double distY;
	double distZ;
	
	double distX2N;
	double distY2N;
	double distZ2N;
	
	double distX2;
	double distY2;
	double distZ2;
	
	double distX3N;
	double distY3N;
	double distZ3N;
	
	double distX3;
	double distY3;
	double distZ3;
	
	double[] normal;
		
	boolean monitor;
	
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
	
	
	/*
	
	private double[] addVectors(int vect_A[], int vect_B[]) {
		double[] temp = new double[3];
		
		temp[0] = vect_A[0] + vect_B[0];
		temp[1] = vect_A[1] + vect_B[1];
		temp[2] = vect_A[2] + vect_B[2];

		return temp;		
	}*/
	
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
	
	@Override
	public void render(Graphics g) {
		double playerInfluence = calculateNormals(distXN, distYN, distZN);
		
		double lightInfluence = (calculateNormals((x - Game.lights[0].x), (Game.lights[0].y - y), (z - Game.lights[0].z)));
				
		double lightIntencity =  Math.sqrt(Math.pow(mean(new double[] {x, x2, x3}) - Game.lights[0].x, 2) + 
				Math.pow(Game.lights[0].y - mean(new double[] {y, y2, y3}), 2) + 
				Math.pow(mean(new double[] {z, z2, z3}) - Game.lights[0].z, 2)) / (Game.lights[0].intensity/10);
		
		System.out.println(lightIntencity);
		
		lightInfluence = Math.max(lightInfluence, 10000000);
		
		int value = (int) ((lightInfluence/lightIntencity)/1000); //(normalDistance*1000));
		
		value = Math.min(255, value);
		
		value = Math.max(0, value);
		
		Color shade = new Color(value,value,value);
		g.setColor(shade);
		
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
		
		
		//find midpoint of first 2 points
		double midPointX1 = (distX2 + distX)/2;
		double midPointY1 = (distY2 + distY)/2;
		double midPointZ1 = (distZ2 + distZ)/2;
		
		double midDistX = midPointX1 - distX3;
		double midDistY = midPointY1 - distY3;
		double midDistZ = midPointZ1 - distZ3;
		
		double midPitch1 = Math.atan(midDistX/midDistY);//adjust for +180
		
		if(midDistY < 0) {
			midPitch1 = midPitch1 + Math.toRadians(180);
		}
		
		double midYaw1 = Math.atan(midDistX/midDistZ);//adjust for +180
		
		if(midDistZ < 0) {
			midYaw1 = midYaw1 + Math.toRadians(180);
		}
		
		double midPointX2 = (distX3 + distX)/2;
		double midPointY2 = (distY3 + distY)/2;
		double midPointZ2 = (distZ3 + distZ)/2;
		
		double midDistX2 = midPointX2 - distX2;
		double midDistY2 = midPointY2 - distY2;
		double midDistZ2 = midPointZ2 - distZ2;
		
		double midPitch2 = Math.atan(midDistX2/midDistY2);//adjust for +180
		
		if(midDistY2 < 0) {
			midPitch2 = midPitch2 + Math.toRadians(180);
		}
		
		double midYaw2 = Math.atan(midDistX2/midDistZ2);//adjust for +180
		
		if(midDistZ2 < 0) {
			midYaw2 = midYaw2 + Math.toRadians(180);
		}
		
		double midPointX3 = (distX3 + distX2)/2;
		double midPointY3 = (distY3 + distY2)/2;
		double midPointZ3 = (distZ3 + distZ2)/2;
		
		double midDistX3 = midPointX3 - distX;
		double midDistY3 = midPointY3 - distY;
		double midDistZ3 = midPointZ3 - distZ;
		
		double midPitch3 = Math.atan(midDistX3/midDistY3);//adjust for +180
		
		if(midDistY3 < 0) {
			midPitch3 = midPitch3 + Math.toRadians(180);
		}
		
		double midYaw3 = Math.atan(midDistX3/midDistZ3);//adjust for +180
		
		if(midDistZ3 < 0) {
			midYaw3 = midYaw3 + Math.toRadians(180);
		}
		
		private boolean calculateNormalsLong() {
		int[] p1 = {x-x2, -(y-y2), z-z2};
		int[] p2 = {x2-x3, -(y2-y3), z2-z3};
		int[] p3 = {x3-x, -(y3-y), z3-z};

		int[] U = subVectors(p2, p1);
		int[] V = subVectors(p3, p1);

		int[] normal = crossProduct(U, V);
		
		double normalYaw;
		if(normal[0] != 0) {
			normalYaw = Math.toDegrees(Math.atan(normal[1]/normal[2]));
		}else {
			if(normal[1] != 0) {
				normalYaw = 90 * (normal[1]/Math.abs(normal[1]));
			} else {
			}
		}
		
		double normalPitch = Math.toDegrees(Math.atan2(normal[2], normal[1]));
		
		if(normal[1] < 0) {
			//normalPitch = normalPitch + 180;
		}
		
		double fds = Math.toDegrees(Math.atan2(normal[0], normal[1]));
		
		if(normal[1] < 0) {
			//normalYaw = normalYaw + 180;
		}
		
		if(normal[1] < 0) {
			normalYaw = normalYaw + 180;
		}
		
		double afds;
		if(normal[0] != 0) {
			normalPitch = Math.toDegrees(Math.atan(normal[1]/normal[0]));
		}else {
			normalPitch = 90 * (normal[1]/Math.abs(normal[1]));
		}
		
		if(normal[1] < 0) {
			normalPitch = normalPitch + 180;
		}
		
		System.out.println(Arrays.toString(normal));
		System.out.println("Yaw: " + normalYaw + "       Pitch: " + normalPitch);
		
		return false;
	}
		
		
		
*/

/*private double[] normalComponents(double dX, double dX2, double dX3, double dY, double dY2, double dY3, double dZ, double dZ2, double dZ3) {
double midPointX = (dX2 + dX)/2;
double midPointY = (dY2 + dY)/2;
double midPointZ = (dZ2 + dZ)/2;

double midDistX = midPointX - dX3;
double midDistY = midPointY - dY3;
double midDistZ = midPointZ - dZ3;

double midPitch = Math.atan(midDistX/midDistY);//adjust for +180

if(midDistY < 0) {
	//midPitch = midPitch + Math.toRadians(180);
}

double midYaw = Math.atan(midDistX/midDistZ);//adjust for +180

if(midDistZ < 0) {
	//midYaw = midYaw + Math.toRadians(180);
}

double[] pair = new double[] {midPitch, midYaw};

return pair;
}*/

/*private double[] normalComponents(double dX, double dX2, double dX3, double dY, double dY2, double dY3, double dZ, double dZ2, double dZ3) {
double midPointX = (dX2 + dX)/2;
double midPointY = (dY2 + dY)/2;
double midPointZ = (dZ2 + dZ)/2;

double midDistX = midPointX - dX3;
double midDistY = midPointY - dY3;
double midDistZ = midPointZ - dZ3;


double midPitch;

if(midDistY != 0) {
	midPitch = -Math.atan(midDistX/midDistY);//adjust for +180
} else {
	midPitch = 0;
}


double midYaw;

if(midDistZ != 0) {
	midYaw = -Math.atan(midDistX/midDistZ);//adjust for +180
}else {
	midYaw = 0.0;
}


if(midDistZ < 0) {
	//midYaw = midYaw + Math.toRadians(180);
}

if(midDistY < 0) {
	//midPitch = midPitch + Math.toRadians(180);
}

double[] pair = new double[] {midPitch, midYaw};


return pair;
}*/


/*private boolean calculateNormals() {
//normal 1
double[] pair1 = normalComponents(distXN, distX2N, distX3N, distYN, distY2N, distY3N, distZN, distZ2N, distZ3N);

//normal 2
double[] pair2 = normalComponents(distX2N, distX3N, distXN, distY2N, distY3N, distYN, distZ2N, distZ3N, distZN);

//normal 3
double[] pair3 = normalComponents(distX3N, distXN, distX2N, distY3N, distYN, distY2N, distZ3N, distZN, distZ2N);


double normalPitch = mean(new double[] {pair1[0], pair2[0], pair3[0]});
		
double normalYaw = mean(new double[] {pair1[1], pair2[1], pair3[1]});

System.out.println("Normal Yaw: " + Math.toDegrees(normalYaw) + "      Normal Pitch: " + Math.toDegrees(normalPitch));


//if(normalPitch > Math.toRadians(-90) && Math.toRadians(90) > normalPitch) {

	if(normalYaw > Math.toRadians(90) && Math.toRadians(180) > normalYaw) {
		return true;
	}
//}


return true;
}*/

//double[] p1 = {(distX-distX2), -(distY-distY2), (distZ-distZ2)};
		//double[] p2 = {(distX2-distX3), -(distY2-distY3), (distZ2-distZ3)};
		//double[] p3 = {(distX3-distX), -(distY3-distY), (distZ3-distZ)};
			
