package com.itc.mn;

import com.badlogic.gdx.Game;
import com.itc.mn.Pantallas.MenuPrincipal;
import com.itc.mn.Pantallas.RenderScreen;

import java.util.ArrayList;

public class MainGame extends Game {

	// Valores prueba
	private double[][] valoresPrueba;
	// Arreglo prueba
	private ArrayList<double[][]> funciones;
	
	@Override
	public void create () {
        //this.setScreen(new MenuPrincipal(this));
		this.setScreen(new RenderScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
}
