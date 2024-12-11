package com.mycompany.a3.Commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

//Command to move spaceship DOWN
public class DCommand extends Command{
	GameWorld gameWorld;
	
	public DCommand(GameWorld gw) {
		super("Down"); //call parent constructor
		gameWorld=gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		gameWorld.dButton();
		System.out.println("Spaceship was moved DOWN.");
	}

}
