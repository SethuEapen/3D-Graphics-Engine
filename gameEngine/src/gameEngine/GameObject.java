package gameEngine;
import java.awt.Graphics;



public abstract class GameObject implements Comparable<GameObject> {
	protected int x;
	protected int y;
	protected int z;
	protected int velX;
	protected int velY;
	protected int velZ;
	protected int normalDistance = -99999999;
		
	public abstract void tick();
	public abstract void render(Graphics g);

	public GameObject (int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public int getVelX() {
		return velX;
	}

	public void setVelX(int velX) {
		this.velX = velX;
	}

	public int getVelY() {
		return velY;
	}

	public void setVelY(int velY) {
		this.velY = velY;
	}
	public int getZ() {
		return z;
	}
	public void setZ(int z) {
		this.z = z;
	}
	public int getVelZ() {
		return velZ;
	}
	public void setVelZ(int velZ) {
		this.velZ = velZ;
	}
	public int getNormalDistance() {
		return normalDistance;
	}
	public void setNormalDistance(int normalDistance) {
		this.normalDistance = normalDistance;
	}

}
