package com.itc.mn.Metodos;

import com.itc.mn.Cosas.FuncionX;


/**
 * Created by zero_ on 23/09/2015.
 */
public class PFijo extends Metodo{

    private FuncionX g;

    // Siempre modifiquen el tipo de metodo que es, para los encabezados en tabla
    {
        tipo = Tipo.PUNTO_FIJO;
    }

    public PFijo (String funOriginal, String funDespejada, double vInicial, double ep){
        funcion = funOriginal;
        funcion2 = funDespejada;
        this.inicio = (float) vInicial;
        this.ep = ep;
        ep_porcentual = (ep*100)+"%";
        g= new FuncionX(funcion2);
        g.valorVariable(vInicial);
        // Creamos los encabezados
        encabezados = new String[]{"Iteracion", "x", "g(x)", "ep%"};
    }

    @Override
    public double getRaiz() {
        raiz = obtenerRaiz();
        return raiz;
    }

    public double obtenerRaiz(){
        double raiz = 0;
        double error = 1;
        double gx = g.obtenerValor();
        double x = gx;
        resultados.add(new double[]{contador, x, gx, error*100});
        while(error > ep){
            gx=g.obtenerValor(gx);
            error= Math.abs(((gx-x)/gx));
            x = gx;
            contador++;
            resultados.add(new double[]{contador, x, gx, error*100});
        }
        raiz=gx;
        return raiz;
    }
}