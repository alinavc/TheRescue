package com.mycompany.a3;

import java.util.Vector;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.l10n.L10NManager;
import com.codename1.ui.Graphics;
import com.mycompany.a3.Interfaces.ISelectable;

public class Astronauts extends Opponents implements ISelectable{
	private Point astroLoc = new Point();
	private ObjSize astroSize = new ObjSize();
	private int astroCol;
	private int astroSpeed;
	private int astroDir;
	private int astroHealth=5; //0-5 ..starts at 5
	private int astroWorth=10; // reduce 1 each fight
	private Boolean astroChangeableCol=true;
	private Vector<GameObject> collVect = new Vector<>();
	private Boolean isSelected=false;
	
	public Astronauts(float gameX,float gameY) { //constructor
		astroLoc=setLoc(gameX,gameY);
		astroCol=setCol(195,39,0, getChangeableCol());
		astroSize=setSize();
		astroSpeed=setSpeed(150);
		astroDir=setDir();
	}
	
// ------------------------------	
	public void updateWorth() {
		astroWorth--;
	}
	
	public int getWorth() {
		return astroWorth;
	}
	
// -----------------------------------
	protected int getSpeed() {
		return astroSpeed;
	}
// --------------------------------------
	public void fightOccurs() {
		updateHealth();
		if(astroSpeed>0) {
			astroSpeed=setSpeed(getSpeed()-30);
		}
	}
// ----------------------------------	
	public void updateHealth() { //updates health and changes astro health color
		if(astroHealth!=0) //decrement health if not already at 0
		{
			astroHealth--;
			updateWorth(); //worth depends on health
			//change color of astronaut 
			if(astroHealth==4) {
				astroCol=setCol(255,85,0,getChangeableCol());
			}
			else if(astroHealth==3) {
				astroCol=setCol(240,117,15,getChangeableCol());
			}
			else if(astroHealth==2) {
				astroCol=setCol(244,128,32,getChangeableCol());
			}
			else if(astroHealth==1) {
				astroCol=setCol(240,149,55,getChangeableCol());
			}
			else if(astroHealth==0) {
				astroCol=setCol(240,161,80,getChangeableCol());
			}
		}
	}
	public int getHealth() {
		return astroHealth;
	}
	
	public void healSelected() { //reset stats back to full
		if(this.isSelected) {
			astroHealth=5;
			astroWorth=10;
			astroSpeed=150;
			astroCol=setCol(195,39,0, getChangeableCol());
		}
	}
// ------------------------------------
	@Override
	public int getSize() {
		return astroSize.getLength();
	}
// -----------------------------------
	public String getCol() {
		String myCol= "color= [" + ColorUtil.red(astroCol) + ","
				+ ColorUtil.green(astroCol) + ","
				+ ColorUtil.blue(astroCol) + "] ";
		return myCol;
	}
	
	@Override
	public Boolean getChangeableCol() {
		return astroChangeableCol;
	}
// ------------------------------------
	public String getLoc() { //for printing
		String myX= L10NManager.getInstance().format(astroLoc.getX(),1);
		String myY= L10NManager.getInstance().format(astroLoc.getY(),1);
		String myLoc = ("loc= " + myX + "," + myY + " ");
		return myLoc;
	}
	
	@Override
	public float getLocX() {
		return astroLoc.getX();
	}
	@Override
	public float getLocY() {
		return astroLoc.getY();
	}
// -------------------------------------	
	@Override
	public void setSpecDir(int newDir) {
		astroDir=newDir;
	}
	
	public int getDir() {
		return astroDir;
	}
// ---------------------------------------
	@Override
	public String printInfo() {
		return (getLoc()+getCol()+"size="+getSize()+" speed="+getSpeed()+" dir="+getDir()+" health="+getHealth());
	}
// -----------------------------------------------
	@Override
	public Point getLocObj() {
		return astroLoc;
	}
// -----------------------------------------
	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) { //implement IDrawable
		int xLoc = (int)(pCmpRelPrnt.getX() + getLocX());
		int yLoc = (int)(pCmpRelPrnt.getY()+getLocY());
		
		int halfSize = getSize()/2;
		
		int xPoints[] = {xLoc,xLoc-halfSize,xLoc+halfSize};
		int yPoints[] = {yLoc + halfSize, yLoc-halfSize, yLoc-halfSize};
		int nPoints = 3; // # of polygon points to make triangle
		
		g.setColor(astroCol);		
		// draw triangle to bottom left line
		if(isSelected()) {
			g.drawPolygon(xPoints, yPoints, nPoints);
		}
		else {
			g.fillPolygon(xPoints, yPoints, nPoints);
		}
	}
// -----------------------------------------------
	@Override
	public Boolean collidesWith(GameObject otherObj) {
		float thisX= this.getLocX();
		float thisY= this.getLocY();
		float otherX=otherObj.getLocX();
		float otherY=otherObj.getLocY();
		
		float thisSize = this.getSize()/2;
		float otherSize = otherObj.getSize()/2;
		
		if(thisX+thisSize>otherX-otherSize &&
			thisX-thisSize< otherX+otherSize &&
			otherY+otherSize > thisY-thisSize &&
			otherY-otherSize< thisY+thisSize) {
				return true;
			}
		return false;
	}

	@Override
	public void handleCollision(GameObject otherObj) {
		//convert GameObject to Alien since Astros can only collide with aliens and do something
		Alien thisAlien = (Alien) otherObj;
		
		this.collVect.add(thisAlien);
		thisAlien.getCollVect().add(this);
		
		this.fightOccurs();
	}
// ----------------------------------------------------
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
// -----------------------------------------------------
	@Override
	public void setSelected(Boolean b) {
		isSelected=b;
	}

	@Override
	public Boolean isSelected() {
		return isSelected;
	}

	@Override
	public Boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt) {
		int padding=20;
		float px= pPtrRelPrnt.getX()+padding;
		float py=pPtrRelPrnt.getY()+padding;
		float xLoc=pCmpRelPrnt.getX()+this.getLocX()-padding;
		float yLoc=pCmpRelPrnt.getY()+this.getLocY()-padding;
		
		if((px>=xLoc) && (px<= xLoc + (this.getSize()))&&(py>= yLoc) && (py<=yLoc+ (this.getSize()))) {
			return true;
		}
		return false;
	}
}