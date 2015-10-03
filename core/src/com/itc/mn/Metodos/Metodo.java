package com.itc.mn.Metodos;

import com.itc.mn.Cosas.FuncionX;

import java.util.ArrayList;

/**
 * Created by zero_ on 25/09/2015.
 */
public strictfp class Metodo {

    public Tipo tipo;
    public ArrayList<double[]> resultados;
    public float inicio_r, fin_r, paso_r, v_inicial, v_final;
    public double raiz, ep, error;
    protected String funcion, funcion2, ep_porcentual;
    protected int contador;
    protected String[] encabezados;
    protected String titulo_ventana;

    {
        // Inicializamos el ArrayList para guardar los valores de las iteraciones
        resultados = new ArrayList(0);
        // Contador para las iteraciones
        contador = 1;
        // Por defecto, los que tengan _r son para graficar
        inicio_r = -10f;
        fin_r = 10f;
        paso_r = 0.001f;
        // Inicializamos el error
        error = 1;
    }

    public enum Tipo{
        PUNTO_FIJO, BISECCION, NEWTON_RAPHSON, REGLA_FALSA, SECANTE
    }

    public float[][] obtenerRango(float inicio, float fin, float paso){
        return new FuncionX(funcion).obtenerRango(inicio, fin, paso);
    }

    public String getTitulo(){ return titulo_ventana; }

    public String[] getEncabezados() { return encabezados; }

    public float[][] obtenerRango(){
        return new  FuncionX(funcion).obtenerRango(inicio_r, fin_r, paso_r);
    }

    public double getRaiz(){
        return raiz;
    }

    public String getTipo(){
        switch (tipo){
            case PUNTO_FIJO:
                return "Punto Fijo";
            case BISECCION:
                return "Biseccion";
        }
        return "";
    }

    public String getFuncion() {
        return funcion;
    }

    public String getFuncion2() {
        return funcion2;
    }

    public int getContador() {
        return contador;
    }

    public float getInicio_r() {
        return inicio_r;
    }

    public float getFin_r() {
        return fin_r;
    }

    public float getPaso_r() {
        return paso_r;
    }

    // Esto regresa en forma de tabla las iteraciones

    /**
     * Regresa los datos, en este caso, una tabla con los valores correspondientes (i, x, fx, fg, xi, etx...)
     * @return
     */
    public ArrayList<double[]> getResultados(){
        return resultados;
    }

}
