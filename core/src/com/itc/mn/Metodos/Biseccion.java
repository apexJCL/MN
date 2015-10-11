package com.itc.mn.Metodos;

import com.itc.mn.Cosas.FuncionX;

/**
 * Created by zero_ on 11/09/2015.
 */
public class Biseccion extends Metodo {

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
        encabezados = new String[]{"Iteracion", "a", "b", "f(a)", "f(b)", "xr", "f(xr)", "ep"};
        calculaRaiz();
        ep_porcentual = String.valueOf(ep*100d)+"%";
        // Creamos el titulo para la ventana
        creaTitulo();
    }

    public void calculaRaiz(){
        do {
            if (xranterior != null)
                error = Math.abs((xr - xranterior) / xr);
            xr = (v_inicial + v_final) / 2d;
            System.out.println(fx.obtenerValor() * fa.obtenerValor());
            if((fx.obtenerValor()*fa.obtenerValor()) > 0)
                v_inicial = xr;
            else if((fx.obtenerValor()*fa.obtenerValor()) < 0)
                v_final = xr;
            xranterior = xr;
            resultados.add(new double[]{contador, v_inicial, v_final, fa.obtenerValor(v_inicial), fb.obtenerValor(v_final), xr, fx.obtenerValor(xr), error * 100d});
            contador++;
        }
        while (error > ep && (fa.obtenerValor()) * fx.obtenerValor() != 0);
        raiz = xr;
//        xr = (v_inicial + v_final)/2d;
//        resultados.add(new double[]{contador, v_inicial, v_final, fa.obtenerValor(v_inicial), fb.obtenerValor(v_final), xr, fx.obtenerValor(xr), 1});
//        while (error > ep && (fa.obtenerValor()*fx.obtenerValor()) != 0){
//            if((fx.obtenerValor()*fa.obtenerValor()) > 0)
//                v_inicial = xr;
//            else if((fx.obtenerValor()*fa.obtenerValor()) < 0)
//                v_final = xr;
//            xranterior = xr;
//            xr = (v_inicial + v_final)/2d;
//            error = Math.abs((xr-xranterior)/xr);
//            contador++;
//            resultados.add(new double[]{contador, v_inicial, v_final, fa.obtenerValor(v_inicial), fb.obtenerValor(v_final), xr, fx.obtenerValor(xr),error*100d});
//            System.out.println(error);
//        }
//        raiz = xr;
    }
}
