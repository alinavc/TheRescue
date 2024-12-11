package com.mycompany.a3;

import java.io.InputStream;

import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;

public class Sound {
	private Media m;
	
	public Sound(String soundFile) {
		if(Display.getInstance().getCurrent() == null) {
			System.out.println("Create Sound obj AFTER calling show.");
			System.exit(0);
		}
			try {
				InputStream is= Display.getInstance().getResourceAsStream(getClass(),"/"+soundFile);
				m=MediaManager.createMedia(is, "audio/wav");
				}
			catch(Exception e) {
					e.printStackTrace();
				}
		}
	
	public void play() {
		m.setTime(0);
		m.play();
	}
	
	public void pause() {
		m.pause();
	}
}
