package gameEngine;

import java.awt.Color;
import java.util.ArrayList;


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
		
		
		//clockwise is showing face
		
		//back face counter clockwise

		ArrayList<GameObject> tempList = new ArrayList<GameObject>();
		
		tempList.add(new Triangle(x, y, (z + width), (x + length), y, (z + width), x, (y + height), (z + width), color, false));
		tempList.add(new Triangle((x + length), (y + height), (z + width), x, (y + height), (z + width), (x + length), y, (z + width), color, false));
		
		//left side counter clockwise
		tempList.add(new Triangle(x, y, z, x, y, (z + width), x, (y + height), z, Color.green, false));
		tempList.add(new Triangle(x, (y + height), (z + width), x, (y + height), z, x, y, (z + width), Color.green, false));

		//right side counter clockwise
		tempList.add(new Triangle((x + length), y, z, (x + length), (y + height), z, (x + length), y, (z + width), Color.green, false));
		tempList.add(new Triangle((x + length), (y + height), (z + width), (x + length), y, (z + width), (x + length), (y + height), z, Color.green, false));
		
		//bottom face //counter clockwise
		tempList.add(new Triangle(x, y, z, (x + length), y, z, x, y, (z + width), Color.red, false));
		tempList.add(new Triangle((x + length), y, (z + width), x, y, (z + width), (x + length), y, z, Color.red, false));

		//top face clockwise
		tempList.add(new Triangle(x, (y + height), z, x, (y + height), (z + width), (x + length), (y + height), z, Color.red, false));
		tempList.add(new Triangle((x + length), (y + height), (z + width), (x + length), (y + height), z, x, (y + height), (z + width), Color.red, false));

		//front face clockwise
		tempList.add(new Triangle(x, y, z, x, (y + height), z, (x + length), y, z, color, false));
		tempList.add(new Triangle((x + length), (y + height), z, (x + length), y, z, x, (y + height), z, color, true));

		//ItemPair tempPair = new ItemPair(tempList, false);

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
