package com.mycompany.a3.Interfaces;

import com.mycompany.a3.GameObject;

public interface ICollider {
	public Boolean collidesWith(GameObject otherObj);
	public void handleCollision(GameObject otherObj);
}
