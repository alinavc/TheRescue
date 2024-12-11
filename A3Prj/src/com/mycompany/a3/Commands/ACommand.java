package com.mycompany.a3.Commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

//Command to move spaceship to random ALIEN
public class ACommand extends Command{
	GameWorld gameWorld;
	
	public ACommand(GameWorld gw) {
		super("Move to Alien"); //call parent constructor
		gameWorld=gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		gameWorld.aButton();
		System.out.println("Spaceship was transported to a random ALIEN.");
	}

}
