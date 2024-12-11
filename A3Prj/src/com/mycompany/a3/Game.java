package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.util.UITimer;
import com.mycompany.a3.Commands.*;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import java.lang.String;

public class Game extends Form implements Runnable{
	private GameWorld gw;
	private MapView mv; // new in A2
	private ScoreView sv; // new in A2
	private int tickSpeed = 20; // in msec
	private UITimer gameClock;
	private String gameStatus= "Pause"; // true if game clock ticking
	private StyleButton pausePlayButton;
	private BGSound bgMusic;
	private Dialog gameOverBox;
	
	private Container topContainer;
	private Container botContainer;
	private Container leftContainer;
	private Container rightContainer;
	
	private Toolbar myToolbar;
	
	//Buttons
	private StyleButton expandButton;
	private StyleButton contractButton;
	private StyleButton openDoorButton;
	private StyleButton rightButton;
	private StyleButton leftButton;
	private StyleButton upButton;
	private StyleButton downButton;
	private StyleButton teleAstroButton;
	private StyleButton teleAlienButton;
	private StyleButton healButton;
	private CheckBox soundSwitch;
	
	//Commands
	private ACommand teleAlienComm;
	private CCommand contractComm;
	private DCommand downComm;
	private ECommand expandComm;
	private LCommand leftComm;
	private OCommand teleAstroComm;
	private RCommand rightComm;
	private SCommand openDoorComm;
	private UCommand upComm;
	private ExitCommand exitComm;
	private HelpCommand helpComm;
	private InfoCommand infoComm;
	private SoundCommand soundSwitchComm;
	private PausePlayCommand pausePlayComm;
	private HealCommand healComm;
		
