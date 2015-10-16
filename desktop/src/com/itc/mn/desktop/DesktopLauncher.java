package com.itc.mn.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.itc.mn.MainGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new MainGame(), config);
		config.width = 1024;
		config.height = 600;
		config.resizable = false;
		config.backgroundFPS = -1;
		config.addIcon("icon_128.png", Files.FileType.Internal);
		config.addIcon("icon.png", Files.FileType.Internal);
		config.title = "Graph";
	}
}
