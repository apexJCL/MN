package com.itc.mn.Cosas;

import org.nfunk.jep.JEP;
import java.util.ArrayList;

/**
 * Created by zero_ on 11/09/2015.
 */
public class Funcion {

    private String ecuacion;
    private JEP parser;
    private ArrayList<String[]> variables;

    public Funcion(String ecuacion){
        this.ecuacion = ecuacion;
        parser = new JEP();
        // Le decimos al parser que habilite las constantes y funciones basicas
        parser.addStandardConstants();
        parser.addStandardFunctions();
        parser.setAllowUndeclared(true);
        // Habilitamos la multiplicacion implicita
        parser.setImplicitMul(true);
        // Inicializamos un arreglo para las variables
        variables = new ArrayList(1);
        // Parseamos la ecuacion
        parser.parseExpression(ecuacion);
    }

    /**
     * Agrega una variable con un valor que se encuentra en la ecuacion
     * @param variable Variable a agregar
     * @param valorDefecto Valor defecto
     * @return Cierto si se agrega, falso porque ya existe
     */
    public boolean agregaActualizaVariable(String variable, double valorDefecto){
        if(variableExiste(variable)) {
            buscarVariable(variable)[1] = String.valueOf(valorDefecto);
            actualizaParser();
            return true;
        }
        String[] nuevo = {variable, String.valueOf(valorDefecto)};
        variables.add(nuevo);
        actualizaParser();
        return true;
    }

    public double obtenerResultado(){
        return parser.getValue();
    }

    /**
     * Verifica si una variable ya esta declarada dentro de las variables actuales
     * @param variable variable a buscar
     * @return boolean
     */
    private boolean variableExiste(String variable){
        for (String[] valores : variables)
            if (valores[0] == variable)
                return true;
        return false;
    }

    private String[] buscarVariable(String variable){
        for (String[] valores : variables)
            if (valores[0] == variable)
                return valores;
        return null;
    }

    private boolean actualizaParser(){
        for(String[] valores : variables)
            parser.addVariable(valores[0], Double.parseDouble(valores[1]));
        return true;
    }

}
