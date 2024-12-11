package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.l10n.L10NManager;
import com.codename1.ui.Graphics;

public class Spaceship extends Rescuers{
	private ObjSize doorSize = new ObjSize();
	private int minDoorSize=50;
	private int maxDoorSize=1000;
	private Boolean doorOpen=false;
	private int shipCol;
	private Point shipLoc = new Point();
	private Boolean shipChangeableCol=false; //cannot change color after initialization

	private static Spaceship shipObj; //singleton start
	
	private Spaceship(float gameX,float gameY) {
		doorSize.setLength(100); //door size
		doorSize.setWidth(doorSize.getLength());
		shipCol=setCol(105,105,105, true); //initially needs to be able to set color
		shipLoc=setLoc(gameX,gameY);
	}
	
	public static Spaceship getSpaceship() {
		if(shipObj==null) {
			shipObj = new Spaceship(WorldSize.getCoords().getGameX(),WorldSize.getCoords().getGameY());
		}
		return shipObj;
	}
// --------------------------------------------------------
	@Override
	public int getSize() {
		return doorSize.getLength();
	}

	public void expandDoor() {
		if(doorSize.getLength()<maxDoorSize) { //check that door is under max door size
			System.out.println("Spaceship door has EXPANDED.");
			doorSize.setLength(doorSize.getLength()+50);
			doorSize.setWidth(doorSize.getLength()); //set width to length since square
		}
		else {
			System.out.println("Door cannot expand anymore.");
		}
	}
	
	public void contractDoor() {
		if(doorSize.getLength()>minDoorSize) { //checks if door is greater than minimum door size
			System.out.println("Spaceship door has CONTRACTED.");
			doorSize.setLength(doorSize.getLength()-50);
			doorSize.setWidth(doorSize.getLength());
		}
		else {
			System.out.println("Door cannot contract anymore.");
		}
	}
	
	public void openDoor() { //set door flag to open (true)
		doorOpen=true;
	}
	
	public void closeDoor() { //set door flag to closed (false)
		doorOpen=false;
	}
	
	public Boolean doorStatus() {
		return doorOpen;
	}
// ------------------------------------------------------------------	
	public String getLoc() { //for printing
		String myX= L10NManager.getInstance().format(shipLoc.getX(),1);
		String myY= L10NManager.getInstance().format(shipLoc.getY(),1);
		String myLoc = ("loc= " + myX + "," + myY + " ");
		return myLoc;
	}
	
	@Override
	public float getLocX() {
		return shipLoc.getX();
	}
	@Override
	public float getLocY() {
		return shipLoc.getY();
	}
// ----------------------------------------------------------
	public String getCol() {
		String myDesc = "color= [" + ColorUtil.red(shipCol) + ","
				+ ColorUtil.green(shipCol) + ","
				+ ColorUtil.blue(shipCol) + "] ";
		return myDesc;
	}
	
	@Override
	public Boolean getChangeableCol() {
		return shipChangeableCol;
	}
// -----------------------------------------------	
	@Override
	public String printInfo() {
		return (getLoc()+getCol()+"size="+getSize());
	}	
// ----------------------------------------------------------
	@Override
	public Point getLocObj() {
		return shipLoc;
	}
// -----------------------------------------------
	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) { //implement IDrawable
		int xLoc = (int)(pCmpRelPrnt.getX() + getLocX());
		int yLoc = (int)(pCmpRelPrnt.getY()+getLocY());
		int halfSize = getSize()/2;
		
		g.setColor(shipCol);
		g.drawArc(xLoc-halfSize, yLoc-halfSize, getSize(), getSize(), 0, 360);
		g.fillArc(xLoc-halfSize, yLoc-halfSize, getSize(), getSize(), 0, 360);
	}
}