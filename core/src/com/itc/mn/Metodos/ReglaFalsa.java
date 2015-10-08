package com.itc.mn.Metodos;

import com.itc.mn.Cosas.FuncionX;

public class ReglaFalsa extends Metodo{

    private double xr;
    private double xranterior;
    private FuncionX fa, fb, fx;

    {
        this.tipo = Tipo.REGLA_FALSA;
    }

    public ReglaFalsa(String funcion, double a, double b, double ep) {
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
        ep_porcentual = String.valueOf(ep * 100d) + "%";
        // Creamos el titulo para la ventana
        creaTitulo();
    }

    public void calculaRaiz() {
        xr = v_final - ((fb.obtenerValor(v_final)*(v_inicial-v_final))/(fa.obtenerValor(v_inicial)-fb.obtenerValor(v_final)));
        resultados.add(new double[]{contador, v_inicial, v_final, fa.obtenerValor(v_inicial), fb.obtenerValor(v_final), xr, fx.obtenerValor(xr), 1});
        while (error > ep){
            if((fx.obtenerValor()*fa.obtenerValor()) > 0)
                v_inicial = xr;
            else if((fx.obtenerValor()*fa.obtenerValor()) < 0)
                v_final = xr;
            else if ((fa.obtenerValor()*fx.obtenerValor()) == 0)
                break;
            xranterior = xr;
            xr = v_final - ((fb.obtenerValor(v_final)*(v_inicial-v_final))/(fa.obtenerValor(v_inicial)-fb.obtenerValor(v_final)));
            error = Math.abs((xr-xranterior)/xr);
            contador++;
            resultados.add(new double[]{contador, v_inicial, v_final, fa.obtenerValor(v_inicial), fb.obtenerValor(v_final), xr, fx.obtenerValor(xr), error * 100d});
        }
        raiz = xr;
    }
}
