package com.mycompany.a3.Commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

//Command to switch SOUND ON/OFF
public class SoundCommand extends Command{
	GameWorld gameWorld;
	
	public SoundCommand(GameWorld gw) {
		super("Sound"); //call parent constructor
		gameWorld=gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		gameWorld.soundButton();
	}

}
