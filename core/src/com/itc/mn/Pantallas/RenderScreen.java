package com.itc.mn.Pantallas;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.itc.mn.Cosas.FuncionX;
import com.itc.mn.Cosas.Results;
import com.itc.mn.Metodos.Metodo;
import com.kotcrab.vis.ui.widget.VisTextField;

import java.util.ArrayList;

/**
 * This screen manages the rendering of functions, independly from GUI
 */
public class RenderScreen extends Pantalla {

    private double raiz;
    private double[][] valores;
    private volatile boolean isRootAvailable;
    private ArrayList<float[][]> funciones;
    private Color[] colores = {Color.BLUE, Color.GREEN, Color.CYAN, Color.YELLOW, Color.FIREBRICK, Color.ROYAL, Color.RED, Color.SALMON, Color.MAGENTA, Color.LIME, Color.TAN, Color.TEAL, Color.VIOLET};
    private Metodo metodo;

    {
        gui_stage.setDebugAll(true);
        isRootAvailable = false;
        gui_stage.getInputField().addListener(new UIListener(gui_stage.getInputField()));
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
    public RenderScreen(Game game, Metodo metodo, boolean inputEnabled) {
        super(game);
        this.metodo = metodo;
        this.valores = metodo.obtenerRango();
        this.raiz = metodo.getRaiz();
        isRootAvailable = true;
        gui_stage.createTablaRes(metodo);
        gui_stage.enableIterTable(true);
        gui_stage.setInputVisible(inputEnabled);
    }

    public RenderScreen(Game game, Results results) {
        super(game);
        this.valores = new FuncionX(results.getFuncion()).obtenerRango(-10, 10);
        this.raiz = results.getRaiz();
        isRootAvailable = true;
        gui_stage.createTableRes(results);
        gui_stage.enableIterTable(true);
        gui_stage.setInputVisible(false);
    }

    /**
     * Recibe un ArrayList de varias funciones, para graficacion multiple
     *
     * @param game      Referencia a Game para manejo de pantallas
     * @param funciones ArrayList con arreglos de valores para cada funcion
     */
    public RenderScreen(Game game, ArrayList<float[][]> funciones, boolean isInputVisible) {
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

    private void renderArreglo() {
        // Para que se renderize con la camara
        shapeRenderer.setProjectionMatrix(camera.combined);
        // Para comenzar el renderizado
        shapeRenderer.begin(ShapeRenderer.ShapeType.Point);
        // Para renderizar solo cuando tenemos el arreglo sencillo de valores
        if (valores != null) {
            // Obviamente define el color
            shapeRenderer.setColor(Color.CYAN);
            // Procesando arreglo
            for (double[] valore : valores)
                shapeRenderer.point((float) (valore[0] * scaleX), (float) (valore[1] * scaleY), 0);
        } else {
            int counter = 0;
            for (float[][] funcion : funciones) {
                if (counter < funciones.size())
                    counter++;
                else
                    counter = 0;
                shapeRenderer.setColor(colores[counter]);
                for (int i = 0; i < funcion.length - 1; i++)
                    shapeRenderer.point(funcion[i][0] * scaleX, funcion[i][1] * scaleY, 0);
            }
        }
        shapeRenderer.end(); // Para finalizar el renderizado
    }

    private void renderRaiz() {
        if (isRootAvailable) { // Para que se renderize con la camara
            shapeRenderer.setProjectionMatrix(camera.combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(0, 255, 0, 0.7f);
            shapeRenderer.circle((float) (raiz * scaleX), 0, 5 * camera.zoom, 50);
            shapeRenderer.setColor(Color.CYAN);
            shapeRenderer.end();
        }
    }

    private void renderEjes() {
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 1, 1, 1);
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

    public Metodo getMetodo() {
        return metodo;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        // Renderizamos los ejes X e Y
        renderEjes();
        // Renderizamos con el shapeRenderer
        renderArreglo();
        // Renderizamos las raiz
        renderRaiz();
        // NECESARIO PARA QUE LA GUI ESTE SOBRE DE TODO
        renderTop();
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

    private class UIListener extends ClickListener {

        private final Actor actor;

        public UIListener(Actor actor) {
            this.actor = actor;
        }

        @Override
        public void clicked(InputEvent event, float x, float y) {
            // Android es Java 6, asi que no hay switch de Strings :(
            if (actor.getName().equals("entrada")) { // Aqui borramos el texto por defecto del campo
                ((VisTextField) actor).setText("");
                if (Gdx.app.getType().equals(Application.ApplicationType.Android))
                    Gdx.input.getTextInput(new MyTextListener(actor), "Funcion", "", "f(x) = ");
            }
            super.clicked(event, x, y);
        }

        @Override
        public boolean keyTyped(InputEvent event, char character) {
            if (actor.getName().equals("entrada"))
                if (event.getKeyCode() == Input.Keys.ENTER) {
                    final FuncionX fx = new FuncionX(((VisTextField) actor).getText());
                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            valores = fx.obtenerRango(-10, 10, 0.001f);
                        }
                    });
                    gui_stage.getEjeX().setValue(10);
                    gui_stage.getEjeX().setValue(10);
                }
            return super.keyTyped(event, character);
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            super.touchUp(event, x, y, pointer, button);
        }
    }

    private class MyTextListener implements Input.TextInputListener {

        private Actor actor;

        public MyTextListener(Actor actor) {
            this.actor = actor;
        }

        @Override
        public void input(String text) {
            ((VisTextField) actor).setText(text);
            final FuncionX fx = new FuncionX(text);
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    valores = fx.obtenerRango(-10, 10, 0.001f);
                }
            });
            gui_stage.getEjeX().setValue(10);
            gui_stage.getEjeX().setValue(10);
        }

        @Override
        public void canceled() {

        }
    }
}