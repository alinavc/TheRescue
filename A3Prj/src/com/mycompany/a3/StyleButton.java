package com.mycompany.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;

// button with style so every button doesnt need its own styling
// saves from copying code
public class StyleButton extends Button{
	
	public StyleButton(String text) {
		super(text);
		
		Style needsStyle = getAllStyles();
		needsStyle.setBgColor(ColorUtil.BLUE);
		needsStyle.setFgColor(ColorUtil.WHITE);
		needsStyle.setPadding(3, 3, 5,5);
		needsStyle.setBgTransparency(255);
		needsStyle.setBorder(Border.createLineBorder(3,ColorUtil.BLACK));
	}

}
