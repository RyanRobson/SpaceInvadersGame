package simpleslickgame;

import it.randomtower.engine.World;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class GameWorld extends World {
	public static int levelNum =1;
	public static Level level;


	public GameWorld(int id, GameContainer gc) {
		super(id, gc);
	}

	@Override
	public void init(GameContainer gc, StateBasedGame game)
			throws SlickException {
		super.init(gc, game);
		System.out.println("Level No:" + levelNum);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta)
			throws SlickException {
		super.update(gc, game, delta);

		if (Enemy.getEnemyNo() <= 0) {
			loadLevel(game);
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g)
			throws SlickException {
		super.render(gc, game, g);
		g.drawString("Level: " + (levelNum - 1), getWidth() - 170, 20);
		g.drawString("Player Lives: " + Player.getPlayerLives(),
				getWidth() - 170, 40);
		g.drawString("NPC Lives: " + NPC.getNPCLives(), getWidth() - 170, 60);
		g.drawString("No of Enemies: " + Enemy.getEnemyNo(), getWidth() - 170,
				80);
		g.drawString("Score: " + Player.getScore(), getWidth() - 170, 100);
		g.drawString("Threat: " + NPC.getThreat(), getWidth()-170, 120);
		g.drawString("NPC Mode: " + NPC.getMode(), getWidth()-170, 140);
		g.drawString("Controls: ", getWidth()-170, 160);
		g.drawString("W - Move Up",getWidth()-170,180);
		g.drawString("A - Move Left", getWidth()-170,200);
		g.drawString("S - Move Down", getWidth()-170,220);
		g.drawString("D - Move Right",getWidth()-170, 240);
		g.drawString("Mouse Click - Fire", getWidth()-170, 260);
		g.drawString("AI has 3 Modes: \nProtect, Help \nand Follow", getWidth()-170, 280);
		if (Player.playerLives <= 0 && NPC.NPCLives <= 0) {
			
			gameOver(gc, g);
		}
	}

	@Override
	public void enter(GameContainer gc, StateBasedGame game)
			throws SlickException {
		super.enter(gc, game);
		loadLevel(game);

	}

	private void loadLevel(StateBasedGame game) {

		if (Level.levelExists(levelNum)) {
			this.clear();
			if(Player.playerLives>0){
				if (levelNum == 1){
					Player p = new Player(getWidth() / 2, getHeight() / 2);
					NPC n = new NPC(getWidth() / 2 +100, getHeight() / 2 );
					add(p);
					add(n);
				} else {
					
					Player p = new Player(Player.getPlayerX(), Player.getPlayerY());
					add(p);
					if((NPC.NPCLives>0)){
						NPC n = new NPC(NPC.getNPCX(), NPC.getNPCY());	
						add(n);
					}					
				}
			level = Level.load(levelNum, this);
			levelNum++;
		}
		}
		else{
			System.out.println("Game Over");
		}
		
		}
	

	public void gameOver(GameContainer gc, Graphics g) throws SlickException {
		Input input = gc.getInput();
		this.clear();
		g.drawString("Game Over, Your score was " + Player.getScore() +"\n Press Esc to exit", getWidth() / 2,
				getHeight() / 2);

		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			System.exit(0);
		}
	}
}
