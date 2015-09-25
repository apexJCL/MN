package com.itc.mn.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.itc.mn.Cosas.Const;
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
    protected final Stage stage;
    protected FitViewport viewport;
    protected VisUI visui;
    protected ShapeRenderer shapeRenderer;
    protected boolean debugEnabled;
    protected VisTable table;
    private MenuBar menu;
    protected double precision;
    private Menu metodos, herramientas;
    private MenuItem h_config;

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

    public void construyeGUI(){
        // Una tabla para gobernarlos a todos... muahahahaha
        table = new VisTable();
        table.setSize(Gdx.graphics.getWidth() * 0.95f, Gdx.graphics.getHeight());
        table.setPosition((Gdx.graphics.getWidth() - table.getWidth()) / 2f, (Gdx.graphics.getHeight() - table.getHeight()) / 2f);
        // Agregando un menu en comun
        menu = new MenuBar();
        // Creando los menues principales
        metodos = new Menu("Metodos");
        herramientas = new Menu("Herramientas");
        // Agregando los menues a la barra
        menu.addMenu(metodos);
        menu.addMenu(herramientas);
        // Creamos los submenues
        h_config = new MenuItem("Configuracion");
        h_config.setShortcut("Ctrl+P");
        // Agregamos las cosas
        herramientas.add(h_config);
        // Agregamos el menu a la tabla
        table.add(menu.getTable()).fillX().expandX().colspan(7).row();
        // Agregamos la tabla al stage
        stage.addActor(table);
        menu.getTable().setDebug(true);
    }

    @Override
    public void show() {
        stage.setDebugAll(debugEnabled);
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
