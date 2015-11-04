package com.itc.mn.Methods;

import com.itc.mn.Things.FuncionX;

/**
 * Created by zero_ on 30/09/2015.
 */
public class NewtonRaphson extends Metodo {

    private FuncionX f, f1;
    private Double xi1;

    {
        this.tipo = Tipo.NEWTON_RAPHSON;
    }

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
        xi = v_inicial;
        calculaRaiz();
        ep_porcentual = String.valueOf(ep*100)+"%";
        encabezados = new String[]{bundle.get("iteration"), "xi", "f(xi)", "f'(xi)", "xi+1", bundle.get("ep")};
        creaTitulo();
    }

    public void calculaRaiz(){
        f = new FuncionX(funcion);
        f1 = new FuncionX(funcion2);
        do{
            if(xi1 != null)
                xi = xi1;
            xi1 = xi - (f.obtenerValor(xi) / f1.obtenerValor(xi));
            error = Math.abs((xi1-xi)/xi1);
            resultados.add(new double[]{contador, xi, f.obtenerValor(), f1.obtenerValor(), xi1, error});
            contador++;
        }
        while(error > ep);
        raiz = xi1;
    }
}
