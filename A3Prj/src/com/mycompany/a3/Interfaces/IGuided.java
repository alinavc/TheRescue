package com.mycompany.a3.Interfaces;

import com.codename1.charts.models.Point;

public interface IGuided {
	public void moveLeft(Point objLoc);
	public void moveRight(Point objLoc);
	public void moveUp(Point objLoc);
	public void moveDown(Point objLoc);
	public void jumpToLocation(Point objLoc,float newX,float newY);
}
