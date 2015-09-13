package com.itc.mn;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.itc.mn.Cosas.FuncionX;
import com.itc.mn.Pantallas.RenderScreen;

import javax.swing.JOptionPane;
import java.util.ArrayList;

public class MainGame extends Game {

	// Valores prueba
	private double[][] valoresPrueba;
	// Arreglo prueba
	private ArrayList<double[][]> funciones;
	
	@Override
	public void create () {
		/** Para probar graficacion sencilla, descomente este bloque y comente el otro
		// Codigo para probar la clase renderizadora
		valoresPrueba = new double[2000][2000];
		for (int i = 0; i < 2000; i++) {
			valoresPrueba[i][0] = i;
			valoresPrueba[i][1] = i*i;
		}
		this.setScreen(new RenderScreen(this, valoresPrueba));
		 **/

        /** Para probar multi-grafica, descomente esto
         funciones = new ArrayList(2);
         for (int j = 0; j < 2; j++) {
         double[][] tmp = new double[2000][2000];
         for (int i = 0; i < 2000; i++) {
         tmp[i][0] = i;
         tmp[i][1] = i*i*i + (j*2);
         }
         funciones.add(tmp);
         }
         System.out.println(funciones.size());
         this.setScreen(new RenderScreen(this, funciones));
         **/
		String fun = "";
		// Prueba
		switch (Gdx.app.getType()){
			case Desktop:
				fun = JOptionPane.showInputDialog("Ingrese la ecuacion a graficar: ");
		}

		// Prueba de arreglo de valores
		FuncionX fx = new FuncionX(fun);
		float[][] res = fx.obtenerRango(-10, 10, .001f);
        this.setScreen(new RenderScreen(this,res));
	}

	@Override
	public void render () {
		super.render();
	}
}
