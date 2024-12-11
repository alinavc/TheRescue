package com.mycompany.a3.Commands;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.TextArea;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;

//Command to REQUEST HELP
public class HelpCommand extends Command{
	
	public HelpCommand() {
		super("Help");
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		Dialog diBox = new Dialog("Clickable Keys");
		diBox.setLayout(new BorderLayout());
		
		TextArea helpTxt = new TextArea(
				"E - Expand door \n"
				+ "C - Contract door \n"
				+ "S - Open spaceship door & Update Score \n"
				+ "R - Move spaceship right \n"
				+ "L - Move spaceship left \n"
				+ "U - Move spaceship up \n"
				+ "D - Move spaceship down \n"
				+ "O - Teleport spaceship to random astronaut \n"
				+ "A - Teleport spaceship to random alien \n"
				+ "W - Alien collision: New alien spawned \n"
				+ "F - Alien vs Astronaut: Astronaut loses points \n"
				+ "T - Game clock ticks: Objects move");
		helpTxt.setEditable(false);
		diBox.add(BorderLayout.CENTER,helpTxt);
		
		Button okButton = new Button("Ok");
		okButton.addActionListener(e->diBox.dispose());
		diBox.add(BorderLayout.SOUTH,okButton);
		
		diBox.show();
	}
}
