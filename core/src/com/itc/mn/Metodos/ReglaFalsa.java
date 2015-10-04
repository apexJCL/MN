package com.itc.mn.Metodos;

import com.itc.mn.Cosas.FuncionX;

/**
 * Created by zero_ on 03/10/2015.
 */
public class ReglaFalsa extends Metodo{

    private float xr, xranterior;
    private FuncionX fa, fb, fx;

    public ReglaFalsa(String funcion, float a, float b, float ep) {
        this.funcion = funcion;
        // Aquí v_inicial y v_final fungen como a y b
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
        ep_porcentual = String.valueOf(ep*100)+"%";
        // Creamos el titulo para la ventana
        titulo_ventana = "Regla Falsa | Funcion: "+funcion+"| Raiz: "+raiz+" | ep: "+ep_porcentual;
    }

    public void calculaRaiz() {
        xr = (float) (v_final - ((fb.obtenerValor(v_final)*(v_inicial-v_final))/(fa.obtenerValor(v_inicial))-fb.obtenerValor(v_final)));
        resultados.add(new double[]{contador, v_inicial, v_final, fa.obtenerValor(v_inicial), fb.obtenerValor(v_final), xr, fx.obtenerValor(xr), 1});
        while (error > ep && (fa.obtenerValor()*fx.obtenerValor())!=0){
            if((fx.obtenerValor()*fa.obtenerValor()) > 0)
                v_inicial = xr;
            else if((fx.obtenerValor()*fa.obtenerValor()) < 0)
                v_final = xr;
            xranterior = xr;
            xr = (float) (v_final - ((fb.obtenerValor(v_final)*(v_inicial-v_final))/(fa.obtenerValor(v_inicial))-fb.obtenerValor(v_final)));
            error = Math.abs((xr-xranterior)/xr);
            contador++;
            resultados.add(new double[]{contador, v_inicial, v_final, fa.obtenerValor(v_inicial), fb.obtenerValor(v_final), xr, fx.obtenerValor(xr),error*100});
        }
        raiz = xr;
    }
}
