package com.itc.mn.Pantallas;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.itc.mn.Cosas.Const;
import com.itc.mn.GUI.FrontEnd;
import com.itc.mn.GUI.GraphingCamera;
import com.kotcrab.vis.ui.VisUI;

/**
 * This class just exists, by now...
 */
public class Pantalla implements Screen {

    public float scaleX = 10f;
    public float scaleY = 10f;
    protected Game game;
    protected final OrthographicCamera camera;
    protected final GraphingCamera camera_stage;
    protected final FrontEnd gui_stage;
    protected final FitViewport viewport;
    protected final ShapeRenderer shapeRenderer;
    protected boolean debugEnabled;
    private Json json = new Json();
    private Const config = json.fromJson(Const.class, Gdx.app.getPreferences(Const.pref_name).getString(Const.id));

    public Pantalla(Game game) {
        this.game = game;
        shapeRenderer = new ShapeRenderer();// We instantiate the shapeRenderer so we can draw on screen with it
        camera = new OrthographicCamera(Const.WIDTH, Const.HEIGHT);// We create the default camera
        camera.setToOrtho(false); //Ortho False = Y goes down-up
        camera.update();//Update the camera settings
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());//Define a viewport so everything fits on any screen nicely
        gui_stage = new FrontEnd(viewport, game, this, true);// We build up a default GUI, the graphing screen, that's why we enable the input
        camera_stage = new GraphingCamera(viewport, camera, gui_stage);// Create the cameraStage that will hold the visual of the graphics
        InputMultiplexer multiplexer = new InputMultiplexer(gui_stage, camera_stage);// Multiplexing inputs so camera pan doesn't affect GUI movements
        Gdx.input.setInputProcessor(multiplexer);// Set multiplexer as default Input Processor
        VisUI.load();// Import VisUI so it loads the skin for the UI
    }

    @Override
    public void show() {
        gui_stage.setDebugAll(debugEnabled);
        camera.position.set(0, 0, 0);
    }

    /**
     * Renderiza por defecto, SIEMPRE MANDE LLAMAR renderGUI al ultimo, es necesario para que la GUI este encima de todos
     * @param delta tiempo de renderizado por defecto
     */
    @Override
    public void render(float delta) {
        Gdx.gl20.glClearColor(config.backgroundColor[0],config.backgroundColor[1],config.backgroundColor[2], config.backgroundColor[3]);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewport.apply();
        camera.update();
    }

    /**
     * It renders the GUI above everything, so it looks nice
     */
    public void renderGUI(){
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

    public void reloadConfig() {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                config = json.fromJson(Const.class, Gdx.app.getPreferences(Const.pref_name).getString(Const.id));
            }
        });
    }
}