package com.mycompany.a3.Commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

//Command to move spaceship RIGHT
public class RCommand extends Command{
	GameWorld gameWorld;
	
	public RCommand(GameWorld gw) {
		super("Right"); //call parent constructor
		gameWorld=gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		gameWorld.rButton();
		System.out.println("Spaceship was moved to the RIGHT.");
	}

}
