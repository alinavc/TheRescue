package com.mycompany.a3;

import java.util.Observable;
import java.util.Random;
import java.util.Vector;

import com.codename1.ui.Form;
import com.codename1.ui.util.UITimer;
import com.mycompany.a3.Alien;
import com.mycompany.a3.Astronauts;
import com.mycompany.a3.GameObject;
import com.mycompany.a3.Interfaces.IIterator;
import com.mycompany.a3.Interfaces.IMoving;
import com.mycompany.a3.Spaceship;
import com.mycompany.a3.WorldSize;

public class GameWorld extends Observable{
	private int initialAstros=4;
	private int currentClockTime;
	private int totalScore; //can be negative
	private int astrosInside;
	private int astrosOutside;
	private int aliensInside;
	private int aliensOutside;
	private int maxScore= initialAstros * 10;
	private Boolean soundSwitch=false;
	private float gameX;
	private float gameY;
	private Boolean scheduleRunning=false;
	private Boolean flagSchedRunning = false;
	
	private BGSound bgMusic;
	private Sound doorSound;
	private Sound fightSound;
	private Sound collSound;
	//create vector of game objects
	GameObjectCollection objVect;
	private Vector<Alien> holdAliens = new Vector<>();
	private Vector <GameObject> deleteOnScore= new Vector<>();
	Vector<GameObject> flaggedObjs = new Vector<>(); // holds game objects whose flag is true
	
	public void init() { //initialize game state objs
		gameX = WorldSize.getCoords().getGameX();
		gameY = WorldSize.getCoords().getGameY();
		objVect = new GameObjectCollection(gameX,gameY);
		updateOutsideCount();
		setChanged();
		notifyObservers();
	}
	
	public GameObjectCollection getObjCollection() {
		return objVect;
	}
	
// return game state vals (Astros/Aliens inside/outside, sounds, clock ticks, points)
	public int getAliensOut() {
		/*aliensOutside=0;
		IIterator thisVect = objVect.getIterator();
		GameObject currIndex;
		while(thisVect.hasNext())
		{
			currIndex=thisVect.getNext();
			if(currIndex instanceof Alien) {
				aliensOutside++;
			}
		}*/
		return aliensOutside;
	}
	
	public int getAliensIn() {
		return aliensInside;
	}
	
	public int getAstrosOut() {
		/*astrosOutside=0;
		IIterator thisVect = objVect.getIterator();
		GameObject currIndex;
		while(thisVect.hasNext())
		{
			currIndex=thisVect.getNext();
			if(currIndex instanceof Astronauts) {
				astrosOutside++;
			}
		}*/
		return astrosOutside;
	}
	
	public int getAstrosIn() {
		return astrosInside;
	}
	
	public int getClock() {
		return currentClockTime;
	}
	
	public int getGameScore() {
		return totalScore;
	}
	
	public Boolean getSoundStatus() {
		return soundSwitch;
	}
	
	private void updateOutsideCount() { //update count of game objects
		aliensOutside=0;
		astrosOutside=0;
		IIterator thisVect = objVect.getIterator();
		GameObject currIndex;
		while(thisVect.hasNext())
		{
			currIndex=thisVect.getNext();
			if(currIndex instanceof Alien) {
				aliensOutside++;
			}
			else if(currIndex instanceof Astronauts) {
				astrosOutside++;
			}
		}
		if(astrosOutside==0) {
			System.out.println("GAME OVER! Stats below:");
			pButton();
			//System.exit(0);
		}
	}

	public void soundButton() {
		if(soundSwitch == false)
		{
			soundSwitch=true;
			playBgMusic();
		}
		else {
			soundSwitch=false;
			bgMusic.pause();
		}
		setChanged();
		notifyObservers();
	}
	
