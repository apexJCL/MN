package com.itc.mn;

import com.badlogic.gdx.Game;
import com.itc.mn.Metodos.PFijo;
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
		PFijo prueba = new PFijo("3/(x-2)", 1, 0.000001d);
		System.out.println("Raiz: "+prueba.obtenerRaiz()[0]);
		this.setScreen(new RenderScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
}
