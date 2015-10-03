package com.itc.mn.Pantallas;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.itc.mn.Cosas.Const;
import com.itc.mn.Cosas.TablaResultados;
import com.itc.mn.Cosas.Ventana;
import com.itc.mn.Metodos.Metodo;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.*;

/**
 * Esta es la clase principal para manejar las ventanas en la aplicacion.
 * Cualquier otra ventana a desplegar debera extender de esta misma para
 * ahorrar recursos, manejo de camaras especificas entre otras cosas.
 *
 * Procura redimensionar los elementos en el metodo resize
 * para que la aplicacion se muestre correctamente en el escritorio
 */
public class Pantalla implements Screen {

    protected final OrthographicCamera camera;
    protected final Stage gui_stage, camera_stage;
    protected Game game;
    protected final FitViewport viewport;
    protected VisUI visui;
    protected final ShapeRenderer shapeRenderer;
    protected boolean debugEnabled;
    protected VisTable table;
    protected double precision;
    protected TablaResultados tabla_res;
    protected volatile MenuItem tabla_iter;
    private InputMultiplexer multiplexer;
    private PopupMenu menu, m_metodos;
    private MenuItem metodos, graficador, metodos_biseccion, metodos_reglafalsa, metodos_PFijo, metodos_nrapson;
    private Ventana ventana;

