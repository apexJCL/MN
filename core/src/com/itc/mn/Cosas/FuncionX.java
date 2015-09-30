package com.itc.mn.Cosas;

import org.nfunk.jep.JEP;

/**
 *  Es una ecuacion en funcion de x por defecto.
 *  Se puede definir otra variable (para expresarse en terminos de otra, como en terminos de y o t)
 */
public strictfp class FuncionX {

    private JEP parser;
    private String ecuacion, variable;
    private double valorVariable;
    private float inicio, fin;
    private float[][] ultimoCalulado;

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
        inicio = -20;
        fin = 20;
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
     * Regresa el valor de la funcion directamente en el valor asignado
     * @param valorVariable valor a poner en la variable actual
     * @return
     */
    public double obtenerValor(double valorVariable){
        valorVariable(valorVariable);
        return obtenerValor();
    }

    /**
     * Evalua la funcion en un rango dado y devuelve los valores para graficar
     * @param inicio Inicio del rango
     * @param fin Fin del rango
     * @param paso Medida del paso
     * @return double[][] valores
     */
    public float[][] obtenerRango(float inicio, float fin, float paso){
        // Guardamos los valores de inicio o fin, para uso en otras funciones
        this.inicio = inicio;
        this.fin = fin;
        int precision = (int)(Math.abs(fin - inicio) / paso);
        float[][] tmp = new float[precision + 1][2];
        // Lo usamos para evaluar del menor al mayor
        float menor = menor(inicio, fin);
        for (int i = 0; i <= precision; i++) {
            float valor_x = menor + (paso*i);
            tmp[i][0] = valor_x;
            tmp[i][1] = (float)obtenerValor(valor_x);
        }
        return tmp;
    }

    /**
     * Devuelve el valor entre un rango dado, con un paso entre valores de 0.01
     * @param inicio Inicio del rango
     * @param fin Fin del rango
     * @return Valores de la expresion evaluada en el rango dado
     */
    public float[][] obtenerRango(float inicio, float fin){
        return obtenerRango(inicio, fin, 0.001f);
    }

    private float menor(float a, float b){
        return (a < b) ? a: b;
    }

}