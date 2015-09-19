package com.itc.mn;

import com.badlogic.gdx.Game;
import com.itc.mn.Pantallas.MenuPrincipal;
import java.util.ArrayList;

public class MainGame extends Game {

	// Valores prueba
	private double[][] valoresPrueba;
	// Arreglo prueba
	private ArrayList<double[][]> funciones;
	
	@Override
	public void create () {
        this.setScreen(new MenuPrincipal(this));
	}

	@Override
	public void render () {
		super.render();
	}
}
