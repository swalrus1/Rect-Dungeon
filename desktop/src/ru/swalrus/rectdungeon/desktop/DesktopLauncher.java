package ru.swalrus.rectdungeon.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ru.swalrus.rectdungeon.MyGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 400;
		config.height = 660;
		config.resizable = false;
		config.title = "Rect Dungeon";
		new LwjglApplication(new MyGame(), config);
	}
}
