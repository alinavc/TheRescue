package com.mycompany.a3;

import java.util.Vector;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.l10n.L10NManager;
import com.codename1.ui.Graphics;

public class Alien extends Opponents{
	private Point alienLoc = new Point();
	private ObjSize alienSize = new ObjSize();
	private int alienCol;
	private int alienSpeed;
	private int alienDir;
	private Boolean alienChangeableCol=false; //can't change color after initialization
	private Boolean hasCollided = false;
	private Vector<GameObject> collVect = new Vector<>();
	
	public Alien(float gameX,float gameY) {
		alienCol=setCol(108,196,23,true); //sends RGB
		alienLoc=setLoc(gameX,gameY); // in GameObject.java
		alienSize=setSize();
		alienSpeed=setSpeed(150); // Able to modify per A3Prj docs
		alienDir=setDir();
	}
// --------------------------------------------------
	@Override
	public int getSize() {
		return alienSize.getLength();
	}
// ---------------------------------------------------
	public String getCol() { //for printing
		String myDesc = "color= [" + ColorUtil.red(alienCol) + ","
				+ ColorUtil.green(alienCol) + ","
				+ ColorUtil.blue(alienCol) + "] ";
		return myDesc;
	}
	
	@Override
	public Boolean getChangeableCol() {
		return alienChangeableCol;
	}
// ----------------------------------------------------		
	public String getLoc() { //sets precision to 1 decimal point
		String myX= L10NManager.getInstance().format(alienLoc.getX(),1);
		String myY= L10NManager.getInstance().format(alienLoc.getY(),1);
		String myLoc = ("loc= " + myX + "," + myY + " ");
		return myLoc;
	}
	
	@Override
	public float getLocX() { //getter for location X var
		return alienLoc.getX();
	}
	@Override
	public float getLocY() { //getter for location Y var
		return alienLoc.getY();
	}
	
	public void setSpawnedLoc(float newX, float newY) {
		alienLoc.setX(newX-5);
		alienLoc.setY(newY-5);
	}
// ------------------------------------------------
	protected int getSpeed() { //get alien speed
		return alienSpeed;
	}
// -----------------------------------------------
	@Override
	public void setSpecDir(int newDir) {
		alienDir=newDir;
	}
	
	protected int getDir() { //get alien direction in DEGREES
		return alienDir; 
	}
// ---------------------------------------------
	@Override
	public String printInfo() {
		return (getLoc()+getCol()+"size="+getSize()+" speed="+getSpeed()+" dir="+getDir());
	}
// -----------------------------------------------
	@Override
	public Point getLocObj() {
		return alienLoc;
	}
// ---------------------------------------------
	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) { //implement IDrawable
		int xLoc = (int)(pCmpRelPrnt.getX() + getLocX());
		int yLoc = (int)(pCmpRelPrnt.getY()+getLocY());
		
		int halfSize = getSize()/2;
		
		g.setColor(alienCol);
		g.drawRect((int)(xLoc -halfSize), (int)((yLoc-halfSize)),getSize(), getSize());
		g.fillRect((int)(xLoc -halfSize), (int)((yLoc-halfSize)),getSize(),getSize()); 
	}
// ---------------------------------------------------
	@Override
	public Boolean collidesWith(GameObject otherObj) {
		float thisX= this.getLocX();
		float thisY= this.getLocY();
		float otherX=otherObj.getLocX();
		float otherY=otherObj.getLocY();
		
		float thisSize = this.getSize()/2;
		float otherSize = otherObj.getSize()/2;
		
		if(thisX+thisSize > otherX-otherSize &&
			thisX-thisSize < otherX+otherSize &&
			otherY+otherSize > thisY-thisSize &&
			otherY-otherSize < thisY+thisSize) {
				return true;
			}
		return false;
	}
	@Override
	public void handleCollision(GameObject otherObj) {
		// convert otherObj to alien since the only way to reach this function is 2 aliens
		if(hasCollided) {
			return;
		}
		
		Alien otherAlien = (Alien) otherObj;
		
		this.hasCollided=true;
		otherAlien.hasCollided=true;
		
		this.setSpawnedLoc(otherObj.getLocX(), otherObj.getLocY());
		this.hasCollided=true;
	}
// -------------------------------------------
	@Override
	public void updateCollVect() {
		for(int i=0; i<collVect.size();i++) {
			if(!this.collidesWith(collVect.get(i))) {
				collVect.remove(i);
			}
		}
	}
	@Override
	public Vector<GameObject> getCollVect() {
		return collVect;
	}
	
	public Boolean getCollStat() {
		return hasCollided;
	}
	public void setFalseCollStat() {
		hasCollided= false;
	}
}