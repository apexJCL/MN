package com.itc.mn.Cosas;

import com.itc.mn.Metodos.Metodo;

import java.util.ArrayList;

/**
 * This class will be used to serialize objects, store them in json format so the user can save/load previous works.
 */
public class Results {

    private Metodo.Tipo tipo;
    private String[] encabezados;
    private ArrayList<double[]> resultados;
    private Double raiz;
    private String funcion, titulo;

    public Results() {
    }

    public void setEncabezados(String[] encabezados) {
        this.encabezados = encabezados;
    }

    public void setResultados(ArrayList<double[]> resultados) {
        this.resultados = resultados;
    }

    public void setRaiz(Double raiz) {
        this.raiz = raiz;
    }

    public void setFuncion(String funcion) {
        this.funcion = funcion;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setTipo(Metodo.Tipo tipo) {
        this.tipo = tipo;
    }

    public Metodo.Tipo getTipo() {
        return tipo;
    }

    public String[] getEncabezados() {
        return encabezados;
    }

    public ArrayList<double[]> getResultados() {
        return resultados;
    }

    public Double getRaiz() {
        return raiz;
    }

    public String getFuncion() {
        return funcion;
    }
}
