package com.itc.mn.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.itc.mn.Methods.Method;
import com.itc.mn.Things.Const;
import com.itc.mn.Things.FuncionX;
import com.itc.mn.Things.Results;

import java.util.ArrayList;

/**
 * This screen manages the rendering of functions, independly from GUI
 */
public class RenderScreen extends Pantalla {

    private double raiz;
    private double[][] valores;
    private volatile boolean isRootAvailable;
    private ArrayList<double[][]> funciones;
    private Color[] colores = {Color.BLUE, Color.GREEN, Color.CYAN, Color.YELLOW, Color.FIREBRICK, Color.ROYAL, Color.RED, Color.SALMON, Color.MAGENTA, Color.LIME, Color.TAN, Color.TEAL, Color.VIOLET};
    private Method metodo;
    private Const config = Const.Load();

    {
        gui_stage.setDebugAll(true);
        isRootAvailable = false;
    }

    /**
     * Recibe el arrreglo de valores de una funcion y lo grafica
     *
     * @param game    Referencia a Game para manejo de pantallas
     * @param valores Valores de la funcion
     */
    public RenderScreen(Game game, double[][] valores, boolean isInputVisible) {
        super(game);
        // Solo para tener una referencia al manejador de pantallas
        this.valores = valores;
        gui_stage.createTablaRes(metodo);
    }

    public RenderScreen(Game game, double[][] valores, double raiz) {
        super(game);
        this.valores = valores;
        this.raiz = raiz;
        isRootAvailable = true;
    }

    /**
     * Crea una instancia de RenderScreen con el metodo asignado, usandolo para obtener la funcion y graficar
     * respectivamente
     *
     * @param game   Instancia Game para cambiar pantallas
     * @param metodo Objeto metodo
     */
    public RenderScreen(Game game, Method metodo, boolean inputEnabled) {
        super(game);
        this.metodo = metodo;
        this.valores = metodo.getRange();
        this.raiz = metodo.getRoot();
        isRootAvailable = true;
        gui_stage.createTablaRes(metodo);
        gui_stage.enableIterTable(true);
        gui_stage.setInputVisible(inputEnabled);
        gui_stage.showTablaIter();
    }

    public RenderScreen(Game game, Results results) {
        super(game);
        this.valores = new FuncionX(results.getFuncion()).obtenerRango(-10, 10);
        this.raiz = results.getRaiz();
        isRootAvailable = true;
        gui_stage.createTableRes(results);
        gui_stage.enableIterTable(true);
        gui_stage.setInputVisible(false);
        gui_stage.showTablaIter();
    }

    /**
     * Recibe un ArrayList de varias funciones, para graficacion multiple
     *
     * @param game      Referencia a Game para manejo de pantallas
     * @param funciones ArrayList con arreglos de valores para cada funcion
     */
    public RenderScreen(Game game, ArrayList<double[][]> funciones, boolean isInputVisible) {
        super(game);
        this.funciones = funciones;
        // Este valor es por si se desea mostrar para "graficar" al vuelo o solo se quieren ver resultados
    }

    /**
     * Constructor defecto, solo hace la instancia con una grafica default de x
     *
     * @param game Instancia de Game que controla las pantallas
     */
    public RenderScreen(Game game) {
        super(game);
        valores = new FuncionX("x").obtenerRango(-10, 10, 0.001f);
        camera.zoom = 0.5f;
    }

    private void renderArray() {
        // Para que se renderize con la camara
        shapeRenderer.setProjectionMatrix(camera.combined);
        // Para comenzar el renderizado
        shapeRenderer.begin(ShapeRenderer.ShapeType.Point);
        // Para renderizar solo cuando tenemos el arreglo sencillo de valores
        if (valores != null) {
            // Obviamente define el color
            shapeRenderer.setColor(config.singleGraphic);
            // Procesando arreglo
            for (double[] valor : valores)
                shapeRenderer.point((float) (valor[0] * scaleX), (float) (valor[1] * scaleY), 0);
        } else {
            int counter = 0;
            for (double[][] funcion : funciones) {
                if (counter < funciones.size())
                    counter++;
                else
                    counter = 0;
                shapeRenderer.setColor(colores[counter]);
                for (int i = 0; i < funcion.length - 1; i++)
                    shapeRenderer.point((float) funcion[i][0] * scaleX, (float) funcion[i][1] * scaleY, 0);
            }
        }
        shapeRenderer.end(); // Para finalizar el renderizado
    }

    private void renderRoot() {
        if (isRootAvailable) { // Para que se renderize con la camara
            shapeRenderer.setProjectionMatrix(camera.combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(config.rootColor);
            shapeRenderer.circle((float) (raiz * scaleX), 0, 5 * camera.zoom, 50);
            shapeRenderer.setColor(Color.CYAN);
            shapeRenderer.end();
        }
    }

    private void renderAxis() {
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(config.axisColor);
        // Renderizan X e Y
        shapeRenderer.line(0, -camera.viewportHeight + camera.position.y, 0, camera.viewportHeight + camera.position.y);
        shapeRenderer.line(-camera.viewportWidth + camera.position.x, 0, camera.viewportWidth + camera.position.x, 0);
        // Renderiza la graduacion de los ejejejes
        for (double i = 0; i < camera.viewportWidth + Math.abs(camera.position.x); i += scaleX) {
            shapeRenderer.line((float) i, -1, (float) i, 1);
            shapeRenderer.line((float) -i, -1, (float) -i, 1);
        }
        for (double i = 0; i < camera.viewportHeight + Math.abs(camera.position.y); i += scaleY) {
            shapeRenderer.line(-1, (float) i, 1, (float) i);
            shapeRenderer.line(-1, (float) -i, 1, (float) -i);
        }
        shapeRenderer.end();
    }

    public Method getMetodo() {
        return metodo;
    }

    public void updateValores(double[][] valores) {
        this.valores = valores;
    }

    public void updateMulti(ArrayList funciones) {
        this.funciones = funciones;
        valores = null;
    }

    public void reloadConfig() {
        super.reloadConfig();
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                config = Const.Load();
            }
        });
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        renderAxis(); // Render the main x and y axis
        renderArray(); // Render the array with the graphics value
        renderRoot(); // Render the root (if any)
        renderGUI(); // This renders the GUI above everything
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }
}