	public Game(){
	gw = new GameWorld(); // create Observable GameWorld
	mv = new MapView(); // create an Observer for the map
	sv = new ScoreView(); // create an Observer for the game state data
	gw.addObserver(mv); // register the map observer
	gw.addObserver(sv); // register the score observer
	
	// Fill toolbar title
	myToolbar = new Toolbar();
	setToolbar(myToolbar);
	this.setTitle("The Rescue Game"); //set title of form
	
// ----------- Border Setup ------------------------	
	setLayout(new BorderLayout()); //Initialize borderLayout
	
	topContainer = new Container(new FlowLayout(Component.CENTER));
	topContainer.add(sv);
	add(BorderLayout.NORTH,topContainer);
	
	leftContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
	add(BorderLayout.WEST,leftContainer);
	
	rightContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
	add(BorderLayout.EAST,rightContainer);
	
	botContainer = new Container(new FlowLayout(Component.CENTER));
	add(BorderLayout.SOUTH,botContainer);
	
	add(BorderLayout.CENTER,mv);
// Commands
	  teleAlienComm = new ACommand(gw);
	  contractComm = new CCommand(gw);
	  downComm = new DCommand(gw);
	  expandComm = new ECommand(gw);
	  leftComm = new LCommand(gw);
	  teleAstroComm = new OCommand(gw);
	  rightComm = new RCommand(gw);
	  openDoorComm = new SCommand(gw);
	  upComm = new UCommand(gw);	
	  exitComm = new ExitCommand();
	  helpComm = new HelpCommand();
	  infoComm = new InfoCommand();
	  soundSwitchComm = new SoundCommand(gw);
	  pausePlayComm = new PausePlayCommand(this);
	  healComm = new HealCommand(gw);
// ------------ Buttons/Side menu -------------------	
			expandButton = new StyleButton("Expand");
			expandButton.setCommand(expandComm);
			
			contractButton = new StyleButton("Contract");
			contractButton.setCommand(contractComm);
			
			openDoorButton = new StyleButton("Score");
			openDoorButton.setCommand(openDoorComm);
			
			rightButton = new StyleButton("Right");
			rightButton.setCommand(rightComm);
			
			leftButton = new StyleButton("Left");
			leftButton.setCommand(leftComm);
			
			upButton = new StyleButton("Up");
			upButton.setCommand(upComm);
			
			downButton = new StyleButton("Down");
			downButton.setCommand(downComm);
			
			teleAstroButton = new StyleButton("Move to Astronaut");
			teleAstroButton.setCommand(teleAstroComm);
			
			teleAlienButton = new StyleButton("Move to Alien");
			teleAlienButton.setCommand(teleAlienComm);
			
			pausePlayButton = new StyleButton("");
			pausePlayButton.setCommand(pausePlayComm);
			pausePlayButton.setText(gameStatus);
			
			healButton = new StyleButton("Heal");
			healButton.setCommand(healComm);
			
			Button emptyButton = new Button(" "); //space filler
			emptyButton.getAllStyles().setPaddingTop(8);
			Button emptyButton2 = new Button(" "); //space filler
			emptyButton2.getAllStyles().setPaddingTop(8);
			
			soundSwitch = new CheckBox("Sound");
			soundSwitch.setCommand(soundSwitchComm);
			soundSwitch.getAllStyles().setBgTransparency(255);
			soundSwitch.getAllStyles().setBgColor(ColorUtil.BLUE);
			soundSwitch.getAllStyles().setFgColor(ColorUtil.WHITE);
			
		// ---------- Attach Listeners --------------------
			//button listeners
			leftContainer.add(emptyButton);
			leftContainer.add(expandButton);
			leftContainer.add(upButton);
			leftContainer.add(leftButton);
			leftContainer.add(teleAstroButton);
			
			rightContainer.add(emptyButton2);
			rightContainer.add(contractButton);
			rightContainer.add(downButton);
			rightContainer.add(rightButton);
			rightContainer.add(teleAlienButton);
			rightContainer.add(openDoorButton);
			
			botContainer.add(healButton);
			botContainer.add(pausePlayButton);
			
			//key listeners
			addKeyListener('e',expandComm);
			addKeyListener('c',contractComm);
			addKeyListener('s',openDoorComm);
			addKeyListener('r',rightComm);
			addKeyListener('l',leftComm);
			addKeyListener('u',upComm);
			addKeyListener('d',downComm);
			addKeyListener('o',teleAstroComm);
			addKeyListener('a',teleAlienComm);
			
			// side menu and toolbar
			myToolbar.addComponentToLeftSideMenu(soundSwitch);
			myToolbar.addCommandToRightBar(helpComm);
			myToolbar.addCommandToLeftSideMenu(infoComm);
			myToolbar.addCommandToLeftSideMenu(exitComm);
			myToolbar.addCommandToLeftSideMenu(openDoorComm);
	
	// Implement timer for ticking game objects
	gameClock = new UITimer(this);
	gameClock.schedule(tickSpeed, true, this);
	enableCommands();
	
	// add all to form
	this.show();
	WorldSize.getCoords().setCoords(mv.getWidth(), mv.getHeight()); // add mapView width/height
	gw.init(); // initialize world
	gw.createSounds();
	revalidate();
	}

	@Override
	public void run() {
		gw.tButton(tickSpeed, this);
		if(gw.getAstrosOut()==0) {
			// --------------- Game over Dialog Box -------------
			gameOverBox = new Dialog("All Astronauts Rescued!");
			gameOverBox.setLayout(new BorderLayout());
			
			gameOverBox.add(BorderLayout.CENTER, new Label("Time: " + gw.getClock()
			+ "  Score: " + gw.getGameScore()
			+ "  Astronauts Rescued: " + gw.getAstrosIn()
			+ "  Aliens Sneaked In: "+ gw.getAliensIn()));
			
			Button yesButton = new Button("Exit"); //exits program when clicked
			yesButton.addActionListener(e->Display.getInstance().exitApplication());
			
			Container buttonContainer = new Container(new FlowLayout(Component.CENTER)); //hold selection buttons
			buttonContainer.add(yesButton);
			
			gameOverBox.add(BorderLayout.SOUTH,buttonContainer);
			gameOverBox.show();
		}
	}
	
	public void pauseTick() {
		gameClock.cancel();
		gameStatus="Play";
		mv.allowSelection();
		pausePlayButton.setText(gameStatus);
		gw.pauseBgMusic();
		// change button/command styling
		disableCommands();
	}
	
	public void playTick() {
		mv.removeSelection();
		gameClock.schedule(tickSpeed, true, this);
		gameStatus="Pause";
		pausePlayButton.setText(gameStatus);
		gw.playBgMusic();
		// change button/command styling
		enableCommands();
	}
	
