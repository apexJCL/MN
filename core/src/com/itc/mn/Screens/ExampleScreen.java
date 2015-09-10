package com.itc.mn.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;

    public ExampleScreen(Game game){
        // Sólo para tener una referencia al manejador de pantallas
        this.game = game;
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        // Creamos las cosas para renderizar
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Const.WIDTH, Const.HEIGHT);
        camera.position.set(-500, -2500, 0);
        camera.update();
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
        // Renderizamos con el shapeRenderer
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.circle(100, 100, 100);
        shapeRenderer.end();
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

    }
}
