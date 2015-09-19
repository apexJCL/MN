package com.itc.mn.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.itc.mn.Cosas.Const;
import com.kotcrab.vis.ui.VisUI;

/**
 * Created by zero_ on 18/09/2015.
 */
public class Pantalla implements Screen {

    protected final OrthographicCamera camera;
    protected final Stage stage;
    protected FitViewport viewport;
    protected VisUI visui;
    protected ShapeRenderer shapeRenderer;


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
        // Creamos el stage, el cual albergara los botones entre otras cosas
        stage = new Stage(viewport);
        // Definimos que el InputProcessor sera el stage
        Gdx.input.setInputProcessor(stage);
        // Importamos el VisUI
        visui.load();
    }

    public Pantalla(boolean debug){
        if(debug)// Modo debug
            stage.setDebugAll(true);
    }

    @Override
    public void show() {
        camera.position.set(0, 0, 0);
    }

    @Override
    public void render(float delta) {
        Gdx.gl20.glClearColor(0, 0, 0, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // Aplicamos el viewport
        viewport.apply();
        // Actualizamos la camara
        camera.update();
        // Actualizamos el Stage
        stage.act();
        stage.draw();
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
        stage.dispose();
        visui.dispose();
    }
}
