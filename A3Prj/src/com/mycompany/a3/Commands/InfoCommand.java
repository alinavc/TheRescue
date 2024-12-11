package com.mycompany.a3.Commands;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;

// Command to display project INFO
public class InfoCommand extends Command{
	public InfoCommand() {
		super("About");
	}
	@Override
	public void actionPerformed(ActionEvent ev) {
		Dialog diBox = new Dialog("Project Information");
		diBox.setLayout(new BorderLayout());
		
		diBox.add(BorderLayout.CENTER, new Label(
				("Alina Corpora \n"
					+ "CSC133-02 \n"
					+ "Assignment 2")));
		
		Button okButton = new Button("Ok");
		okButton.addActionListener(e->diBox.dispose());
		
		diBox.add(BorderLayout.SOUTH,okButton);
		
		diBox.show();
	}

}
