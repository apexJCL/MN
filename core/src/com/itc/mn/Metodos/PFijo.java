package com.itc.mn.Metodos;

import com.itc.mn.Cosas.FuncionX;


/**
 * Created by zero_ on 23/09/2015.
 */
public class PFijo extends Metodo{

    private FuncionX g;
    private double x, xi;

    // Siempre modifiquen el tipo de metodo que es, para los encabezados en tabla
    {
        tipo = Tipo.PUNTO_FIJO;
    }

    public PFijo (String funOriginal, String funDespejada, double vInicial, float ep){
        // Almacenamos los valores de las funciones para futuras referencias
        funcion = funOriginal;
        funcion2 = funDespejada;
        // Es el valor inicial de la evaluacion, guardado para futuros usos
        this.v_inicial = (float) vInicial;
        // Valor del error porcentual, guardado para futuros usos
        this.ep = ep;
        // Creamos la funcion g(x), que es la que se evaluara
        g = new FuncionX(funcion2);
        // Creamos los encabezados
        encabezados = new String[]{"Iteracion", "x", "g(x)", "ep%"};
        // Calculamos la raiz para que este lista
        calculaRaiz();
        // Definimos el titulo para la ventana
        ep_porcentual = String.valueOf(ep*100)+"%";
        titulo_ventana = "Punto Fijo | Funcion: "+funOriginal+"| Raiz: "+raiz+" | ep: "+ep_porcentual;
    }

    public void calculaRaiz(){
        raiz = 0;
        x = v_inicial;
        xi = g.obtenerValor(v_inicial);
        resultados.add(new double[]{contador, x, xi, error*100});
        while(error > ep){
            x = xi;
            xi = g.obtenerValor(x);
            error= Math.abs(((xi-x)/xi));
            contador++;
            resultados.add(new double[]{contador, x, xi, error*100});
        }
        raiz = xi;
    }
}