package com.mycompany.a3.Commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

//Command to  EXPAND DOOR
public class ECommand extends Command{
	GameWorld gameWorld;
	
	public ECommand(GameWorld gw) {
		super("Expand"); //call parent constructor
		gameWorld=gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		gameWorld.eButton();
	}

}
