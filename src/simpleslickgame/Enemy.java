package simpleslickgame;

import it.randomtower.engine.ME;
import it.randomtower.engine.ResourceManager;
import it.randomtower.engine.entity.Entity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class Enemy extends Entity{
	public static  String  ENEMY = "enemy";
	public static final double SPEED = 0.075;
	private int frameCount = 0;
	private int nextFrame = 500;
	int frame = 0;
	private int health = 100;
	public static int enemyNo = 0;
	static Vector2f enemyDist;
	static float enemyX;
	static float enemyY;

	public Enemy(float x, float y) {
		super(x, y);
		enemyX = x;
		enemyY=y;
		Image enemy = ResourceManager.getImage("squidMonster");
		setHitBox(8,1,22,30);
		setGraphic(enemy);
		addType(ENEMY);
		health = 100;
		enemyNo++;
		enemyDist = new Vector2f(x,y);
		//enemyDist.add(NPC.dist);
		
		
	}
	public void update (GameContainer gc, int delta) throws SlickException{
		super.update(gc, delta);
		frameCount += delta;
		if (frameCount <= nextFrame){
			frameCount -= nextFrame;
			frame++;
			enemyDist = new Vector2f(x,y);
			if (frame % 2 == 0 ){
				setGraphic(ResourceManager.getImage("squidMonsterMove"));
			}else{
				setGraphic(ResourceManager.getImage("squidMonster"));
			}
		}
		y+= (SPEED * delta); 
		if(y > ME.world.getHeight()){
			this.destroy();
			enemyNo--;
			Player.score-=10;
		}
	}
	@Override
	public void collisionResponse(Entity e){
		health-=100;
		if (health<=0){
			this.destroy();
			enemyNo--;
		}
	}
	
	public static int getEnemyNo() {
	    return enemyNo;
	}	
	public static Vector2f getEnemyDist(){
		return enemyDist;
	}
	public static float getEnemyX(){
		return enemyX;
	}
	public static float getEnemyY(){
		return enemyY;
	}
	

}
