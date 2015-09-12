package com.itc.mn.Cosas;

import org.nfunk.jep.JEP;

/**
 *  Es una ecuacion en funcion de x por defecto.
 *  Se puede definir otra variable (para expresarse en terminos de otra, como en terminos de y ó t)
 */
public class FuncionX {

    private JEP parser;
    private String ecuacion, variable;
    private double valorVariable;

    {
        parser = new JEP();
        // Le decimos al parser que habilite las constantes y funciones basicas
        parser.addStandardConstants();
        parser.addStandardFunctions();
        parser.setAllowUndeclared(true);
        // Habilitamos la multiplicacion implicita
        parser.setImplicitMul(true);
        variable = "x";
        valorVariable = 0;
    }

    /**
     * Crea una ecuacion en funcion de x (con valor de 0 por defecto)
     * @param ecuacion
     */
    public FuncionX(String ecuacion){
        this.ecuacion = ecuacion;
        // Le asignamos la funcion al parser
        parser.parseExpression(ecuacion);
        // Asignamos x con un valor por defecto
        parser.addVariable(variable, valorVariable);
    }

    /**
     * Le asigna un nuevo valor a la variable
     * @param valor Nuevo valor de la variable
     */
    public void valorVariable(double valor){
        valorVariable = valor;
        parser.addVariable(variable, valorVariable);
    }

    public void reemplazaVariable(String variable, double valor){
        parser.removeVariable(this.variable);
        this.variable = variable;
        parser.addVariable(variable, valor);
    }

    /**
     * Devuelve el valor de la funcion con el ultimo valor asignado
     * @return double
     */
    public double obtenerValor(){
        return parser.getValue();
    }

    /**
     * Evalua la funcion en un rango dado y devuelve los valores para graficar
     * @param inicio Inicio del rango
     * @param fin Fin del rango
     * @param paso Medida del paso
     * @return double[][] valores
     */
    public double[][] obtenerRango(double inicio, double fin, double paso){
        double precision = Math.abs(fin - inicio) / paso;
        double[][] tmp = new double[(int)precision + 1][2];
        // Lo usamos para evaluar del menor al mayor
        double menor = menor(inicio, fin);
        for (int i = 0; i <= precision; i+=paso) {
            double valor_x = menor + i;
            parser.addVariable(variable, valor_x);
            tmp[i][0] = valor_x;
            tmp[i][1] = parser.getValue();
        }
        return tmp;
    }

    private double menor(double a, double b){
        return (a < b) ? a: b;
    }

}