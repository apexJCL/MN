package com.itc.mn.Metodos;

import com.itc.mn.Cosas.FuncionX;

/**
 * Created by zero_ on 23/09/2015.
 */
public class PFijo extends Metodo{

    private FuncionX g;
    private double vInicial, ep;

    {
        tipo = Tipo.PUNTO_FIJO;
    }

    public PFijo (String funOriginal, String funDespejada, double vInicial, double ep){
        funcion = funOriginal;
        funcion2 = funDespejada;
        this.vInicial = vInicial;
        this.ep = ep;
        g= new FuncionX(funcion2);
        g.valorVariable(vInicial);
    }

    @Override
    public double[] getRaices() {
        raices = obtenerRaiz();
        return raices;
    }

    public double[] obtenerRaiz(){
        double[] raiz = new double[1];
        double error = 1;
        double gx = g.obtenerValor();
        double x = gx;
        resultados.add(new double[]{counter, x, gx, error});
        while(error > ep){
            g.valorVariable(gx);
            gx=g.obtenerValor();
            error= Math.abs(((gx-x)/gx));
            x=gx;
            counter++;
            resultados.add(new double[]{counter, x, gx, error});
        }
        raiz[0]=gx;
        return raiz;
    }
}