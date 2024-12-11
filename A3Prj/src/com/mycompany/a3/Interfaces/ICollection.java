package com.mycompany.a3.Interfaces;

import com.mycompany.a3.GameObject;

public interface ICollection {
	public void add(GameObject newObj);
	public IIterator getIterator();
}
