package com.itc.mn.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.itc.mn.GUI.GlobalMenu;
import com.itc.mn.UI.Modules.WelcomeModule;

public class MainScreen implements Screen {

    protected final Stage stage;
    private final GlobalMenu menuBar;
    private final Table root;
    private Actor module = null;

    public MainScreen(){
        stage = new Stage(new ScreenViewport());
        stage.setDebugAll(true);
        root = new Table();
        root.setFillParent(true);
        stage.addActor(root);
        Gdx.input.setInputProcessor(stage);
        menuBar = new GlobalMenu(this);
        root.add(menuBar.getTable()).fillX().expand().top().pad(0).row();
        root.add(new WelcomeModule()).fill().expand().center();
    }

    public Table getRoot() {
        return root;
    }

    public void showModule(Actor actor){
        module = actor;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
