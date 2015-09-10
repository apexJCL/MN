package com.itc.mn;

import com.badlogic.gdx.Game;
import com.itc.mn.Screens.ExampleScreen;

public class MainGame extends Game {
	
	@Override
	public void create () {
		this.setScreen(new ExampleScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
}
