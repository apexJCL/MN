package com.itc.mn.Metodos;

import com.itc.mn.Cosas.FuncionX;

/**
 * Bisection Method
 */
public strictfp class Biseccion extends Metodo {

    private FuncionX fa, fb, fx;
    private Double xr, xranterior;

    {
        this.tipo = Tipo.BISECCION;
    }

    public Biseccion(String funcion, double a, double b, double ep){
        this.funcion = funcion;
        // Aqui v_inicial y v_final fungen como a y b
        this.v_inicial = a;
        this.v_final = b;
        this.ep = ep;
        // Inicializamos las funciones
        fa = new FuncionX(funcion);
        fb = new FuncionX(funcion);
        fx = new FuncionX(funcion);
        //Creamos los encabezados para la tabla de iteraciones
        encabezados = new String[]{bundle.get("iteration"), "a", "b", "f(a)", "f(b)", "xr", "f(xr)", bundle.get("ep")};
        calculaRaiz();
        ep_porcentual = (ep*100)+"%";
        // Creamos el titulo para la ventana
        creaTitulo();
    }

    public void calculaRaiz(){
        do {
            xr = (v_inicial + v_final) / 2d;
            resultados.add(new double[]{contador, v_inicial, v_final, fa.obtenerValor(v_inicial), fb.obtenerValor(v_final), xr, fx.obtenerValor(xr), error * 100d});
            if (xranterior != null)
                error = Math.abs((xr - xranterior) / xr);
            if (fa.obtenerValor() * fx.obtenerValor() < 0)
                v_final = xr;
            else if (fa.obtenerValor() * fx.obtenerValor() > 0)
                v_inicial = xr;
            System.out.println(fa.obtenerValor()*fx.obtenerValor());
            xranterior = xr;
            contador++;
        }
        while (error > ep && Math.abs(fa.obtenerValor() * fx.obtenerValor()) != 0);
        raiz = xr;
    }
}
