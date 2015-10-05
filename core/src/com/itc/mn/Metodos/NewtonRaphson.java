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
    public NewtonRaphson(String funcion, String derivada, double aprox, double ep){
        this.funcion = funcion;
        this.funcion2 = derivada;
        this.ep = ep;
        this.v_inicial = aprox;
        calculaRaiz();
        ep_porcentual = String.valueOf(ep*100)+"%";
        encabezados = new String[]{"Iteracion", "xi", "f(xi)", "f'(xi)", "xi+1", "ep"};
        titulo_ventana = "Newton-Raphson | Funcion: "+funcion+"| Raiz: "+raiz+" | ep: "+ep_porcentual;
    }

    public void calculaRaiz(){
        f = new FuncionX(funcion);
        f1 = new FuncionX(funcion2);
        error = 1;
        double xi = v_inicial;
        double xi1 = xi - (f.obtenerValor(xi) / f1.obtenerValor(xi));
        error = Math.abs((xi1-xi)/xi1);
        resultados.add(new double[]{contador, xi, f.obtenerValor(), f1.obtenerValor(), xi1, error});
        while(error > ep) {
            xi = xi1;
            xi1 = xi - (f.obtenerValor(xi) / f1.obtenerValor(xi));
            error = Math.abs((xi1-xi)/xi1);
            resultados.add(new double[]{contador, xi, f.obtenerValor(), f1.obtenerValor(), xi1, error});
            contador++;
        }
        raiz = xi1;
    }
}
