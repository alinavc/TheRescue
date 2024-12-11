package com.mycompany.a3.Commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

//Command to move spaceship to the LEFT
public class LCommand extends Command{
	GameWorld gameWorld;
	
	public LCommand(GameWorld gw) {
		super("Left"); //call parent constructor
		gameWorld=gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		gameWorld.lButton();
		System.out.println("Spaceship was moved to the LEFT.");
	}

}
