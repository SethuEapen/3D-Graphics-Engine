package gameEngine;

import java.awt.Color;
import java.awt.Graphics;

public class Box {

	int centerx = Game.FRAME_WIDTH/2;
	int centery = Game.FRAME_HEIGHT/2;
	int length, width, height;
	Color color;
	Handler handler;
	
	public Box(int x, int y, int z, int length, int width, int height, Color color, Handler handler){
		this.length = length;
		this.width = width;
		this.height = height;
		this.color = color;
		this.handler = handler;
		
		
		
		
		//back face
		handler.addObject(new Triangle(x, y, (z + width), (x + length), y, (z + width), x, (y + height), (z + width), color));
		handler.addObject(new Triangle((x + length), (y + height), (z + width), (x + length), y, (z + width), x, (y + height), (z + width), color));
		
		//left side
		handler.addObject(new Triangle(x, y, z, x, y, (z + width), x, (y + height), z, Color.green));
		handler.addObject(new Triangle(x, (y + height), (z + width), x, y, (z + width), x, (y + height), z, Color.green));

		//right side
		handler.addObject(new Triangle((x + length), y, z, (x + length), y, (z + width), (x + length), (y + height), z, Color.green));
		handler.addObject(new Triangle((x + length), (y + height), (z + width), (x + length), y, (z + width), (x + length), (y + height), z, Color.green));
		
		//top face
		handler.addObject(new Triangle(x, y, z, (x + length), y, z, x, y, (z + width), Color.red));
		handler.addObject(new Triangle((x + length), y, (z + width), (x + length), y, z, x, y, (z + width), Color.red));

		//bottom face
		handler.addObject(new Triangle(x, (y + height), z, (x + length), (y + height), z, x, (y + height), (z + width), Color.red));
		handler.addObject(new Triangle((x + length), (y + height), (z + width), (x + length), (y + height), z, x, (y + height), (z + width), Color.red));

		//front face
		handler.addObject(new Triangle(x, y, z, (x + length), y, z, x, (y + height), z, color));
		handler.addObject(new Triangle((x + length), (y + height), z, (x + length), y, z, x, (y + height), z, color));
	}


}

/*
g.drawRect(x, y, size, size);
int distx = centerx - x;
int disty = centery - y;

int width = size;

double temp = (int) (Math.pow(distx, 2) + Math.pow(disty, 2));

double distT = (int) Math.pow(temp, 0.5);

double theta  = Math.acos(width/distT);

int something = (int)Math.tan(theta)*width;

int backSize = (int) (size*((distT - (Math.abs(distT)/5))/distT));
//System.out.println((distT - (distx/5)) + " " + distT);

//g.drawRect(x + (distx/5), y+(disty/5), backSize, backSize);
g.drawRect(x + (distx/5), y+(disty/5), backSize, backSize);
g.drawLine(x, y, x + (distx/5), y+(disty/5));//left corner
g.drawLine(x+size, y, x + (distx/5) + backSize, y+(disty/5));//right corner
g.drawLine(x, y+size, x + (distx/5), y+(disty/5) + backSize);//bottom left corner
g.drawLine(x+size, y+size, x + (distx/5) + backSize, y+(disty/5) + backSize);//bottom right corner

*/