package simpleslickgame;

import it.randomtower.engine.World;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.newdawn.slick.util.ResourceLoader;

public class Level {
	public final static char ENEMY = '*';
	private ArrayList<String> lines = null;
	private static String startXCoord = null;
	private static String startYCoord = null;

	public static int levelNum;

	public static boolean levelExists(int levelNum) {
		String level = "data/levels/level" + levelNum + ".dat";
		try {
			ResourceLoader.getResourceAsStream(level);
		} catch (RuntimeException e) {
			return false;
		}
		return true;
	}

	public static Level load(int levelNum, World world) {
		String level = "data/levels/level" + levelNum + ".dat";
		Level levelLoaded = null;

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				ResourceLoader.getResourceAsStream(level)));
		levelLoaded = new Level();
		levelLoaded.lines = new ArrayList<String>();
		Level.levelNum = levelNum;
		int width = 0;
		int height = 0;

		try {
			int count = 0;
			while (reader.ready()) {
				String line = reader.readLine();
				count++;
				if (count == 1) {
					startXCoord = line;
				}
				if (count == 2) {
					startYCoord = line;
				}
				if (line == null) {
					break;
				}
				if (count > 2) {
					width = Math.max(line.length(), width);
					levelLoaded.lines.add(line);

				}
			}
		} catch (IOException ex) {
			System.out.println("Failed to load level");
		}
		height = levelLoaded.lines.size();
		levelLoaded.createdEntities(levelLoaded, width - 1, height - 1, world);
		System.out.println("Level " + levelNum + " Loaded Successfully");

		return levelLoaded;
	}

	private void createdEntities(Level levelsLoaded, int width, int height,
			World world) {
		int a = Integer.parseInt(startXCoord);
		int b = Integer.parseInt(startYCoord);
		for (int i = width; i >= 0; i--) {
			for (int J = height; J >= 0; J--) {
				char c = lines.get(J).charAt(i);

				float x = ((i + 1) * 22) + a;
				float y = ((J + 1) * 30) + b;

				switch (c) {
				case ENEMY:
					world.add(new Enemy(x, y));
					break;
				}
			}
		}
	}

}