	public void aButton() { //transfer spaceship to random ALIEN
		Random r = new Random();
		Vector<Alien> tempVect = new Vector<> ();
		IIterator thisVect = objVect.getIterator();
		IIterator thisVectCopy = objVect.getIterator();
		GameObject currIndex;
		while(thisVect.hasNext())
		{
			currIndex = thisVect.getNext();
			if(currIndex instanceof Alien)
			{
				tempVect.add((Alien)currIndex);
			}
		}
		
		if(!tempVect.isEmpty()) {
			int hold=(r.nextInt(tempVect.size())); //get random alien index
			
			while(thisVectCopy.hasNext())
			{
				currIndex = thisVectCopy.getNext();
				if(currIndex instanceof Spaceship)
				{
					Spaceship thisShip = (Spaceship) currIndex;
					thisShip.jumpToLocation(thisShip.getLocObj(),tempVect.get(hold).getLocX(), tempVect.get(hold).getLocY());
				}
			}
		}
		else {
			System.out.println("There are currently no aliens in space to teleport to.");
		}
		setChanged();
		notifyObservers();
	}
	public void oButton() { //transfer spaceship to random ASTRO
		Random r = new Random();
		Vector<Astronauts> tempVect = new Vector<>();
		IIterator thisVect = objVect.getIterator();
		IIterator thisVectCopy = objVect.getIterator();
		GameObject currIndex;
		while(thisVect.hasNext())
		{
			currIndex = thisVect.getNext();
			if(currIndex instanceof Astronauts)
			{
				tempVect.add((Astronauts) currIndex);
			}
		}
		
		if(!tempVect.isEmpty()) {
			int hold=(r.nextInt(tempVect.size()));	
			
			while(thisVectCopy.hasNext())
			{
				currIndex = thisVectCopy.getNext();
				if(currIndex instanceof Spaceship)
				{
					Spaceship thisShip = (Spaceship) currIndex;
					thisShip.jumpToLocation(thisShip.getLocObj(),tempVect.get(hold).getLocX(), tempVect.get(hold).getLocY());
				}
			}
			setChanged();
			notifyObservers();
		}
		else {
			System.out.println("There are currently no astronauts in space to teleport to.");
		}
		
	}
	public void rButton() { //move spaceship to the RIGHT
		IIterator thisVect = objVect.getIterator();
		GameObject currIndex;
		while(thisVect.hasNext())
		{
			currIndex = thisVect.getNext();
			if(currIndex instanceof Spaceship)
			{
				Spaceship thisShip = (Spaceship) currIndex;
				thisShip.moveRight(thisShip.getLocObj());
			}
		}
		setChanged();
		notifyObservers();
	}
	public void lButton() { //move spaceship to the LEFT
		IIterator thisVect = objVect.getIterator();
		GameObject currIndex;
		while(thisVect.hasNext())
		{
			currIndex = thisVect.getNext();
			if(currIndex instanceof Spaceship)
			{
				Spaceship thisShip = (Spaceship) currIndex;
				thisShip.moveLeft(thisShip.getLocObj());
			}
		}
		setChanged();
		notifyObservers();
	}
	public void uButton() { //move spaceship UP
		IIterator thisVect = objVect.getIterator();
		GameObject currIndex;
		while(thisVect.hasNext())
		{
			currIndex = thisVect.getNext();
			if(currIndex instanceof Spaceship)
			{
				Spaceship thisShip = (Spaceship) currIndex;
				thisShip.moveUp(thisShip.getLocObj());
			}
		}
		setChanged();
		notifyObservers();
	}
	public void dButton() { //move spaceship DOWN
		IIterator thisVect = objVect.getIterator();
		GameObject currIndex;
		while(thisVect.hasNext())
		{
			currIndex = thisVect.getNext();
			if(currIndex instanceof Spaceship)
			{
				Spaceship thisShip = (Spaceship) currIndex;
				thisShip.moveDown(thisShip.getLocObj());
			}
		}
		setChanged();
		notifyObservers();
	}
	public void eButton() { //EXPAND spaceship DOOR
		IIterator thisVect = objVect.getIterator();
		GameObject currIndex;
		while(thisVect.hasNext())
		{
			currIndex = thisVect.getNext();
			if(currIndex instanceof Spaceship)
			{
				Spaceship thisShip = (Spaceship) currIndex;
				thisShip.expandDoor();
			}
		}
		setChanged();
		notifyObservers();
	}
	public void cButton() { //CONTRACT spaceship DOOR
		IIterator thisVect = objVect.getIterator();
		GameObject currIndex;
		while(thisVect.hasNext())
		{
			currIndex = thisVect.getNext();
			if(currIndex instanceof Spaceship)
			{
				Spaceship thisShip = (Spaceship) currIndex;
				thisShip.contractDoor();
			}
		}
		setChanged();
		notifyObservers();
	}
	public void tButton(int tickSpeedInMsec, Game gameForm) { //TICK game clock
		IIterator thisVect = objVect.getIterator();
		GameObject vOne;
		GameObject vTwo;
		int updateDir;
		Vector<Alien> toSkipAliens = new Vector<>(); //holds aliens to skip collision handling
		
		currentClockTime++; //increment clock time
		
		while(thisVect.hasNext()) // make all moving objects move
		{
			vOne = thisVect.getNext();
				if(vOne instanceof IMoving) {
					if(vOne instanceof Alien) {
						Alien thisAlien = (Alien) vOne;
						updateDir=thisAlien.move(thisAlien.getLocObj(),thisAlien.getDir(),thisAlien.getSpeed(),tickSpeedInMsec);
						thisAlien.setSpecDir(updateDir);
						
						IIterator vectorTwo = objVect.getIterator();
						while(vectorTwo.hasNext()) {
							vTwo = vectorTwo.getNext();
							//check for collisions
							if(vOne!=vTwo) { // make sure dont collide with self
								thisAlien.updateCollVect();
								if(vTwo instanceof Alien) { // Alien collision
									Alien alienTwo = (Alien) vTwo;
									//skip if newly created alien
									if(toSkipAliens.contains(alienTwo) || toSkipAliens.contains(thisAlien)) {
										continue;
									}
									
									if(thisAlien.collidesWith(alienTwo)) {
										if(!alienTwo.getCollStat() && !thisAlien.getCollStat()) {
											holdAliens.add(new Alien(gameX,gameY));
											Alien newAlien= holdAliens.lastElement();
											newAlien.handleCollision(thisAlien); //changes spawn loc, update new child and parent 1 coll vectors
											
											flaggedObjs.add(newAlien);
											flaggedObjs.add(thisAlien);
											flaggedObjs.add(alienTwo);
											
											toSkipAliens.add(newAlien);
											
											if(getSoundStatus()) { //play collision sound if sound is ON
												collSound.play();
											}
										}
									}
								}
								else if(vTwo instanceof Astronauts) { // Alien VS Astro
									Astronauts astroTwo = (Astronauts) vTwo;
									astroTwo.updateCollVect();
									if(thisAlien.collidesWith(astroTwo) &&
											!thisAlien.getCollVect().contains(astroTwo) &&
											!astroTwo.getCollVect().contains(thisAlien)) {
										
										astroTwo.handleCollision(thisAlien);
										flaggedObjs.add(astroTwo);
										flaggedObjs.add(thisAlien);
										if(getSoundStatus()) { //play FIGHT sound if sound is ON
											fightSound.play();
										}
									}
								}
							}
							}
						}
					else if(vOne instanceof Astronauts) {
						Astronauts thisAstro = (Astronauts) vOne;
						updateDir=thisAstro.move(thisAstro.getLocObj(),thisAstro.getDir(),thisAstro.getSpeed(),tickSpeedInMsec);
						thisAstro.setSpecDir(updateDir);
					}
				}
		}
		if(!holdAliens.isEmpty() && !scheduleRunning) {
			scheduleRunning=true;
			scheduleAddAliens(gameForm, toSkipAliens);
		}
		
		if(!flaggedObjs.isEmpty() && !flagSchedRunning) {
			System.out.println("flags BEFORE: ");
			for(int i=0; i<flaggedObjs.size(); i++) { // add new aliens to official obj vector
				System.out.println(flaggedObjs.get(i));
			}
			flagSchedRunning=true;
			scheduleResetFlags(gameForm);
		}
		
		updateOutsideCount();
		setChanged();
		notifyObservers();
	}
	
