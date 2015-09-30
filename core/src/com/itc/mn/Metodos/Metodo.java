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
    protected int contador;
    public float inicio, fin, paso;
    public double[] raices;
    private String ep;
    protected String ep_porcentual;


    {
        // Inicializamos el ArrayList para guardar los valores de las iteraciones
        resultados = new ArrayList(0);
        // Contador para las iteraciones
        contador = 1;
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

    public double getRaiz() { return raices[0]; }

    public String get_errorporcentual() {
        return ep_porcentual;
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

    public float getInicio() {
        return inicio;
    }

    public float getFin() {
        return fin;
    }

    public float getPaso() {
        return paso;
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
