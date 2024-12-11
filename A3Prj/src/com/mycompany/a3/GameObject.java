package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.mycompany.a3.Interfaces.IDrawable;

import java.util.Random;

public abstract class GameObject implements IDrawable{
	private int objCol;
	private ObjSize objSize = new ObjSize();
	private Point objLoc = new Point();
	
	public GameObject() {} //empty
	
	//abstract classes
	public abstract Point getLocObj();
	public abstract Boolean getChangeableCol();
	public abstract Boolean getChangeableSize();
	public abstract String printInfo();
	public abstract float getLocX();
	public abstract float getLocY();
	public abstract int getSize();

	//remains same among all child classes
	protected Point setLoc(float gameX,float gameY) {
		Random r = new Random();
		objLoc.setX(0+r.nextFloat()*(gameX-0));
		objLoc.setY(0+r.nextFloat()*(gameY-0));
		
		return objLoc;
	}
	
	public ObjSize setSize() {
		Random r = new Random();
		objSize.setLength(r.nextInt(120-50+1)+50);
		objSize.setWidth(objSize.getLength()); //since its a square
		
		return objSize;
	}
	
	public int setCol(int r, int g, int b, Boolean canChange) {
		if(canChange==true)
		{
			objCol=ColorUtil.rgb(r,g,b);
		}
		return objCol;
	}
}