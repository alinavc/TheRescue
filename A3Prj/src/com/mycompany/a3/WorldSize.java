package com.mycompany.a3;

// Singleton to access world length and width
// allows all classes to access container x and y
public class WorldSize {
	private static WorldSize coords;
	private float width;
	private float height;
	
	private  WorldSize() {}
	
	public static WorldSize getCoords() {
		if(coords==null) {
			coords = new WorldSize();
		}
		return coords;
	}
	
	public void setCoords(float x,float y) { //set game container width and height
		width=x;
		height=y;
	}
	
	public float getGameX() { //get game width
		return width;
	}
	
	public float getGameY() { //get game height
		return height;
	}

}
