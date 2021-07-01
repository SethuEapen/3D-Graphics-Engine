package gameEngine;

import java.awt.Graphics;

public class Light extends GameObject {

	int intensity;

	public Light(int x, int y, int z, int intensity) {
		super(x, y, z);
		this.intensity = intensity;
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compareTo(GameObject o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	public int getIntensity() {
		return intensity;
	}

	public void setIntensity(int intensity) {
		this.intensity = intensity;
	}

}
