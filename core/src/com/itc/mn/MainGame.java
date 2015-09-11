package com.itc.mn;

import com.badlogic.gdx.Game;
import com.itc.mn.Pantallas.ExampleScreen;

public class MainGame extends Game {

	// Valores prueba
	private double[][] valoresPrueba;
	
	@Override
	public void create () {
		// Codigo para probar la clase renderizadora
		valoresPrueba = new double[2000][2000];
		for (int i = 0; i < 2000; i++) {
			valoresPrueba[i][0] = i;
			valoresPrueba[i][1] = i*i;
		}
		this.setScreen(new ExampleScreen(this, valoresPrueba));
	}

	@Override
	public void render () {
		super.render();
	}
}
