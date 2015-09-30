package com.itc.mn;

import com.badlogic.gdx.Game;
import com.itc.mn.Metodos.NewtonRaphson;
import com.itc.mn.Pantallas.RenderScreen;

public class MainGame extends Game {
	
	@Override
	public void create () {
		NewtonRaphson n = new NewtonRaphson("(x^3)-20", "3x^2", 1, 0.00001);
		System.out.println(n.getRaiz());
		this.setScreen(new RenderScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
}
