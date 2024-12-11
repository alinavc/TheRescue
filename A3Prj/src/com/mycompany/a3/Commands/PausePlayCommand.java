package com.mycompany.a3.Commands;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.Game;

public class PausePlayCommand extends Command{
	private Game gameForm;
	private Boolean gameStatus=true; // true = game active
	
	public PausePlayCommand(Game g) {
		super("");
		gameForm=g;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		if(!gameStatus) { // if play button is active
			System.out.println("Game has RESUMED");
			gameForm.playTick();
			gameStatus=true;
		}
		else { // if pause button is active
			gameForm.pauseTick();
			System.out.println("Game has PAUSED.");
			gameStatus=false;
		}
	}
}
