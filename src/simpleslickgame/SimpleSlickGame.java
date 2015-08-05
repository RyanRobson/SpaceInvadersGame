package simpleslickgame;

import it.randomtower.engine.ResourceManager;

import java.io.IOException;
import java.util.logging.Logger;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class SimpleSlickGame extends StateBasedGame{
	public final int GAME_STATE = 1;
	
	public static boolean restart = false;
	public SimpleSlickGame(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws SlickException{
		AppGameContainer app = new AppGameContainer(new SimpleSlickGame("Assignment"));
		app.setDisplayMode(1200,800,false);
		app.setTargetFrameRate(60);
		Display.setResizable(true);
		app.start();
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		//States are stored here 
		try {
			ResourceManager.loadResources("/data/resources.xml");
		} catch (IOException ex) {
			// TODO Auto-generated catch block
			Logger.getLogger(SimpleSlickGame.class.getName()).log(null);
		}
		addState(new GameWorld(GAME_STATE, gc));
	}
	public void launch(){
		
	}
	
}