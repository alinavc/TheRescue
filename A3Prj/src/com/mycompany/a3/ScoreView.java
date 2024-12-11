package com.mycompany.a3;

import java.util.Observable;
import java.util.Observer;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;

public class ScoreView extends Container implements Observer {
	private String soundSwitch;
	Label statsTxt;
	
	String statsMsg = ("Time: 0" //make default scores
	+ "  Score: 0"
	+ "  Astronauts Rescued: 0"
	+ "  Aliens Sneaked In: 0"
	+ "  Astronauts Remaining: 4"
	+ "  Aliens Remaining: 3"
	+ "  Sound: OFF");
	
	public ScoreView() {
		statsTxt=new Label(statsMsg);
		statsTxt.getAllStyles().setFgColor(ColorUtil.BLUE); //set font color to blue
		add(statsTxt); //add to form
	}
	@Override
	public void update(Observable observable, Object data) {
		//code here to update labels from game state data in GameWorld (Observable)
		if(observable instanceof GameWorld) {
			GameWorld gw = (GameWorld) observable;
			
			if(gw.getSoundStatus()==true) {
				soundSwitch="ON";
			}
			else {
				soundSwitch="OFF";
			}
			
			statsTxt.setText("Time: " + gw.getClock()
						+ "  Score: " + gw.getGameScore()
						+ "  Astronauts Rescued: " + gw.getAstrosIn()
						+ "  Aliens Sneaked In: "+ gw.getAliensIn()
						+ "  Astronauts Remaining: "+ gw.getAstrosOut()
						+ "  Aliens Remaining: "+ gw.getAliensOut()
						+ "  Sound: "+ soundSwitch);
		}
	}
}