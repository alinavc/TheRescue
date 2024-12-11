package com.mycompany.a3;

import java.util.Random;
import java.util.Vector;

import com.codename1.charts.models.Point;
import com.mycompany.a3.Interfaces.ICollider;
import com.mycompany.a3.Interfaces.IMoving;

public abstract class Opponents extends GameObject implements IMoving, ICollider{
	private int objDir;
	private int objSpeed;
	
	//abstract
	public abstract void setSpecDir(int newDir);
	public abstract void updateCollVect();
	public abstract Vector<GameObject> getCollVect();
	
	//non-abstract
	public Boolean getChangeableSize() {
		return false;
	}
	
	protected int setDir() {
		Random r = new Random();
		objDir = r.nextInt(360);
		return objDir;
	}
	
	protected int setSpeed(int factor1) {
		int constant = 1;
		objSpeed=factor1*constant;
		return objSpeed;
	}
	
	@Override
	public int move(Point thisObj,int oppDir, int oppSpeed, int tickSpeed) {
		if(thisObj.getX()>=WorldSize.getCoords().getGameX() || thisObj.getX()<=0.0) { //checks if X OOB
			oppDir=(oppDir +180) % 360;
		}
		if(thisObj.getY()>=WorldSize.getCoords().getGameY() || thisObj.getY()<=0.0) //check if Y OOB
		{
			oppDir=(oppDir +180) % 360; //change direction if hitting the wall
		}
		double deltaX= (Math.cos(90-Math.toRadians(oppDir-5))) * (oppSpeed * ((double)tickSpeed/1000));
		double deltaY= (Math.sin(90-Math.toRadians(oppDir-5))) * (oppSpeed * ((double)tickSpeed/1000));
		
		thisObj.setX((float) (thisObj.getX()+deltaX));
		thisObj.setY((float) (thisObj.getY()+deltaY));
		
		return oppDir;
	}
}