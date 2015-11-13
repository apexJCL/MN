package com.itc.mn.UI;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
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

public class MainScreen implements Screen {

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
    private OrthographicCamera renderCamera;

    {
        scaleX = scaleY = 10;
    }

    public MainScreen() {
        if(Gdx.app.getType() == Application.ApplicationType.Desktop)
            VisUI.load(VisUI.SkinScale.X1);
        else
            VisUI.load(VisUI.SkinScale.X2);
        instantiateThings();
        addGUI();
        // Test
        renderValues = new FuncionX("x^2").obtenerRango(-30, 30);
        setStageProperties();
    }

    private void instantiateThings() {
        stage = new Stage(new ScreenViewport());
        System.out.println(stage.getWidth());
        System.out.println(stage.getHeight());
        camera = (OrthographicCamera) stage.getCamera();
        // Create the camera that will render stuff
        renderCamera = new OrthographicCamera();
        renderCamera.setToOrtho(false, stage.getViewport().getScreenWidth(), stage.getViewport().getScreenHeight());
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

    private void setupTabs() {
        tabbedPane.addListener(new TabbedPaneAdapter() {

            @Override
            public void switchedTab(Tab tab) {
                Table content = tab.getContentTable();
                container.clearChildren();
                container.add(content).center().expand().fill();
                if (content instanceof RenderModule.CustomTable)
                    setRenderStatus(true);
                else
                    setRenderStatus(false);
            }
        });
    }

    private void setStageProperties() {
        stage.addListener(new ActorGestureListener() {
            @Override
            public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
                if (render)
                    renderCamera.position.set(renderCamera.position.x - deltaX * renderCamera.zoom, renderCamera.position.y - deltaY * renderCamera.zoom, 0);
            }

            @Override
            public void zoom(InputEvent event, float initialDistance, float distance) {
                if(render) {
                    float diff = initialDistance - distance;
                    if (diff > 0 && render)
                        renderCamera.zoom += (renderCamera.zoom < 1) ? renderCamera.zoom * 0.01f : 0;
                    else
                        renderCamera.zoom -= (renderCamera.zoom > 0.02f) ? renderCamera.zoom * 0.01f : 0;
                }
            }
        });
        stage.addListener(new InputListener(){
            @Override
            public boolean scrolled(InputEvent event, float x, float y, int amount) {
                if(render) {
                    if (amount > 0)
                        renderCamera.zoom += (renderCamera.zoom < 1) ? 0.015f : 0;
                    else
                        renderCamera.zoom += (renderCamera.zoom > 0.02f) ? -0.015f : 0;
                }
                return super.scrolled(event, x, y, amount);
            }
        });
    }

    public boolean getRenderStatus() {
        return render;
    }

    public void setRenderStatus(final boolean status) {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                render = status;
            }
        });
    }

    public TabbedPane getTabbedPane() {
        return tabbedPane;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (render) {
            renderCamera.update();
            renderAxis();
            renderArray();
            renderRoot();
        }
        renderGUI(delta);
    }

    private void renderGUI(float delta) {
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

    private void renderArray() {
        // Begins shaperenderer with renderCamera
        shapeRenderer.setProjectionMatrix(renderCamera.combined);
        // Begins process
        shapeRenderer.begin(ShapeRenderer.ShapeType.Point);
        // Here we check if we have values to render
        if (renderValues != null) {
            // Define the color of the graphic
            shapeRenderer.setColor(config.singleGraphic);
            // Draw x and y points to simulate function
            for (double[] valor : renderValues)
                shapeRenderer.point(centerX(valor[0]* scaleX), centerY(valor[1]* scaleY), 0);
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
        shapeRenderer.setProjectionMatrix(renderCamera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(config.axisColor);
        // This renders continuosly x and y axis
        shapeRenderer.line(centerX(), -renderCamera.viewportHeight + renderCamera.position.y, centerX(), renderCamera.viewportHeight + renderCamera.position.y);
        shapeRenderer.line(-renderCamera.viewportWidth + renderCamera.position.x + xoffset, centerY(), renderCamera.viewportWidth + renderCamera.position.x, centerY());
        // Renders axis graduation
        for (double i = 0; i < renderCamera.viewportWidth + Math.abs(renderCamera.position.x); i += scaleX) {
            shapeRenderer.line(centerX(i), centerY(1), centerX(i), centerY(-1)); // This renders positive graduation
            shapeRenderer.line(centerX(-i), centerY(-1), centerX(-i) , centerY(1)); // This renders negative graduation
        }
        for (double i = 0; i < renderCamera.viewportHeight + Math.abs(renderCamera.position.y); i += scaleY) {
            shapeRenderer.line(centerX(1), centerY(i), centerX(-1), centerY(i));
            shapeRenderer.line(centerX(-1), centerY(-i), centerX(1), centerY(-i));
        }
        shapeRenderer.end();
    }

    private float centerX(){ return renderCamera.viewportWidth/2f; }
    private float centerY(){ return renderCamera.viewportHeight/2f; }
    private float centerX(double number){ return (float)(centerX()+number); }
    private float centerY(double number){ return  (float) (centerY()+number); }
}
