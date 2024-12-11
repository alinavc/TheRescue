package com.mycompany.a3.Interfaces;

import com.mycompany.a3.GameObject;

public interface IIterator {
	public Boolean hasNext();
	public GameObject getNext();
	public void remove();
}
