package com.itc.mn;

import com.badlogic.gdx.Game;
import com.itc.mn.Pantallas.RenderScreen;

public class MainGame extends Game {

	private RenderScreen rs;

	@Override
	public void create () {
		rs = new RenderScreen(this);
		this.setScreen(rs);
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
