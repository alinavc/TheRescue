package com.mycompany.a3;

import java.util.Vector;

import com.mycompany.a3.Interfaces.ICollection;
import com.mycompany.a3.Interfaces.IIterator;

// implements iterator pattern
public class GameObjectCollection implements ICollection{
	private int initialAstros=4;
	private int initialAliens=3;
	private Vector<GameObject> objVect;
	
	public GameObjectCollection(float gameX,float gameY) {
		objVect = new Vector<> ();
		Spaceship thisShip= Spaceship.getSpaceship();
		
		//Add 1 new spaceship to world
		objVect.add(thisShip);			
		//Fill Astronauts to object vector
		for(int i=0; i<initialAstros;i++)
		{
			objVect.add(new Astronauts(gameX,gameY));
		}
						
		//Fill Aliens to object vector
		for(int i=0; i<initialAliens;i++)
		{
			objVect.add(new Alien(gameX,gameY));
		}
	}

	@Override
	public void add(GameObject newObj) {
		objVect.addElement(newObj);
	}

	@Override
	public IIterator getIterator() {
		return new VectorIterator();
	}
	
//INNER PRIVATE CLASS
	private class VectorIterator implements IIterator{
		private int currIndex;
		
		public VectorIterator() {
			currIndex=-1;
		}

		@Override
		public Boolean hasNext() {
			if(objVect.size()<=0 || currIndex == objVect.size()-1) {
				return false;
			}
			return true;
		}

		@Override
		public GameObject getNext() {
			currIndex++;
			return objVect.elementAt(currIndex);
		}

		@Override
		public void remove() {
			objVect.removeElementAt(currIndex);
		}}
}