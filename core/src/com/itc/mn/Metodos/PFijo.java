package com.itc.mn.Metodos;

import com.itc.mn.Cosas.FuncionX;

/**
 * Created by zero_ on 23/09/2015.
 */
public class PFijo {
    private FuncionX g;
    private String fun;
    private double vInicial, ep;

    public PFijo (String fun, double vInicial, double ep){
        this.fun = fun;
        this.vInicial = vInicial;
        this.ep = ep;
        g= new FuncionX(fun);
        g.valorVariable(vInicial);
    }

    public double[] obtenerRaiz(){
        double[] raiz = new double[1];
        double error = 1;
        double gx = g.obtenerValor();
        double gxant = gx;
        while(error > ep){
            g.valorVariable(gx);
            gx=g.obtenerValor();
            error= Math.abs(((gx-gxant)/gx));
            gxant=gx;
            System.out.println(error);
        }
        raiz[0]=gx;
        return raiz;
    }
}