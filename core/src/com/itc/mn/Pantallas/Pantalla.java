package com.itc.mn.Pantallas;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.itc.mn.Cosas.Const;
import com.itc.mn.GUI.FrontEnd;
import com.itc.mn.GUI.GraphingCamera;
import com.kotcrab.vis.ui.VisUI;

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
    protected final GraphingCamera camera_stage;
    protected final FrontEnd gui_stage;
    protected final FitViewport viewport;
    protected final ShapeRenderer shapeRenderer;
    protected boolean debugEnabled;
    protected double precision, scaleX, scaleY;
    protected Game game;

    public Pantalla(Game game) {
        this.game = game;
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
        camera_stage = new GraphingCamera(viewport, camera, gui_stage);
        // Creamos el multiplexer para la captura de eventos
        InputMultiplexer multiplexer = new InputMultiplexer(gui_stage, camera_stage);
        // Definimos como procesador el multiplexer
        Gdx.input.setInputProcessor(multiplexer);
        // Importamos el VisUI
        VisUI.load();
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
        viewport.apply();
        camera.update();
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