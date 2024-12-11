package com.mycompany.a3;

import java.io.InputStream;

import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;

public class BGSound implements Runnable {
	private Media m;
	
	public BGSound(String soundFile) {
		if(Display.getInstance().getCurrent()==null) {
			System.out.println("ERROR: Create sound obj AFTER calling show()");
			System.exit(0);
		}
			try {
				InputStream is=Display.getInstance().getResourceAsStream(getClass(),"/"+soundFile);
				m=MediaManager.createMedia(is, "audio/wav",this); //this adds runnable
			}
			catch(Exception e) {
				e.printStackTrace();
			}
	}
	
	public void play() {
		m.play();
	}
	
	public void pause() {
		m.pause();
	}

	@Override
	public void run() {
		m.setTime(0);
		play();
	}

}
