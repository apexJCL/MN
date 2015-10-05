package com.itc.mn.Pantallas;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.itc.mn.Cosas.FuncionX;
import com.itc.mn.Cosas.TablaResultados;
import com.itc.mn.Metodos.Metodo;
import com.kotcrab.vis.ui.widget.*;

import java.util.ArrayList;

/**
 * Created by zero_ on 10/09/2015.
 */
public class RenderScreen extends Pantalla {

    private double raiz;
    private double[][] valores;
    private float scaleX, scaleY;
    private volatile boolean isInputVisible, isRootAvailable;
    private ArrayList<float[][]> funciones;
    private Color[] colores = {Color.BLUE, Color.GREEN, Color.CYAN, Color.YELLOW,  Color.FIREBRICK, Color.ROYAL, Color.RED, Color.SALMON, Color.MAGENTA, Color.LIME, Color.TAN, Color.TEAL, Color.VIOLET};
    private VisSlider ejeX, ejeY;
    private Metodo metodo;

    {
        // Definimos la escala defecto de los ejes
        scaleX = 10;
        scaleY = 10;
        gui_stage.setDebugAll(true);
        isRootAvailable = false;
    }

    /**
     * Recibe el arrreglo de valores de una funcion y lo grafica
     * @param game Referencia a Game para manejo de pantallas
     * @param valores Valores de la funcion
     */
    public RenderScreen(Game game, double[][] valores, boolean isInputVisible){
        super();
        // Solo para tener una referencia al manejador de pantallas
        this.game = game;
        this.valores = valores;
        // Este valor es por si se desea mostrar para "graficar" al vuelo o solo se quieren ver resultados
        this.isInputVisible = isInputVisible;
        // Construimos nuestra GUI
        construyeGUI();
        tabla_res = new TablaResultados(metodo);
    }

    public RenderScreen(Game game, double[][] valores, double raiz){
        super();
        this.game = game;
        this.valores = valores;
        this.raiz = raiz;
        isRootAvailable = true;
        // Construimos nuestra GUI
        construyeGUI();
    }

    /**
     * Crea una instancia de RenderScreen con el metodo asignado, usandolo para obtener la funcion y graficar
     * respectivamente
     * @param game Instancia Game para cambiar pantallas
     * @param metodo Objeto metodo
     */
    public RenderScreen(Game game, Metodo metodo){
        super();
        this.metodo = metodo;
        this.game = game;
        this.valores = metodo.obtenerRango();
        this.raiz = metodo.getRaiz();
        isRootAvailable = true;
        // Construimos nuestra GUI
        construyeGUI();
        tabla_res = new TablaResultados(metodo);
        tabla_iter.setDisabled(false);
    }

    /**
     * Recibe un ArrayList de varias funciones, para graficacion multiple
     * @param game Referencia a Game para manejo de pantallas
     * @param funciones ArrayList con arreglos de valores para cada funcion
     */
    public RenderScreen(Game game, ArrayList<float[][]> funciones, boolean isInputVisible){
        // Solo para tener una referencia al manejador de pantallas
        this.game = game;
        this.funciones = funciones;
        // Este valor es por si se desea mostrar para "graficar" al vuelo o solo se quieren ver resultados
        this.isInputVisible = isInputVisible;
        // Construimos nuestra GUI
        construyeGUI();
    }

    /**
     * Constructor defecto, solo hace la instancia con una grafica default de x
     * @param game Instancia de Game que controla las pantallas
     */
    public RenderScreen(Game game){
        this.game = game;
        valores = new FuncionX("sin(x*cos((pi^x)*tan(e^pi*x)))").obtenerRango(-10, 10, 0.001f);
        isInputVisible = true;
        // Construimos nuestra GUI
        construyeGUI();
        camera.zoom = 0.5f;
    }

    public void setInputVisible(boolean flag){
        isInputVisible = flag;
    }

    private void renderArreglo(){
        // Para que se renderize con la camara
        shapeRenderer.setProjectionMatrix(camera.combined);
        // Para comenzar el renderizado
        shapeRenderer.begin(ShapeRenderer.ShapeType.Point);
        // Para renderizar solo cuando tenemos el arreglo sencillo de valores
        if(valores != null) {
            // Obviamente define el color
            shapeRenderer.setColor(Color.CYAN);
            // Procesando arreglo
            for (int i = 0; i < valores.length; i++)
                shapeRenderer.point((float)(valores[i][0] * scaleX), (float)(valores[i][1] * scaleY), 0);
        }
        else{
            int counter = 0;
            for (float[][] funcion : funciones) {
                if (counter < funciones.size())
                    counter++;
                else
                    counter = 0;
                shapeRenderer.setColor(colores[counter]);
                for (int i = 0; i < funcion.length - 1; i++)
                    shapeRenderer.point((float)(funcion[i][0]*scaleX), (float)(funcion[i][1]*scaleY), 0);
            }
        }
        // Para finalizar el renderizado
        shapeRenderer.end();
    }

