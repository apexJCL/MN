package com.itc.mn.Methods;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.I18NBundle;
import com.itc.mn.Things.Const;
import com.itc.mn.Things.FuncionX;
import com.itc.mn.Things.Results;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Default base-class for Methods
 */
public strictfp class Method {

    public Tipo tipo;
    public ArrayList<double[]> resultados;
    public Double inicio_r, fin_r, paso_r, v_inicial, v_final, x_i, xi;
    public double raiz, ep, error;
    protected String funcion, funcion2, ep_porcentual;
    protected int contador;
    protected String[] encabezados;
    protected String titulo_ventana;
    protected I18NBundle bundle = I18NBundle.createBundle(Gdx.files.internal("i18n/uilang"), new Locale(Locale.getDefault().toString().substring(0, Locale.getDefault().toString().indexOf('_'))));

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

    public Method() {
    }

    public Method(Results result) {
        this.tipo = result.getTipo();
        this.raiz = result.getRaiz();
        this.funcion = result.getFuncion();
        this.encabezados = result.getEncabezados();
        this.titulo_ventana = result.getTitulo();
        this.resultados = result.getResultados();
    }

    public String getTabTitle(){
        switch (tipo){
            case PUNTO_FIJO:
                return bundle.get("m_fixedpoint");
            case BISECCION:
                return bundle.get("m_bisection");
            case NEWTON_RAPHSON:
                return bundle.get("m_nr");
            case REGLA_FALSA:
                return bundle.get("m_falseposition");
            case SECANTE:
                return bundle.get("m_secant");
            default:
                return "No";
        }
    }

    public void creaTitulo(){
        // We get the format to show the root on the window
        Const tmp = Const.Load();
        switch (tipo){
            case PUNTO_FIJO:
                titulo_ventana = bundle.get("m_fixedpoint")+" | ";
                break;
            case BISECCION:
                titulo_ventana = bundle.get("m_bisection")+" | ";
                break;
            case NEWTON_RAPHSON:
                titulo_ventana = bundle.get("m_nr")+" | ";
                break;
            case REGLA_FALSA:
                titulo_ventana = bundle.get("m_falseposition")+" | ";
                break;
            case SECANTE:
                titulo_ventana = bundle.get("m_secant")+" | ";
                break;
        }
        titulo_ventana += bundle.get("function")+": "+funcion+"| "+bundle.get("root")+": "+new DecimalFormat(tmp.getFormat()).format(raiz)+" | "+bundle.get("epa")+": "+ep_porcentual;
    }

    public double[][] getRange(double inicio, double fin, double paso) {
        return new FuncionX(funcion).obtenerRango(inicio, fin, paso);
    }

    public String getWindowTitle(){ return titulo_ventana; }

    public String[] getTitles() { return encabezados; }

    /**
     * Returns an array of the function evalued in the default given limits (-10 to 10)
     * Used for graphing
     * @return bidimensional double array
     */
    public double[][] getRange(){
        return new  FuncionX(funcion).obtenerRango(inicio_r, fin_r, paso_r);
    }

    public double getRoot(){
        return raiz;
    }

    public Tipo _getTipo() {
        return tipo;
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

    /**
     * Regresa los datos, en este caso, una tabla con los valores correspondientes (i, x, fx, fg, xi, etx...)
     * @return Results arrray
     */
    public ArrayList<double[]> getResultados(){
        return resultados;
    }

    public enum Tipo {
        PUNTO_FIJO, BISECCION, NEWTON_RAPHSON, REGLA_FALSA, SECANTE
    }

}
