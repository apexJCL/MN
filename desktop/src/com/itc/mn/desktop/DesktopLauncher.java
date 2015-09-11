package com.itc.mn.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.itc.mn.MainGame;
import com.itc.mn.Cosas.Const;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new MainGame(), config);
		config.width = Const.WIDTH;
		config.height = Const.HEIGHT;
	}
}
