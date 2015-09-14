package com.itc.mn.Pantallas;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.itc.mn.Cosas.Const;
import com.itc.mn.Cosas.FuncionX;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.*;

import java.util.ArrayList;

/**
 * Created by zero_ on 10/09/2015.
 */
public class RenderScreen implements Screen {

    private OrthographicCamera camera;
    private Stage stage;
    private Game game;
    private FitViewport viewport;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private float[][] valores;
    private ArrayList<float[][]> funciones;
    private Color[] colores = {Color.BLUE, Color.GREEN, Color.CYAN, Color.YELLOW,  Color.FIREBRICK, Color.ROYAL, Color.RED, Color.SALMON, Color.MAGENTA, Color.LIME, Color.TAN, Color.TEAL, Color.VIOLET};
    private String funcion;
    private float scaleX, scaleY;
    // Test
    private VisUI visui; // VisUI es una skin para Scene2D
    private VisSlider ejeX, ejeY;

    // Default, se ejecutara siempre, independientemente del constructor
    {
        // Cosas a generar por defecto
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        // Definimos el viewport
        viewport = new FitViewport(Const.WIDTH, Const.HEIGHT);
        // Creamos las cosas para renderizar
        camera = new OrthographicCamera(Const.WIDTH, Const.HEIGHT);
        camera.setToOrtho(false);
        camera.position.set(0, 0, 0);
        camera.update();
        // Definimos el Stage para entradas tactiles
        stage = new Stage();
        stage.setViewport(viewport);
        // For moving
        Gdx.input.setInputProcessor(stage);
        stage.addListener(new ActorGestureListener() {
            @Override
            public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
                super.pan(event, x, y, deltaX, deltaY);
                if (y > -230) // Zona muerta para que al interactuar con los sliders o el TextField, no haga panning
                    camera.position.set(camera.position.x - deltaX * camera.zoom, camera.position.y - deltaY * camera.zoom, 0);
            }

            @Override
            public void zoom(InputEvent event, float initialDistance, float distance) {
                super.zoom(event, initialDistance, distance);
                float diff = initialDistance - distance;
                if (diff > 0)
                    camera.zoom += (camera.zoom < 1) ? camera.zoom * 0.01f : 0;
                else
                    camera.zoom -= (camera.zoom > 0.02f) ? camera.zoom * 0.01f : 0;

            }

            @Override
            public boolean handle(Event e) {
                return super.handle(e);
            }
        });
        stage.addListener(new InputListener() {
            @Override
            public boolean keyTyped(InputEvent event, char character) {
                // Esto se encarga de controlar el zoom con el teclado
                // El Zoom de la camara va de 0f a 1f, siendo 0f lo mas cercano, pero
                // causa problemas al renderizar, por eso se limita a 0.02f
                if (event.getKeyCode() == Input.Keys.DOWN) { // Zoom menos
                    camera.zoom += (camera.zoom < 1) ? 0.01f : 0;
                    return true;
                } else if (event.getKeyCode() == Input.Keys.UP) { // Zoom mas
                    camera.zoom -= (camera.zoom > 0.02f) ? 0.01f : 0;
                    return true;
                }
                return super.keyTyped(event, character);
            }

            @Override
            public boolean scrolled(InputEvent event, float x, float y, int amount) {
                if (amount > 0)
                    camera.zoom += (camera.zoom < 1) ? 0.01f : 0;
                else
                    camera.zoom += (camera.zoom > 0.02f) ? -0.01f : 0;
                return super.scrolled(event, x, y, amount);
            }
        });

        scaleX = 10;
        scaleY = 10;

        // Construimos nuestra GUI
        construyeGUI();
    }

    /**
     * Recibe el arrreglo de valores de una funcion y lo grafica
     * @param game Referencia a Game para manejo de pantallas
     * @param valores Valores de la funcion
     */
    public RenderScreen(Game game, float[][] valores){
        // Solo para tener una referencia al manejador de pantallas
        this.game = game;
        this.valores = valores;
    }

    /**
     * Recibe un ArrayList de varias funciones, para graficacion multiple
     * @param game Referencia a Game para manejo de pantallas
     * @param funciones ArrayList con arreglos de valores para cada funcion
     */
    public RenderScreen(Game game, ArrayList<float[][]> funciones){
        // Solo para tener una referencia al manejador de pantallas
        this.game = game;
        this.funciones = funciones;
    }

    /**
     * Constructor defecto, solo hace la instancia con una grafica default de x
     * @param game Instancia de Game que controla las pantallas
     */
    public RenderScreen(Game game){
        this.game = game;
        valores = new FuncionX("x").obtenerRango(-10, 10, 0.001f);
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
                shapeRenderer.point((valores[i][0]*scaleX), (valores[i][1]*scaleY), 0);
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
                    shapeRenderer.point(funcion[i][0]*scaleX, funcion[i][1]*scaleY, 0);
            }
        }
        // Para finalizar el renderizado
        shapeRenderer.end();
    }

    private void renderEjes(){
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 1, 1, 1);
        // Renderizan X e Y
        shapeRenderer.line(0, -camera.viewportHeight + camera.position.y , 0, camera.viewportHeight + camera.position.y);
        shapeRenderer.line(-camera.viewportWidth + camera.position.x, 0, camera.viewportWidth + camera.position.x, 0);
        // Renderiza la graduacion de los ejejejes
        for (float i = 0; i < camera.viewportWidth + Math.abs(camera.position.x); i+=scaleX){
            shapeRenderer.line(i, -1, i, 1);
            shapeRenderer.line(-i, -1, -i, 1);
        }
        for (float i = 0; i < camera.viewportHeight + Math.abs(camera.position.y); i+=scaleY){
            shapeRenderer.line(-1, i, 1, i);
            shapeRenderer.line(-1, -i, 1, -i);
        }
        shapeRenderer.end();
    }

    private void construyeGUI(){
        // Inicializamos VisUI
        visui.load();
        // Una tabla para gobernarlos a todos... muahahahaha
        VisTable table = new VisTable();
        if (Gdx.app.getType().equals(Application.ApplicationType.Android)) {
            table.setSize(Gdx.graphics.getWidth()* 0.6f, Gdx.graphics.getHeight());
            table.setPosition((-Gdx.graphics.getWidth() / 2f) * .7f, (-Gdx.graphics.getHeight() / 2f) * .7f);
        }
        else {
            table.setSize(camera.viewportWidth * 0.95f, camera.viewportHeight);
            table.setPosition(-camera.viewportWidth / 2f, -camera.viewportHeight / 2f);
        }
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
        // Agregamos la tabla al stage
        stage.addActor(table);
        // stage.setDebugAll(true);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // Aplicamos el viewport
        viewport.apply();
        // Actualizamos la camara
        camera.update();
        // Batch
        batch.setProjectionMatrix(camera.combined);
        // Actualizamos el Stage
        stage.act();
        stage.draw();
        // Renderizamos los ejes X e Y
        renderEjes();
        // Renderizamos con el shapeRenderer
        renderArreglo();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.zoom = 0.3f;
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

    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();
        visui.dispose();
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
            else{

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