package com.mycompany.a3.Commands;

import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.*;


//Command to query user about exiting the game
public class ExitCommand extends Command{
	public ExitCommand() {
		super("Exit Game");
	}
	@Override
	public void actionPerformed(ActionEvent ev) {
		Dialog diBox = new Dialog("Exit Game");
		diBox.setLayout(new BorderLayout());
		
		diBox.add(BorderLayout.CENTER, new Label(
				"Would you like to quit?"));
		
		Button yesButton = new Button("Yes"); //exits program when clicked
		yesButton.addActionListener(e->Display.getInstance().exitApplication());
		
		Button noButton = new Button("Cancel"); //continues program when clicked
		noButton.addActionListener(e->diBox.dispose());
		
		Container buttonContainer = new Container(new FlowLayout(Component.CENTER)); //hold selection buttons
		buttonContainer.add(yesButton);
		buttonContainer.add(noButton);
		
		diBox.add(BorderLayout.SOUTH,buttonContainer);
		
		diBox.show(); //show modal on screen
	
	}
}