package com.mycompany.a3;

import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.plaf.Border;
import com.mycompany.a3.Interfaces.IIterator;

public class MapView extends Container implements Observer {
	private GameWorld gwObj;
	private ActionListener al;
	private Container centerContainer;
	
	public MapView() { //center container of Game form
		setLayout(new BorderLayout());
		centerContainer= new Container();
		centerContainer.getAllStyles().setBorder(Border.createLineBorder(4,ColorUtil.rgb(255, 0, 0)));
		centerContainer.getAllStyles().setBgColor(ColorUtil.WHITE);
		centerContainer.getAllStyles().setBgTransparency(255);
		add(BorderLayout.CENTER,centerContainer);
	}
	
	@Override
	public void update(Observable observable, Object data) {
		// code here to call the method in GameWorld (Observable) that output the
		// game object information to the console
		if(observable instanceof GameWorld) {
			gwObj = (GameWorld) observable;
			gwObj.mButton(); //call map print
		}
		repaint(); //calls paint
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		GameObjectCollection gameObjs = gwObj.getObjCollection();
		
		// loop through while to redraw game objects
		IIterator thisVect = gameObjs.getIterator();
		GameObject currIndex;
		while(thisVect.hasNext())
		{
			currIndex=thisVect.getNext();
			currIndex.draw(g,new Point(getX(),getY()) );
		}
	}

	public void pointerPressed(int x,int y) {
		x= x-getParent().getAbsoluteX();
		y=y-getParent().getAbsoluteY();
		
		Point pPtrRelPrnt = new Point(x,y);
		Point pCmpRelPrnt = new Point(getX(),getY());
				
		GameObjectCollection gameObjs = gwObj.getObjCollection();
		
		// loop through while to check if astronauts are selected or not
		IIterator thisVect = gameObjs.getIterator();
		GameObject currIndex;
		while(thisVect.hasNext())
		{
			GameObject holdObj = thisVect.getNext();
			if(holdObj instanceof Astronauts) {
				Astronauts holdAstro = (Astronauts) holdObj;
				if(holdAstro.contains(pPtrRelPrnt, pCmpRelPrnt)) {
					holdAstro.setSelected(true);
				}
				else {
					holdAstro.setSelected(false);
				}
			}
		}
		repaint();
	}
	
	public void allowSelection() {
		al = new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				pointerPressed(ev.getX(), ev.getY());
			}
		};
		
		centerContainer.addPointerPressedListener(al);
	}
	
	public void removeSelection() {
		if(al!=null) {
			centerContainer.removePointerPressedListener(al);
		}
		
		GameObjectCollection gameObjs = gwObj.getObjCollection();
		IIterator thisVect = gameObjs.getIterator();
		GameObject currIndex;
		while(thisVect.hasNext())
		{
			currIndex=thisVect.getNext();
			if(currIndex instanceof Astronauts) {
				Astronauts holdAstro = (Astronauts) currIndex;
				if(holdAstro.isSelected()) {
					holdAstro.setSelected(false);
				}
			}
		}
	}
}