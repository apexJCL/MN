package com.itc.mn.Pantallas;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.itc.mn.Cosas.Const;
import com.itc.mn.Cosas.SplashIcon;

/**
 * Created by zero_ on 16/10/2015.
 */
public class SplashScreen implements Screen {

    private Const config = new Json().fromJson(Const.class, Gdx.app.getPreferences(Const.pref_name).getString(Const.id));
    private OrthographicCamera camera;
    private FitViewport viewport;
    private RenderScreen main;
    private SplashIcon icon;
    private Sprite sprite;
    private Stage stage;
    private Game game;

    public SplashScreen(Game game){
        this.game = game;
        main = new RenderScreen(game);
        camera = new OrthographicCamera(16,10);
        camera.setToOrtho(false);
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage(viewport);
        icon = new SplashIcon(game, main);
    }

    @Override
    public void show() {
        stage.addActor(icon.introAnimation());
    }

    @Override
    public void render(float delta) {
        Gdx.gl20.glClearColor(config.backgroundColor[0], config.backgroundColor[1],config.backgroundColor[2], config.backgroundColor[3]);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewport.apply(true);
        camera.update();
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

    }
}
