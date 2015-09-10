package com.itc.mn.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.itc.mn.Misc.Const;

/**
 * Created by zero_ on 10/09/2015.
 */
public class ExampleScreen implements Screen {

    private OrthographicCamera camera;
    private Stage stage;
    private Game game;
    private FitViewport viewport;

    public ExampleScreen(Game game){
        // Sólo para tener una referencia al manejador de pantallas
        this.game = game;
        // Creamos las cosas para renderizar
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Const.WIDTH, Const.HEIGHT);
        // Definimos el viewport
        viewport = new FitViewport(Const.WIDTH, Const.HEIGHT);
        // Definimos el Stage para entradas táctiles
        stage = new Stage(viewport);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // Actualizamos la cámara
        camera.update();
        // Actualizamos el Stage
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

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

    }
}
