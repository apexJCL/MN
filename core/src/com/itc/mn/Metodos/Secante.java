package com.itc.mn.Metodos;

import com.itc.mn.Cosas.FuncionX;

public class Secante extends Metodo {

    private FuncionX fx_1, fx, fx1;
    private Double xi1;

    public Secante(String funcion, double x_i, double xi, double ep){
        this.funcion = funcion;
        this.ep = ep;
        this.x_i = x_i;
        this.xi = xi;
        // Inicializamos las funciones
        fx_1 = new FuncionX(funcion);
        fx = new FuncionX(funcion);
        fx1 = new FuncionX(funcion);
        // Actualizamos los valores de las funciones con los valores iniciales dados
        fx_1.valorVariable(x_i);
        fx.valorVariable(xi);
        ep_porcentual = String.valueOf(ep * 100d) + "%";
        // Definimos encabezados
        encabezados = new String[]{"Iteracion", "xi-1", "xi", "f(xi-1)", "f(xi)", "xi+1", "f(xi+1)", "ep%"};
        creaTitulo();
        calculaRaiz();
    }

    public void calculaRaiz(){
        xi1 = xi - ((fx.obtenerValor(xi) * (x_i - xi)) / (fx_1.obtenerValor(x_i) - fx.obtenerValor(xi)));

    }
}