	public void scheduleResetFlags(Game gameForm) {
		UITimer timer = new UITimer(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("RUNNING FLAGS");
				for(int i=0; i<flaggedObjs.size();i++) {
					if(flaggedObjs.get(i) instanceof Alien) {
						Alien tempAlien = (Alien) flaggedObjs.get(i);
						//tempAlien.setFalseCollStat();
					}
				}
				System.out.println("flags AFTER: ");
				for(int i=0; i<flaggedObjs.size(); i++) { // add new aliens to official obj vector
					System.out.println(flaggedObjs.get(i));
				}
				flaggedObjs.clear();
				flagSchedRunning=false;
			}
	});
		timer.schedule(200,false,gameForm);
}
	
	public void scheduleAddAliens(Game gameForm, Vector<Alien> skipVect) {
		UITimer timer = new UITimer(new Runnable() {
		
			@Override
			public void run() {
				for(int i=0; i<holdAliens.size(); i++) { // add new aliens to official obj vector
					System.out.println("LIST:"+holdAliens.get(i));
					objVect.add(holdAliens.get(i));
				}
				holdAliens.clear();
				skipVect.clear();
				scheduleRunning = false;
				
				updateOutsideCount();
				setChanged();
				notifyObservers();
			}
		});
		timer.schedule(100, false, gameForm);
	}
	
	public void sButton() {  // open door / update GAME SCORES / REMOVE eaten elems
		IIterator thisVect = objVect.getIterator();
		int doorSize;
		GameObject currIndex;
		
		if(getSoundStatus()) { //play DOOR sound if sound is ON
			doorSound.play();
		}
		
		while(thisVect.hasNext())
		{
			currIndex = thisVect.getNext();
			if(currIndex instanceof Spaceship)
			{
				Spaceship thisShip = (Spaceship) currIndex;
				thisShip.openDoor();
				doorSize=thisShip.getSize(); //get door size
				IIterator thisVectCopy = objVect.getIterator();
				while(thisVectCopy.hasNext()) { //go through object list
					currIndex=thisVectCopy.getNext();
					//create initial door bounds before OOB (recreate with each iteration)
					float bounds1=thisShip.getLocX()-thisShip.getSize()/2; //low x
					float bounds2=thisShip.getLocX()+thisShip.getSize()/2; //high x
					float bounds3=thisShip.getLocY()-thisShip.getSize()/2; //low y
					float bounds4=thisShip.getLocY()+thisShip.getSize()/2; //high y
					
					if(bounds1 <doorSize){ //NEGATIVE X OOB CATCH
						bounds1=0;
					}
					if(bounds2>WorldSize.getCoords().getGameX()-doorSize) { //PAST X OOB CATCH
						bounds2=WorldSize.getCoords().getGameX();
					}
					if(bounds3< doorSize) { //NEGATIVE Y OOB CATCH
						bounds3=0;
					}
					if (bounds4>WorldSize.getCoords().getGameY()-doorSize){ //PAST Y OOB CATCH
						bounds4=WorldSize.getCoords().getGameY();
					}
					try {
						deleteObjByBounds(currIndex,thisShip,bounds1,bounds2,bounds3,bounds4);
					} catch (ArrayIndexOutOfBoundsException e) {}
				}
				thisShip.closeDoor();
			}
		}
		
		// delete objs in gameObject collection
		deleteObjsFromScoreVect();
		
		updateOutsideCount();
		setChanged();
		notifyObservers();
	}
	
	private void deleteObjByBounds(GameObject opponent, Spaceship myShip, float b1,float b2,float b3, float b4) {
		IIterator thisVect = objVect.getIterator();
		
		
		while(!(thisVect.getNext().equals(opponent))) {}
		if(opponent.getLocX()>=b1 && //check if obj is in door range
				opponent.getLocX() <= b2 && //door size/2 because cant double the range for each side
						opponent.getLocY() >= b3 &&
								opponent.getLocY() <= b4 &&
				myShip.doorStatus()==true) { //check that door IS OPEN
			
			if(opponent instanceof Alien) {
				totalScore-=10; //-10 if alien enters spaceship
				
				deleteOnScore.add(opponent);
				aliensInside++;
			}
			if(opponent instanceof Astronauts) {
				Astronauts thisAstro= (Astronauts) opponent;
				totalScore+=thisAstro.getWorth(); //add astronaut score to game score
				deleteOnScore.add(opponent);
				astrosInside++;
			}
		}
	}
	
	private void deleteObjsFromScoreVect() {
		IIterator vectCopy = objVect.getIterator();
		IIterator countIndex = objVect.getIterator();
		int vectIndex=0;
		GameObject holdObj;
		
		// count amount of objects in game object collection
		while(countIndex.hasNext()) {
			countIndex.getNext();
			vectIndex++;
		}
		
		for(int i=0;i<vectIndex;i++) {
			if(vectCopy.hasNext()) {
				holdObj = vectCopy.getNext();
				if(holdObj != null) {
					if(deleteOnScore.contains(holdObj)) {
						vectCopy.remove();
					}
				}
			}
		}
		deleteOnScore.clear();
	}
	public void pButton() { //PRINT game vals
		String soundStatus;
		
		//set sound status to on or off from boolean
		if(getSoundStatus()) {
			soundStatus="ON";
		}
		else {
			soundStatus="OFF";
		}
		
		//Print clock val
		System.out.println("Seconds elapsed: "+ currentClockTime);
		//Print current score
		System.out.println("Current Score: "+ totalScore + " / "+ maxScore);
		//Print # astros inside/outside
		System.out.println("# Astronauts INSIDE: " + astrosInside + "\n# Astronauts OUTSIDE: " + astrosOutside);
		//Print # of aliens inside / outside 
		System.out.println("# Aliens INSIDE: " + aliensInside + "\n# Aliens OUTSIDE: " + aliensOutside);
		//Print Sound Status
		System.out.println("Sound Status: "+ soundStatus);
	}
	public void mButton() { //PRINT object stats
		IIterator thisVect = objVect.getIterator();
		GameObject currIndex;
		
		System.out.println("\n");
		while(thisVect.hasNext())
		{
			currIndex = thisVect.getNext();
			if(currIndex instanceof Spaceship) //print ship vals
			{
				System.out.println("Spaceship: "+ currIndex.printInfo());
			}
			else if(currIndex instanceof Astronauts) //print astro vals
			{
				System.out.println("Astronaut: "+ currIndex.printInfo());
			}
			else if(currIndex instanceof Alien) //print alien vals
			{
				System.out.println("Alien: "+currIndex.printInfo());
			}
		}
		System.out.println("\n");
	}
	
	public void healButton() {
		IIterator iter = objVect.getIterator();
		
		while(iter.hasNext()) {
			GameObject objHold = iter.getNext();
			if(objHold instanceof Astronauts) {
				Astronauts astroHold= (Astronauts) objHold;
				if(astroHold.isSelected()) {
					astroHold.healSelected();
				}
			}
		}
	}
	
	public void createSounds() {
		bgMusic = new BGSound("BGMusic.wav");
		doorSound = new Sound("Door.wav");
		fightSound = new Sound("BoxingBell.wav");
		collSound = new Sound("AlienLaugh.wav");
	}
	
	public void playBgMusic() {
		if(getSoundStatus()) { //play bg music if sound is ON
			bgMusic.play();
		}
	}
	
	public void pauseBgMusic() {
			bgMusic.pause();
	}
}