    private void renderRaiz(){
        // Para que se renderize con la camara
        if(isRootAvailable) {
            shapeRenderer.setProjectionMatrix(camera.combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(0, 255, 0, 0.7f);
            shapeRenderer.circle((float) (raiz * scaleX), 0, 5 * camera.zoom, 50);
            shapeRenderer.setColor(Color.CYAN);
            shapeRenderer.end();
        }

    }

    private void renderEjes(){
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 1, 1, 1);
        // Renderizan X e Y
        shapeRenderer.line(0, -camera.viewportHeight + camera.position.y , 0, camera.viewportHeight + camera.position.y);
        shapeRenderer.line(-camera.viewportWidth + camera.position.x, 0, camera.viewportWidth + camera.position.x, 0);
        // Renderiza la graduacion de los ejejejes
        for (double i = 0; i < camera.viewportWidth + Math.abs(camera.position.x); i+=scaleX){
            shapeRenderer.line((float)i, -1, (float)i, 1);
            shapeRenderer.line((float)-i, -1, (float)-i, 1);
        }
        for (double i = 0; i < camera.viewportHeight + Math.abs(camera.position.y); i+=scaleY){
            shapeRenderer.line(-1, (float)i, 1, (float)i);
            shapeRenderer.line(-1, (float)-i, 1, (float)-i);
        }
        shapeRenderer.end();
    }

    @Override
    public void construyeGUI(){
        super.construyeGUI();
        System.out.println(isInputVisible);
        if(isInputVisible) {
            // Un panel de entrada para re-evaluar
            VisLabel funcion = new VisLabel("Funcion: ");
            VisTextField entrada = new VisTextField("f(x) = ");
            entrada.pack();
            // Le agregamos un nombre para que pueda ser identificado
            entrada.setName("entrada");
            entrada.addListener(new UIListener(entrada));
            // Los agregamos a la tabla
            table.add(funcion).bottom().left().pad(4);
            table.add(entrada).expand().bottom().left().pad(4);
        }
        // Para ajustar la grafica
        ejeX = new VisSlider(0.1f, 50, 0.001f, false);
        ejeX.setValue(scaleX);
        // Agregamos un escuchador de eventos
        ejeX.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                scaleX = ((VisSlider) actor).getValue();
            }
        });
        ejeY = new VisSlider(0.1f, 50, 0.001f, false);
        ejeY.setValue(scaleY);
        // Agregamos un escuchador de eventos
        ejeY.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                scaleY = ((VisSlider) actor).getValue();
            }
        });
        // Los agregamos a la tabla
        VisLabel ajuste = new VisLabel("Ajuste ejes");
        table.add(ajuste).bottom().right().pad(4f);
        table.add(ejeX).expandY().bottom().left().pad(5f);
        table.add(ejeY).expandY().bottom().left().pad(5f);
        // Para reestablecer escala
        VisTextButton restablece = new VisTextButton("Reinicia ejes");
        restablece.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        ejeX.setValue(10);
                        ejeY.setValue(10);
                    }
                });
                return true;
            }
        });
        // Agregamos el boton
        table.add(restablece).right().expandY().bottom().pad(5f);
        // Agregamos la tabla al gui_stage
        gui_stage.addActor(table);
        gui_stage.setDebugAll(true);
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

        public UIListener(Actor actor){
            this.actor = actor;
        }

        @Override
        public void clicked(InputEvent event, float x, float y) {
            // Android es Java 6, asi que no hay switch de Strings :(
            if(actor.getName().equals("entrada")){ // Aqui borramos el texto por defecto del campo
                ((VisTextField)actor).setText("");
                if(Gdx.app.getType().equals(Application.ApplicationType.Android))
                    Gdx.input.getTextInput(new MyTextListener(actor), "Funcion", "", "f(x) = ");
            }
            super.clicked(event, x, y);
        }

        @Override
        public boolean keyTyped(InputEvent event, char character) {
            if(actor.getName().equals("entrada"))
                if(event.getKeyCode() == Input.Keys.ENTER){
                    final FuncionX fx = new FuncionX(((VisTextField)actor).getText());
                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            valores = fx.obtenerRango(-10, 10, 0.001f);
                        }
                    });
                    ejeX.setValue(10);
                    ejeY.setValue(10);
                }
            return super.keyTyped(event, character);
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            super.touchUp(event, x, y, pointer, button);
        }

    }

    private class MyTextListener implements Input.TextInputListener{

        private Actor actor;

        public MyTextListener(Actor actor){
            this.actor = actor;
        }

        @Override
        public void input(String text) {
            ((VisTextField)actor).setText(text);
            final FuncionX fx = new FuncionX(text);
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    valores = fx.obtenerRango(-10, 10, 0.001f);
                }
            });
            ejeX.setValue(10);
            ejeY.setValue(10);
        }

        @Override
        public void canceled() {

        }
    }

}