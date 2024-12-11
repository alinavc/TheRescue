package com.mycompany.a3.Commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

// Command to CONTRACT DOOR
public class CCommand extends Command{
	GameWorld gameWorld;
	
	public CCommand(GameWorld gw) {
		super("Contract");
		gameWorld=gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		gameWorld.cButton();
	}
}
