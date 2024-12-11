package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.mycompany.a3.Interfaces.IGuided;

public abstract class Rescuers extends GameObject implements IGuided{
	
	//abstracts
	public Boolean getChangeableSize() {
		return true; //size CAN be changed on spaceship (only rescuer at the moment)
	}
	
	
	protected void  guidedMove(Point objLoc,int direction, String xyFlag) { //gets direction from keyboard command 
		double deltaX= Math.cos(90-Math.toRadians(direction))*10;
		double deltaY= Math.sin(90-Math.toRadians(direction))*10;
		
		if(xyFlag=="x") {
			objLoc.setX((float) (objLoc.getX()+deltaX));
		}
		if(xyFlag=="y") {
			objLoc.setY((float) (objLoc.getY()+deltaY));
		}
	}
	//Declare IGuided functions
	@Override
	public void moveLeft(Point objLoc) {
		guidedMove(objLoc,270,"x");
	}

	@Override
	public void moveRight(Point objLoc) {
		guidedMove(objLoc,90,"x");
	}

	@Override
	public void moveUp(Point objLoc) {
		guidedMove(objLoc,0,"y");
	}

	@Override
	public void moveDown(Point objLoc) {
		guidedMove(objLoc,180,"y");
	}

	@Override
	public void jumpToLocation(Point objLoc,float newX, float newY) { //can be to random alien or astronaut
		objLoc.setX(newX);
		objLoc.setY(newY);
	}
}
