package com.mycompany.a3.Commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

//Command to move spaceship to random ASTRONAUT
public class OCommand extends Command{
	GameWorld gameWorld;
	
	public OCommand(GameWorld gw) {
		super("Move to Astronaut"); //call parent constructor
		gameWorld=gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		gameWorld.oButton();
		System.out.println("Spaceship was transported to a random ASTRONAUT.");
	}

}
