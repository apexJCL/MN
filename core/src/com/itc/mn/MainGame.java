package com.itc.mn;

import com.badlogic.gdx.Game;
import com.itc.mn.Metodos.NewtonRaphson;
import com.itc.mn.Pantallas.RenderScreen;

public class MainGame extends Game {
	{
		System.out.println("shite");
	}
	
	@Override
	public void create () {
		this.setScreen(new RenderScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
}
