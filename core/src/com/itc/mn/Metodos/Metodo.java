package com.itc.mn.Metodos;

import com.itc.mn.Cosas.FuncionX;

import java.util.ArrayList;

/**
 * Created by zero_ on 25/09/2015.
 */
public class Metodo {

    protected String funcion, funcion2;
    public Tipo tipo;
    public ArrayList<double[]> resultados;
    protected int counter;
    public float inicio, fin, paso;
    public double[] raices;

    {
        // Inicializamos el ArrayList para guardar los valores de las iteraciones
        resultados = new ArrayList(0);
        // Contador para las iteraciones
        counter = 1;
        // Por defecto
        inicio = -10;
        fin = 10;
        paso = 0.001f;
    }

    public enum Tipo{
        PUNTO_FIJO, BISECCION
    }

    public float[][] obtenerRango(float inicio, float fin, float paso){
        return new FuncionX(funcion).obtenerRango(inicio, fin, paso);
    }

    public float[][] obtenerRango(){
        return new  FuncionX(funcion).obtenerRango(inicio, fin, paso);
    }

    public double[] getRaices(){
        return raices;
    }

}
