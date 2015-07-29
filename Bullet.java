package simpleslickgame;

import it.randomtower.engine.ME;
import it.randomtower.engine.ResourceManager;
import it.randomtower.engine.entity.Entity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Bullet extends Entity {
	public static String BULLET = "bullet";
	private final String[] enemies = { Enemy.ENEMY };

	public Bullet(float x, float y) {
		super(x, y);
		Image bullet = ResourceManager.getImage("bullet");
		setGraphic(bullet);
		setHitBox(2, 1, 4, 6);
		addType(BULLET);
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		y += (.7 - delta);

		if (collide(enemies, x, y) != null) {
			this.destroy();
			Player.score += 10;
		}

		if (y <= 0) {
			ME.world.remove(this);

		}
		super.update(gc, delta);
	}

}
