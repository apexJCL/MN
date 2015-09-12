package com.itc.mn;

import com.badlogic.gdx.Game;
import com.itc.mn.Cosas.Funcion;
import com.itc.mn.Cosas.FuncionX;
import com.itc.mn.Pantallas.RenderScreen;

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

		// Prueba de arreglo de valores
		FuncionX fx = new FuncionX("(x^2)/6");
		double[][] res = fx.obtenerRango(-10, 10, 1f);
        for (double[] valores : res) {
            System.out.println("x: "+valores[0]+" y: "+valores[1]);
        }
        this.setScreen(new RenderScreen(this,res));
	}

	@Override
	public void render () {
		super.render();
	}
}
