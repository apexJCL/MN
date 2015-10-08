package com.itc.mn.Pantallas;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.itc.mn.Cosas.Const;
import com.itc.mn.GUI.FrontEnd;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.MenuItem;
import com.kotcrab.vis.ui.widget.VisTable;

/**
 * Esta es la clase principal para manejar las ventanas en la aplicacion.
 * Cualquier otra ventanaValores a desplegar debera extender de esta misma para
 * ahorrar recursos, manejo de camaras especificas entre otras cosas.
 *
 * Procura redimensionar los elementos en el metodo resize
 * para que la aplicacion se muestre correctamente en el escritorio
 */
public class Pantalla implements Screen {

    protected final OrthographicCamera camera;
    protected final Stage camera_stage;
    protected final FrontEnd gui_stage;
    protected final FitViewport viewport;
    protected final ShapeRenderer shapeRenderer;
    protected boolean debugEnabled;
    protected double precision, scaleX, scaleY;
    protected volatile MenuItem tabla_iter;
    protected Game game;
    protected VisTable table;

    {
        // Creamos el shaperenderer
        shapeRenderer = new ShapeRenderer();
        // Creamos una camara
        camera = new OrthographicCamera(Const.WIDTH, Const.HEIGHT);
        // La definimos falsa como ortografica (el eje Y va de - a + desde la esquina inf. izquierda)
        camera.setToOrtho(false);
        // Actualizamos la configuracion
        camera.update();
        // Definimos un viewport, para que el contenido se escale a la pantalla en la que corra
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        // Creamos el gui_stage, el cual albergara los botones entre otras cosas
        gui_stage = new FrontEnd(viewport, game);
        // Inicializamos el camera_Stage que contendra los elementos de la gui
        camera_stage = new Stage(viewport);
        // Creamos el multiplexer para la captura de eventos
        InputMultiplexer multiplexer = new InputMultiplexer(gui_stage, camera_stage);
        // Definimos como procesador el multiplexer
        Gdx.input.setInputProcessor(multiplexer);
        // Importamos el VisUI
        VisUI.load();
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
                    gui_stage.getMenu().showMenu(gui_stage, x, y);
            }

            @Override
            public boolean longPress(Actor actor, float x, float y) {
                gui_stage.getMenu().showMenu(gui_stage, x, y);
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

    @Override
    public void show() {
        gui_stage.setDebugAll(debugEnabled);
        camera.position.set(0, 0, 0);
    }

    /**
     * Renderiza por defecto, SIEMPRE MANDE LLAMAR renderTop al ultimo, es necesario para que la GUI este encima de todos
     * @param delta tiempo de renderizado por defecto
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
        camera_stage.dispose();
        shapeRenderer.dispose();
        VisUI.dispose();
    }
}