package com.itc.mn.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.itc.mn.GUI.GlobalMenu;
import com.itc.mn.Things.Const;
import com.itc.mn.Things.FuncionX;
import com.itc.mn.UI.Modules.RenderModule;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.tabbedpane.Tab;
import com.kotcrab.vis.ui.widget.tabbedpane.TabbedPane;
import com.kotcrab.vis.ui.widget.tabbedpane.TabbedPaneAdapter;

import java.util.ArrayList;

public class MainScreen implements Screen{

    protected Stage stage;
    private GlobalMenu menuBar;
    private TabbedPane tabbedPane;
    private Table root, container;
    private double[][] renderValues;
    private Const config = Const.Load();
    private boolean render = false;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private float scaleX, scaleY;
    private OrthographicCamera camera;
    private ArrayList<double[][]> funciones;
    private Color[] colores = {Color.BLUE, Color.GREEN, Color.CYAN, Color.YELLOW, Color.FIREBRICK, Color.ROYAL, Color.RED, Color.SALMON, Color.MAGENTA, Color.LIME, Color.TAN, Color.TEAL, Color.VIOLET};
    private boolean isRootAvailable = false;
    private float raiz = 0;
    private float xoffset, yoffset;

    {
        scaleX = scaleY = 10;
        xoffset = yoffset = 0;
    }

    public MainScreen(){
        VisUI.load(VisUI.SkinScale.X1);
        instantiateThings();
        addGUI();
        // Test
        renderValues = new FuncionX("x^2").obtenerRango(-30, 30);
        setStageProperties();
    }

    private void instantiateThings(){
        stage = new Stage(new ScreenViewport());
        System.out.println(stage.getWidth());
        System.out.println(stage.getHeight());
        camera = (OrthographicCamera)stage.getCamera();
        //stage.setDebugAll(true);
        root = new Table();
        // This table with hold the menu and the container for the modules
        root.setFillParent(true);
        container = new Table();
        tabbedPane = new TabbedPane();
        setupTabs();
        menuBar = new GlobalMenu(this);
    }

    private void addGUI() {
        stage.addActor(root);
        Gdx.input.setInputProcessor(stage);
        root.add(menuBar.getTable()).fillX().expandX().top().pad(0).row();
        root.add(tabbedPane.getTable()).left().row();
        root.add(container).fill().expand().row();
    }

    private void setupTabs(){
        tabbedPane.addListener(new TabbedPaneAdapter() {

            @Override
            public void switchedTab(Tab tab) {
                Table content = tab.getContentTable();
                container.clearChildren();
                container.add(content).center().expand().fill();
                if(content instanceof RenderModule.CustomTable)
                    setRenderStatus(true);
                else
                    setRenderStatus(false);
            }
        });
    }

    private void setStageProperties(){
        stage.addListener(new ActorGestureListener() {
            @Override
            public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
                if (render) {
                    for (double[] value : renderValues) {
                        value[0] += deltaX * 0.1f;
                        value[1] += deltaY * 0.1f;
                    }
                    xoffset += deltaX;
                    yoffset += deltaY;
                }
            }

            @Override
            public void zoom(InputEvent event, float initialDistance, float distance) {
                System.out.println("Zoom!");
            }
        });
    }

    public boolean getRenderStatus() { return render; }
    
    public void setRenderStatus(final boolean status){
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                render = status;
            }
        });
    }

    public TabbedPane getTabbedPane(){ return tabbedPane; }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(render) {
            renderAxis();
            renderArray();
            renderRoot();
        }
        renderGUI(delta);
    }

    private void renderGUI(float delta){
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

    public Stage getStage() {
        return stage;
    }

    private void renderArray() {
        // Para que se renderize con la camara
        shapeRenderer.setProjectionMatrix(stage.getCamera().combined);
        // Para comenzar el renderizado
        shapeRenderer.begin(ShapeRenderer.ShapeType.Point);
        // Para renderizar solo cuando tenemos el arreglo sencillo de valores
        if (renderValues != null) {
            // Obviamente define el color
            shapeRenderer.setColor(config.singleGraphic);
            // Procesando arreglo
            for (double[] valor : renderValues)
                shapeRenderer.point((float) (valor[0] * scaleX) + stage.getWidth()/2f, (float) (valor[1] * scaleY) + stage.getHeight()/2f, 0);
        } else {
//            int counter = 0;
//            for (double[][] funcion : funciones) {
//                if (counter < funciones.size())
//                    counter++;
//                else
//                    counter = 0;
//                shapeRenderer.setColor(colores[counter]);
//                for (int i = 0; i < funcion.length - 1; i++)
//                    shapeRenderer.point((float) funcion[i][0] * scaleX, (float) funcion[i][1] * scaleY, 0);
//            }
        }
        shapeRenderer.end(); // Para finalizar el renderizado
    }

    private void renderRoot() {
        if (isRootAvailable) { // Para que se renderize con la camara
            shapeRenderer.setProjectionMatrix(camera.combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(config.rootColor);
            shapeRenderer.circle((float) (raiz * scaleX), 0, 5 * camera.zoom, 50);
            shapeRenderer.setColor(Color.CYAN);
            shapeRenderer.end();
        }
    }

    private void renderAxis() {
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(config.axisColor);
        // Renderizan X e Y
        shapeRenderer.line(0, stage.getHeight()/2f + yoffset, 0, stage.getWidth(), stage.getHeight()/2f + yoffset, 0);
        shapeRenderer.line(stage.getWidth()/2f + xoffset, 0, 0, stage.getWidth()/2f + xoffset, stage.getHeight(), 0);
        // Renderiza la graduacion de los ejejejes
        for (double i = 0; i < stage.getWidth(); i += scaleX) {
            shapeRenderer.line((float) i, -1, (float) i, 1);
            shapeRenderer.line((float) -i, -1, (float) -i, 1);
        }
        for (double i = 0; i < camera.viewportHeight + Math.abs(camera.position.y); i += scaleY) {
            shapeRenderer.line(-1, (float) i, 1, (float) i);
            shapeRenderer.line(-1, (float) -i, 1, (float) -i);
        }
        shapeRenderer.end();
    }
}
