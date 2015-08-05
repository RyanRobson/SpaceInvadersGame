package simpleslickgame;

//import fuzzyTemp.FuzzyLogic;
//import fuzzyTemp.FuzzySet;
import it.randomtower.engine.ME;
import it.randomtower.engine.ResourceManager;
import it.randomtower.engine.entity.Entity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class NPC extends Entity {
	public final String NPC = "npc";
	public final String PLAYER = "Player";
	private final String[] enemies = { Enemy.ENEMY };
	NPCFuzzyLogic flg = new NPCFuzzyLogic();
	Bayes Bc = new Bayes();
	static Vector2f dist;

	private Image player, playerRight, playerLeft;
	private int fireRate = 50;
	private int milliCount = 0;
	private int millis = fireRate;
	private int milliStep = millis / 5;
	static int NPCLives = 5;
	static float NPCX;
	static float NPCY;
	float distance;
	float distX;
	float distY;
	static String threat;
	static String fuzzyThreat;
	static String mode;
	final int DIST_BETWEEN_PLAYER = 100;
	double mrgnOfError = 5;
	static String enemySize;
	static String enemyDistance;

	public NPC(float x, float y) {
		super(Player.getPlayerX(), Player.playerY + 20);
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
		dist = new Vector2f(x, y);
	}

	public void update(GameContainer gc, int delta) throws SlickException {
		super.update(gc, delta);
		resetGraphic();
		NPCX = x;
		NPCY = y;
		distX = distX / Enemy.getEnemyNo();
		distY = distY / Enemy.getEnemyNo();
		distance = dist.distance(Enemy.enemyDist) / 10;
		double size = Enemy.getEnemyNo();
		
		NPCFuzzyLogic fuzzyLogicSize = new NPCFuzzyLogic();
		NPCFuzzyLogic fuzzyLogicDist = new NPCFuzzyLogic();
		fuzzyLogicSize.clear();
		fuzzyLogicDist.clear();

		FuzzySet small = new FuzzySet(0,15,1);
		FuzzySet medium = new FuzzySet(10,25,1);
		FuzzySet large = new FuzzySet(20,100,1)	;
		
		FuzzySet close  =new FuzzySet(0,50,1);
		FuzzySet medDist  =new FuzzySet(40,100,1);
		FuzzySet far  =new FuzzySet(90,150,1);
		

		fuzzyLogicSize.add(small);
		fuzzyLogicSize.add(medium);
		fuzzyLogicSize.add(large);
		
		fuzzyLogicDist.add(close);
		fuzzyLogicDist.add(medDist);
		fuzzyLogicDist.add(far);
		
		String str = String.valueOf(size);
		double sz = 0;
		try {
			if (!str.equals(""))
				sz = Double.parseDouble(str);
		} catch (NumberFormatException e) {
			
		}
		
		double array[] = fuzzyLogicSize.evaluate(sz);
		double arrayDist[] = fuzzyLogicDist.evaluate(distance);
		
		double eClose = arrayDist[0];
		double eMedDist = arrayDist[1];
		double eFar = arrayDist[2];
		
		double eSmall = array[0];
		double eMedium = array[1];
		double eLarge = array[2];
		
		if((eSmall >eMedium) && (eSmall > eLarge)){
			enemySize = "small";
			if ((eFar>eMedDist) && (eFar>eClose)){
				enemyDistance = "far";
				fuzzyThreat = "low";
				calcThreat(fuzzyThreat,delta);
			}
			else{
				mediumThreat(delta);
				}
			}	
			else if ((eMedium >eSmall) && (eMedium > eLarge)){
				enemySize = "medium";
				if((eClose>eMedDist)&& (eClose>eFar)){
					enemyDistance = "close";
					fuzzyThreat = "high";
					calcThreat(fuzzyThreat,delta);
				}
				else if((eMedDist>eClose)&& (eMedDist>eFar)){
					enemyDistance = "medium";
					fuzzyThreat = "medium";
					calcThreat(fuzzyThreat,delta);
				}
				else if ((eFar>eMedDist) && (eFar>eClose)){
					enemyDistance = "far";
					fuzzyThreat = "low";
					calcThreat(fuzzyThreat,delta);
				}		 
			}
			else if ((eLarge >eMedium) && (eLarge > eSmall)){
				enemySize  ="large";
				if ((eFar>eMedDist) && (eFar>eClose)){
					enemyDistance = "far";
					fuzzyThreat = "high";
					calcThreat(fuzzyThreat,delta);
				}
				else {
					fuzzyThreat = "medium";
					calcThreat(fuzzyThreat,delta);
				}
		}
		NPCCollision();
		Bc.setEnemySize(enemySize);
		Bc.setDistance(enemyDistance);
		Bc.setPlayerLives(Player.getPlayerLives());
		Bc.Threat();
	}
	/*
	 * Threat Level methods
	 */

	public void lowThreat(int delta) {
		threat = "low";
		followMode(delta);
	}

	public void mediumThreat(int delta) {
		threat = "medium";
		helpMode(delta);
	}

	public void highThreat(int delta) {
		threat = "high";
		protectMode(delta);
	}

	public void NPCFire(float dist, int delta) {
		if (dist <= 100) {
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
	}

	/*
	 * NPC mode methods
	 */
	public void followMode(int delta) { // mode used in low threat to follow
		// human player
		mode = "Follow";

		double xDiff = x - Player.getPlayerX();

		if (Math.abs(xDiff) <= mrgnOfError) {

			if (y > (Player.getPlayerY() -64)) {
				moveUp(delta);
			} else if (y < (Player.getPlayerY()+64)) {
				moveDown(delta);
			}
		} else if (x > Player.getPlayerX()) {
			moveLeft(delta);
		} else if (x < Player.getPlayerX()) {
			moveRight(delta);
		}
		NPCFire(distance, delta);
	}

	public void helpMode(int delta) {// mode used in medium threat to help human
		// player
		mode = "Help";
		double yDiff = y - Player.getPlayerY();
		if (Math.abs(yDiff) <= mrgnOfError) {
			if (x > (Player.getPlayerX() + DIST_BETWEEN_PLAYER)) {
				moveLeft(delta);
			} else if (x < (Player.getPlayerX() - DIST_BETWEEN_PLAYER)) {
				moveRight(delta);
			}
		} else if (y > Player.getPlayerY()) {
			moveUp(delta);
		} else if (y < Player.getPlayerY()) {
			moveDown(delta);
		}
	}

	public void protectMode(int delta) { // mode used in high threat to protect
		// human player
		mode = "Protect";
		double xDiff = x - Player.getPlayerX();

		if (Math.abs(xDiff) <= mrgnOfError) {

			if (y > Player.getPlayerY()) {
				moveUp(delta);
			} else if (y < (Player.getPlayerY() - DIST_BETWEEN_PLAYER)) {
				moveDown(delta);
			}
		} else if (x > Player.getPlayerX()) {
			moveLeft(delta);
		} else if (x < Player.getPlayerX()) {
			moveRight(delta);
		}
		NPCFire(distance, delta);

	}// end of protectMode

	/*
	 * Move Methods
	 */

	public void moveRight(int delta) {
		if (x + player.getWidth() < world.width) {
			x += (.4 * delta);
			setGraphic(playerRight);
		}
	}

	public void moveLeft(int delta) {
		if (x > 0) {
			x -= (.4 * delta);
			setGraphic(playerLeft);
		}
	}

	public void moveUp(int delta) {
		if (y > 0) {
			y -= (.4 * delta);
			setGraphic(player);
		}
	}

	public void moveDown(int delta) {
		if (y + player.getHeight() < world.height) {
			y += (.4 * delta);
			setGraphic(player);
		}
	}

	public void NPCCollision() {
		if (collide(enemies, x, y) != null) {
			NPCLives--;
			System.out.println("NPC Lives Remaining: " + NPCLives);
			if (NPCLives <= 0) {
				this.destroy();
				System.out.println("NPC is dead");
			}
		}
	}
	public void resetGraphic(){
		setGraphic(player);
	}

	/*
	 * 
	 * Get methods
	 */
	public static String getMode() {
		return mode;
	}

	public static String getThreat() {
		return threat;
	}

	public static int getNPCLives() {
		return NPCLives;
	}

	public static float getNPCX() {
		return NPCX;
	}

	public static float getNPCY() {
		return NPCY;
	}
	public static String getEnemySize(){
		return enemySize;
	}
	public static String getEnemyDistance(){
		return enemyDistance;
	}
	static String getline()
	{
		String str="";
		char ch;
		try {
			do {
				ch = (char)System.in.read();
				if ( (ch!='\n') && (ch!='\r') )
					str+=ch;
			} while (ch!='\n');
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return str;
	}
	
	
	public void calcThreat(String fzyThreat, int delta){
		String BayesThreat = Bc.getBayesThreat();
		String fThreat = fzyThreat;
		// needs optimising 
		if ((BayesThreat.equals("high") && (fThreat.equals("high")))){
			highThreat(delta);			
		}
		else if ((BayesThreat.equals("high") && (fThreat.equals("medium")))){
			highThreat(delta);
		}
		else if ((BayesThreat.equals("high") && (fThreat.equals("low")))){
			mediumThreat(delta);
		}
		else if ((BayesThreat.equals("medium") && (fThreat.equals("high")))){
			highThreat(delta);
		}
		else if ((BayesThreat.equals("medium") && (fThreat.equals("meduim")))){
			mediumThreat(delta);
		}
		else if ((BayesThreat.equals("medium") && (fThreat.equals("low")))){
			lowThreat(delta);
		}
		else if ((BayesThreat.equals("low") && (fThreat.equals("high")))){
			mediumThreat(delta);
		}
		else if ((BayesThreat.equals("low") && (fThreat.equals("medium")))){
			mediumThreat(delta);
		}
		else if ((BayesThreat.equals("low") && (fThreat.equals("low")))){
			lowThreat(delta);
		}
		
	}
	
}
