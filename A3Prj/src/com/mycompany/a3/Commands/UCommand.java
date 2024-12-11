package com.mycompany.a3.Commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

//Command to move spaceship UP
public class UCommand extends Command{
	GameWorld gameWorld;
	
	public UCommand(GameWorld gw) {
		super("Up"); //call parent constructor
		gameWorld=gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		gameWorld.uButton();
		System.out.println("Spaceship was moved UP.");
	}

}
