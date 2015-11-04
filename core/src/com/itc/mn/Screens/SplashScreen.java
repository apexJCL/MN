package com.itc.mn.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.itc.mn.Things.Const;
import com.itc.mn.Things.SplashIcon;

/**
 * A simple splashscreen
 */
public class SplashScreen implements Screen {

    private Const config = Const.Load();
    private OrthographicCamera camera;
    private FitViewport viewport;
    private RenderScreen main;
    private SplashIcon icon;
    private Stage stage;
    private float d = 0;
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
        if (d <= 0.8)
            Gdx.gl20.glClearColor(d / 1f, d / 1f, d / 1f, 1);
        else
            Gdx.gl20.glClearColor(1f - (config.backgroundColor[0] * (d - 1f)), 1f - (config.backgroundColor[1] * (d - 1)), 1f - (config.backgroundColor[2] * (d - 1)), config.backgroundColor[3]);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewport.apply(true);
        camera.update();
        stage.act();
        stage.draw();
        d += delta;
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