    {
        // Creamos el shaperenderer
        shapeRenderer = new ShapeRenderer();
        // Creamos una camara
        camera = new OrthographicCamera(Const.WIDTH, Const.HEIGHT);
        // La definimos falsa como ortografica (el eje Y va de - a + desde la esquina inf. izquierda)
        camera.setToOrtho(false);
        // La posicionamos en 0, 0, 0. Esto quiere decir que el centro del plano cartesiano queda en el
        // Actualizamos la configuracion
        camera.update();
        // Definimos un viewport, para que el contenido se escale a la pantalla en la que corra
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        // Creamos el gui_stage, el cual albergara los botones entre otras cosas
        gui_stage = new Stage(viewport);
        // Inicializamos el camera_Stage que contendra los elementos de la gui
        camera_stage = new Stage(viewport);
        // Creamos el multiplexer para la captura de eventos
        multiplexer = new InputMultiplexer(gui_stage, camera_stage);
        // Definimos como procesador el multiplexer
        Gdx.input.setInputProcessor(multiplexer);
        // Importamos el VisUI
        visui.load();

        // Agregamos los escuchadores del gui_stage
        camera_stage.addListener(new ActorGestureListener() {
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
            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // Para eventos tactiles de android, usamos puntero, para PC usamos button
                if (button == 1)
                    menu.showMenu(gui_stage, x, y);
            }

            @Override
            public boolean longPress(Actor actor, float x, float y) {
                menu.showMenu(gui_stage, x, y);
                return true;
            }

            @Override
            public boolean handle(Event e) {
                return super.handle(e);
            }
        });
        camera_stage.addListener(new InputListener() {
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
                    camera.zoom += (camera.zoom < 1) ? 0.015f : 0;
                else
                    camera.zoom += (camera.zoom > 0.02f) ? -0.015f : 0;
                return super.scrolled(event, x, y, amount);
            }
        });
    }

    public void construyeGUI(){
        // Una tabla para gobernarlos a todos... muahahahaha
        table = new VisTable();
        table.setSize(Gdx.graphics.getWidth() * 0.95f, Gdx.graphics.getHeight());
        table.setPosition((Gdx.graphics.getWidth() - table.getWidth()) / 2f, (Gdx.graphics.getHeight() - table.getHeight()) / 2f);
        // Agregando un menu en comun
        menu = new PopupMenu();
        // Instanciamos los submenues
        m_metodos = new PopupMenu();
        creaItems();
        // Agregamos la tabla al gui_stage
        gui_stage.addActor(table);
    }

    private void creaItems(){
        // Creamos los elementos del menu
        metodos = new MenuItem("Metodos");
        metodos.setSubMenu(m_metodos);
        graficador = new MenuItem("Graficador");
        tabla_iter = new MenuItem("Tabla iteraciones");
        tabla_iter.setDisabled(true);

        // Instanciamos los elemenos del submenu metodos
        metodos_biseccion = new MenuItem("Biseccion");
        metodos_reglafalsa = new MenuItem("Regla Falsa");
        metodos_PFijo = new MenuItem("Punto Fijo");
        metodos_nrapson = new MenuItem("Newton-Raphson");

        // Asignamos eventos a cada item
        asignaEventos();
         // Agregamos los submenues al menu principal
        menu.addItem(metodos);
        menu.addItem(graficador);
        menu.addItem(tabla_iter);
        // Agregamos los elementos de los submenues
        m_metodos.addItem(metodos_biseccion);
        m_metodos.addItem(metodos_reglafalsa);
        m_metodos.addItem(metodos_PFijo);
        m_metodos.addItem(metodos_nrapson);
    }

    private void asignaEventos(){
        graficador.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                RenderScreen s = new RenderScreen(game);
                game.setScreen(s);
            }
        });
        //Para biseccion
        metodos_biseccion.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (ventana == null || !gui_stage.getActors().contains(ventana, true))
                    mostrar_biseccion();
                else
                    ventana.parpadear();
            }
        });
        // para regla falsa
        metodos_reglafalsa.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (ventana == null || !gui_stage.getActors().contains(ventana, true))
                    mostrar_rf();
                else
                    ventana.parpadear();
            }
        });
        // Para punto fijo
        metodos_PFijo.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (ventana == null || !gui_stage.getActors().contains(ventana, true))
                    mostrar_pfijo();
                else
                    ventana.parpadear();
            }
        });
        // Para Newton Raphson
        metodos_nrapson.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (ventana == null || !gui_stage.getActors().contains(ventana, true))
                    mostrar_nr();
                else
                    ventana.parpadear();
            }
        });
        tabla_iter.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!tabla_iter.isDisabled()) {
                    tabla_res.show(gui_stage);
                }
            }
        });
    }

    private void mostrar_pfijo(){
        String[][] campos = new String[][]{{"Funcion Original", "f1"},{"Funcion Despejada", "f2"}, {"Valor inicial", "vi"}, {"Error (0-100)", "ep"}};
        ventana = new Ventana("Punto Fijo", campos, game);
        ventana.asignaEvento(Metodo.Tipo.PUNTO_FIJO);
        gui_stage.addActor(ventana.fadeIn(0.3f));
    }

    private void mostrar_nr(){
        String[][] campos = new String[][]{{"Funcion Original", "fx"},{"Primer Derivada", "f'x"}, {"Valor inicial", "vi"}, {"Error (0-100)", "ep"}};
        ventana = new Ventana("Newton-Raphson", campos, game);
        ventana.asignaEvento(Metodo.Tipo.NEWTON_RAPHSON);
        gui_stage.addActor(ventana.fadeIn(0.3f));
    }

    private void mostrar_biseccion(){
        String[][] campos = new String[][]{{"Funcion", "f"}, {"Valor a", "a"}, {"Valor b", "b"}, {"Error (0-100)", "ep"}};
        ventana = new Ventana("Biseccion", campos, game);
        ventana.asignaEvento(Metodo.Tipo.BISECCION);
        gui_stage.addActor(ventana.fadeIn(0.3f));
    }

    private void mostrar_rf(){
        String[][] campos = new String[][]{{"Funcion", "f"}, {"Valor a", "a"}, {"Valor b", "b"}, {"Error (0-100)", "ep"}};
        ventana = new Ventana("Regla Falsa", campos, game);
        ventana.asignaEvento(Metodo.Tipo.REGLA_FALSA);
        gui_stage.addActor(ventana.fadeIn(0.3f));
    }

    @Override
    public void show() {
        gui_stage.setDebugAll(debugEnabled);
        camera.position.set(0, 0, 0);
    }

    /**
     * Renderiza por defecto, SIEMPRE MANDE LLAMAR renderTop al ultimo, es necesario para que la GUI este encima de todo
     * @param delta
     */
    @Override
    public void render(float delta) {
        Gdx.gl20.glClearColor(0, 0, 0, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // Aplicamos el viewport
        viewport.apply();
        // Actualizamos la camara
        camera.update();
        // Actualizamos el Stage
    }

    public void renderTop(){
        camera_stage.act();
        camera_stage.draw();
        gui_stage.act();
        gui_stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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
        gui_stage.dispose();
        visui.dispose();
    }
}
