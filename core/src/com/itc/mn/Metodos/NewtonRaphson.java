package com.itc.mn.Metodos;

import com.itc.mn.Cosas.FuncionX;

/**
 * Created by zero_ on 30/09/2015.
 */
public class NewtonRaphson extends Metodo {

    private FuncionX f, f1;

    /**
     * Metodo Newton-Raphson
     * @param funcion Funcion original
     * @param derivada Primer derivada de la funcion original
     * @param aprox Aproximacion inicial
     * @param ep error porcentual
     */
    public NewtonRaphson(String funcion, String derivada, float aprox, double ep){
        this.funcion = funcion;
        this.funcion2 = derivada;
        this.ep = ep;
        this.inicio = aprox;
        calculaRaiz();
        encabezados = new String[]{"Iteracion", "xi", "f(xi)", "f'(xi)", "xi+1", "ep"};
    }

    public void calculaRaiz(){
        f = new FuncionX(funcion);
        f1 = new FuncionX(funcion2);
        double error = 1;
        double xi = inicio;
        double xi1 = xi - (f.obtenerValor(xi) / f1.obtenerValor(xi));
        error = Math.abs((xi1-xi)/xi1);
        while(error > ep) {
            xi = xi1;
            xi1 = xi - (f.obtenerValor(xi) / f1.obtenerValor(xi));
            error = Math.abs((xi1-xi)/xi1);
        }
        raiz = xi1;
    }
}
