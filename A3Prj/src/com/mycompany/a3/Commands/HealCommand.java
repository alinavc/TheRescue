package com.mycompany.a3.Commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

public class HealCommand extends Command{
	GameWorld gw;
	public HealCommand(GameWorld gameWorld) {
		super("Heal");
		gw=gameWorld;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		gw.healButton();
		System.out.println("Selected astronaut has been HEALED.");
	}

}