	public void makeButtonUnstyled(StyleButton sb) {
		sb.getAllStyles().setBgColor(ColorUtil.WHITE);
		sb.getAllStyles().setFgColor(ColorUtil.BLUE);
		sb.getAllStyles().setPadding(3, 3, 5,5);
		sb.getAllStyles().setBgTransparency(255);
		sb.getAllStyles().setBorder(Border.createLineBorder(3,ColorUtil.BLACK));
	}
	
	public void makeButtonStyled(StyleButton sb) {
		sb.getAllStyles().setBgColor(ColorUtil.BLUE);
		sb.getAllStyles().setFgColor(ColorUtil.WHITE);
		sb.getAllStyles().setPadding(3, 3, 5,5);
		sb.getAllStyles().setBgTransparency(255);
		sb.getAllStyles().setBorder(Border.createLineBorder(3,ColorUtil.BLACK));
	}
	
	public void disableCommands()
	{
	// ------------ Buttons/Side menu -------------------
		expandButton.setEnabled(false);
		makeButtonUnstyled(expandButton);
		contractButton.setEnabled(false);
		makeButtonUnstyled(contractButton);
		openDoorButton.setEnabled(false);
		makeButtonUnstyled(openDoorButton);
		rightButton.setEnabled(false);
		makeButtonUnstyled(rightButton);
		leftButton.setEnabled(false);
		makeButtonUnstyled(leftButton);
		upButton.setEnabled(false);
		makeButtonUnstyled(upButton);
		downButton.setEnabled(false);
		makeButtonUnstyled(downButton);
		teleAstroButton.setEnabled(false);
		makeButtonUnstyled(teleAstroButton);
		teleAlienButton.setEnabled(false);
		makeButtonUnstyled(teleAlienButton);
		healButton.setEnabled(true);
		makeButtonStyled(healButton);
		
	// ---------- Attach Listeners --------------------
		
		//key listeners
		removeKeyListener('e',expandComm);
		removeKeyListener('c',contractComm);
		removeKeyListener('s',openDoorComm);
		removeKeyListener('r',rightComm);
		removeKeyListener('l',leftComm);
		removeKeyListener('u',upComm);
		removeKeyListener('d',downComm);
		removeKeyListener('o',teleAstroComm);
		removeKeyListener('a',teleAlienComm);
		
		// side menu and toolbar
		soundSwitch.setEnabled(true);
		helpComm.setEnabled(true);
		infoComm.setEnabled(true);
		exitComm.setEnabled(true);
		openDoorComm.setEnabled(false);
		removeCommand(openDoorComm);
		myToolbar.addCommandToLeftSideMenu(openDoorComm);

		//this.show();
		revalidate();
		
	}
	
	public void enableCommands()
	{	
	// ------------ Buttons/Side menu -------------------	
		expandButton.setEnabled(true);
		makeButtonStyled(expandButton);
		contractButton.setEnabled(true);
		makeButtonStyled(contractButton);
		openDoorButton.setEnabled(true);
		makeButtonStyled(openDoorButton);
		rightButton.setEnabled(true);
		makeButtonStyled(rightButton);
		leftButton.setEnabled(true);
		makeButtonStyled(leftButton);
		upButton.setEnabled(true);
		makeButtonStyled(upButton);
		downButton.setEnabled(true);
		makeButtonStyled(downButton);
		teleAstroButton.setEnabled(true);
		makeButtonStyled(teleAstroButton);
		teleAlienButton.setEnabled(true);
		makeButtonStyled(teleAlienButton);
		healButton.setEnabled(false);
		makeButtonUnstyled(healButton);
		
	// ---------- Attach Listeners --------------------
		
		//key listeners
		addKeyListener('e',expandComm);
		addKeyListener('c',contractComm);
		addKeyListener('s',openDoorComm);
		addKeyListener('r',rightComm);
		addKeyListener('l',leftComm);
		addKeyListener('u',upComm);
		addKeyListener('d',downComm);
		addKeyListener('o',teleAstroComm);
		addKeyListener('a',teleAlienComm);
		
		// side menu and toolbar
		soundSwitch.setEnabled(true);
		helpComm.setEnabled(true);
		infoComm.setEnabled(true);
		exitComm.setEnabled(true);
		openDoorComm.setEnabled(true);
		removeCommand(openDoorComm);
		myToolbar.addCommandToLeftSideMenu(openDoorComm);
		
		revalidate();
	}
}