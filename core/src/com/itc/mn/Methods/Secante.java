package com.itc.mn.Methods;

import com.itc.mn.Things.FuncionX;

public class Secante extends Method {

    private FuncionX fx_1, fx, fx1;
    private Double xi1, xi1ant;

    {
        tipo = Tipo.SECANTE;
    }

    public Secante(String funcion, double x_i, double xi, double ep){
        this.funcion = funcion;
        this.ep = ep;
        this.x_i = x_i;
        this.xi = xi;
        fx_1 = new FuncionX(funcion); // Inicializamos las funciones
        fx = new FuncionX(funcion);
        fx1 = new FuncionX(funcion);
        fx_1.valorVariable(x_i); // Actualizamos los valores de las funciones con los valores iniciales dados
        fx.valorVariable(xi);
        ep_porcentual = String.valueOf(ep * 100d) + "%";
        // Definimos encabezados
        encabezados = new String[]{bundle.get("iteration"), "xi-1", "xi", "f(xi-1)", "f(xi)", "xi+1", "f(xi+1)", bundle.get("ep")};
        calculaRaiz();
        creaTitulo();
    }

    public void calculaRaiz(){
        do {
            xi1 = xi - ((fx.obtenerValor(xi) * (x_i - xi)) / (fx_1.obtenerValor(x_i) - fx.obtenerValor(xi)));
            if (xi1ant != null) {
                error = Math.abs((xi1 - xi1ant) / xi1);
                resultados.add(new double[]{contador, x_i, xi, fx_1.obtenerValor(), fx.obtenerValor(), xi1, fx1.obtenerValor(xi1), error * 100d});
            } else
                resultados.add(new double[]{contador, x_i, xi, fx_1.obtenerValor(), fx.obtenerValor(), xi1, fx1.obtenerValor(), 100d});
            xi1ant = xi1;
            x_i = xi;
            xi = xi1;
            contador++;
        }
        while (error > ep);
        raiz = xi1;
    }
}
