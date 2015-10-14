package com.itc.mn.Metodos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Json;
import com.itc.mn.Cosas.Const;
import com.itc.mn.Cosas.FuncionX;
import com.itc.mn.Cosas.Results;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Default base-class for Methods
 */
public strictfp class Metodo {

    public Tipo tipo;
    public ArrayList<double[]> resultados;
    public Double inicio_r, fin_r, paso_r, v_inicial, v_final, x_i, xi;
    public double raiz, ep, error;
    protected String funcion, funcion2, ep_porcentual;
    protected int contador;
    protected String[] encabezados;
    protected String titulo_ventana;
    private Json json = new Json();
    private Preferences prefs = Gdx.app.getPreferences(Const.pref_name);

    {
        // Inicializamos el ArrayList para guardar los valores de las iteraciones
        resultados = new ArrayList(0);
        // Contador para las iteraciones
        contador = 1;
        // Por defecto, los que tengan _r son para graficar
        inicio_r = -10d;
        fin_r = 10d;
        paso_r = 0.001d;
        // Inicializamos el error
        error = 1d;
    }

    public Metodo() {
    }

    public Metodo(Results result) {
        this.tipo = result.getTipo();
        this.raiz = result.getRaiz();
        this.funcion = result.getFuncion();
        this.encabezados = result.getEncabezados();
        this.titulo_ventana = result.getTitulo();
        this.resultados = result.getResultados();
    }

    public void creaTitulo(){
        // We get the format to show the root on the window
        Const tmp = json.fromJson(Const.class, prefs.getString(Const.id));
        switch (tipo){
            case PUNTO_FIJO:
                titulo_ventana = "Punto Fijo | ";
                break;
            case BISECCION:
                titulo_ventana = "Biseccion | ";
                break;
            case NEWTON_RAPHSON:
                titulo_ventana = "Newton-Raphson | ";
                break;
            case REGLA_FALSA:
                titulo_ventana = "Regla Falsa | ";
                break;
            case SECANTE:
                titulo_ventana = "Secante | ";
                break;
        }
        titulo_ventana += "Funcion: "+funcion+"| Raiz: "+new DecimalFormat(tmp.getFormat()).format(raiz)+" | ep: "+ep_porcentual;
    }

    public enum Tipo{
        PUNTO_FIJO, BISECCION, NEWTON_RAPHSON, REGLA_FALSA, SECANTE
    }

    public double[][] obtenerRango(double inicio, double fin, double paso){
        return new FuncionX(funcion).obtenerRango(inicio, fin, paso);
    }

    public String getTitulo(){ return titulo_ventana; }

    public String[] getEncabezados() { return encabezados; }

    public double[][] obtenerRango(){
        return new  FuncionX(funcion).obtenerRango(inicio_r, fin_r, paso_r);
    }

    public double getRaiz(){
        return raiz;
    }

    public Tipo _getTipo() {
        return tipo;
    }

    public String getTipo(){
        switch (tipo){
            case PUNTO_FIJO:
                return "Punto Fijo";
            case BISECCION:
                return "Biseccion";
        }
        return "";
    }

    public String getFuncion() {
        return funcion;
    }

    public String getFuncion2() {
        return funcion2;
    }

    public int getContador() {
        return contador;
    }

    public double getInicio_r() {
        return inicio_r;
    }

    public double getFin_r() {
        return fin_r;
    }

    public double getPaso_r() {
        return paso_r;
    }

    // Esto regresa en forma de tabla las iteraciones

    /**
     * Regresa los datos, en este caso, una tabla con los valores correspondientes (i, x, fx, fg, xi, etx...)
     * @return Results arrray
     */
    public ArrayList<double[]> getResultados(){
        return resultados;
    }

}
