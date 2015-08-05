package simpleslickgame;

import it.randomtower.engine.ME;
import it.randomtower.engine.ResourceManager;
import it.randomtower.engine.entity.Entity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;

public class Player extends Entity {
	public final String PLAYER = "Player";
	private final String[] enemies = { Enemy.ENEMY };
	public final String NPC = "npc";

	private Image player, playerRight, playerLeft;
	private int fireRate = 50;
	private int milliCount = 0;
	private int millis = fireRate;
	private int milliStep = millis / 5;
	static int playerLives = 5;
	static float playerX;
	static float playerY;
	public static int score;

	public Player(float x, float y) {
		super(x, y);
		player = ResourceManager.getImage("player");
		playerRight = ResourceManager.getImage("playerRight");
		playerLeft = ResourceManager.getImage("playerLeft");

		setGraphic(player);
		setHitBox(2, 6, 59, 52);
		addType(PLAYER);
		define("right", Input.KEY_RIGHT, Input.KEY_D);
		define("left", Input.KEY_LEFT, Input.KEY_A);
		define("up", Input.KEY_UP, Input.KEY_W);
		define("down", Input.KEY_DOWN, Input.KEY_S);
	}

	public void update(GameContainer gc, int delta) throws SlickException {
		super.update(gc, delta);
		Input input = gc.getInput();
		playerX = x;
		playerY = y;
		MouseListener ml = new MouseListener() {

			@Override
			public void inputEnded() {
				// TODO Auto-generated method stub

			}

			@Override
			public void inputStarted() {
				// TODO Auto-generated method stub

			}

			@Override
			public boolean isAcceptingInput() {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			public void setInput(Input arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(int arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseDragged(int arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseMoved(int arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(int arg0, int arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(int arg0, int arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseWheelMoved(int arg0) {
				// TODO Auto-generated method stub

			}
		};
		
		if (check("right") && x + player.getWidth() < world.width) {
			if ((collide(NPC, x, y)) != null)  {
				//playerX-=10;
			} else {
				x += (.4 * delta);
				setGraphic(playerRight);
			}
		}
		if (check("left") && x > 0) {
			if ((collide(NPC, x, y)) != null) {
				//playerX+=10;
			} else {
				x -= (.4 * delta);
				setGraphic(playerLeft);
			}
		}
		if (check("up") && y > 0) {
			if ((collide(NPC, x, y)) != null) {
				//playerY+=10;
			} else {
				y -= (.4 * delta);
				setGraphic(player);
			}
		}
		if (check("down") && y + player.getHeight() < world.height) {
			if ((collide(NPC, x, y)) != null) {
				//playerY-=10;
				
			} else {

				y += (.4 * delta);
				setGraphic(player);
			}
		}
		if (!check("right") && !check("left")) {
			setGraphic(player);
		}
		if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
			milliCount = +delta;
			while (milliCount > milliStep) {
				milliCount -= milliStep;
				millis -= milliStep;
			}
			if (millis <= 0) {
				Bullet b = new Bullet(x + 32, y);
				ME.world.add(b);
				millis = fireRate;

			}
		}
		if (collide(enemies, x, y) != null) {
			playerLives--;
			System.out.println("Player Lives Remaining: " + playerLives);
			if (playerLives <= 0) {
				this.destroy();
				System.out.println("Game Over");
			}
		}			
		}
	@Override
	public void collisionResponse(Entity e) {

	}

	public static int getPlayerLives() {
		return playerLives;
	}

	public static float getPlayerX() {
		return playerX;
	}

	public static float getPlayerY() {
		return playerY;
	}

	public static int getScore() {
		return score;
	}
	public static void setPlayerX(float x){
		playerX = x;
	}
	public static void setPlayerY(float y){
		playerY = y;
	}
}
