package com.itc.mn;

import com.badlogic.gdx.Game;
import com.itc.mn.Pantallas.RenderScreen;

public class MainGame extends Game {

	@Override
	public void create () {
		this.setScreen(new RenderScreen(this));
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
