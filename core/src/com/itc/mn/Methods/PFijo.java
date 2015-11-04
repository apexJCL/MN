package com.itc.mn.Methods;

import com.itc.mn.Things.FuncionX;


/**
 * Created by zero_ on 23/09/2015.
 */
public class PFijo extends Metodo{

    private FuncionX g;
    private double x, xi;

    {
        this.tipo = Tipo.PUNTO_FIJO;
    }

    // Siempre modifiquen el tipo de metodo que es, para los encabezados en tabla
    {
        tipo = Tipo.PUNTO_FIJO;
    }

    public PFijo (String funOriginal, String funDespejada, double vInicial, double ep){
        // Almacenamos los valores de las funciones para futuras referencias
        funcion = funOriginal;
        funcion2 = funDespejada;
        // Es el valor inicial de la evaluacion, guardado para futuros usos
        this.v_inicial = vInicial;
        // Valor del error porcentual, guardado para futuros usos
        this.ep = ep;
        // Creamos la funcion g(x), que es la que se evaluara
        g = new FuncionX(funcion2);
        // Creamos los encabezados
        encabezados = new String[]{bundle.get("iteration"), "x", "g(x)", bundle.get("ep")};
        // Calculamos la raiz para que este lista
        calculaRaiz();
        // Definimos el titulo para la ventana
        ep_porcentual = String.valueOf(ep*100)+"%";
        // Creamos encabezado
        creaTitulo();
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