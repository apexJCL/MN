package com.itc.mn.Metodos;

import com.itc.mn.Cosas.FuncionX;

import java.util.ArrayList;

/**
 * Created by zero_ on 23/09/2015.
 */
public class PFijo extends Metodo{

    private FuncionX g;
    private double vInicial, ep;
    private ArrayList<double[]> resultados;
    private int counter;

    {
        tipo = Tipo.PUNTO_FIJO;
    }

    public PFijo (String fun, double vInicial, double ep){
        funcion = fun;
        this.vInicial = vInicial;
        this.ep = ep;
        g= new FuncionX(funcion);
        g.valorVariable(vInicial);
        // Inicializamos el ArrayList para guardar los valores de las iteraciones
        resultados = new ArrayList(0);
        // Contador para las iteraciones
        counter = 1;
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
        // debug
        for (double[] resultado : resultados) {
            for (double res : resultado) {
                System.out.print(res + "\t");
            }
            System.out.println("\n");
        }
        return raiz;
    }
}