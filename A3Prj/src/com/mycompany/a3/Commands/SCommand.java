package com.mycompany.a3.Commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

//Command to OPEN door , UPDATE scores, EAT game objects in range
public class SCommand extends Command{
	GameWorld gameWorld;
	
	public SCommand(GameWorld gw) {
		super("Score"); //call parent constructor
		gameWorld=gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		gameWorld.sButton();
		System.out.println("Spaceship door has OPENED. Objects nearby have been let in and SCORES"
				+ " will be updated.");
	}

}