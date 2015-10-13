package com.itc.mn;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.itc.mn.Pantallas.RenderScreen;

public class MainGame extends Game {

	@Override
	public void create () {
		this.setScreen(new RenderScreen(this));
        System.out.println(Gdx.files.getExternalStoragePath());
    }

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose() {
		super.dispose();
	}
}
