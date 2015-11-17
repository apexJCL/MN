package com.itc.mn.UI;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
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
import com.itc.mn.Things.Const;
import com.itc.mn.UI.EventHandlers.RenderInputHandler;
import com.itc.mn.UI.Modules.MethodModule;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.tabbedpane.Tab;
import com.kotcrab.vis.ui.widget.tabbedpane.TabbedPane;
import com.kotcrab.vis.ui.widget.tabbedpane.TabbedPaneAdapter;

import java.util.ArrayList;

public class MainScreen implements Screen {

    protected Stage stage;
    private RenderInputHandler renderHandler;
    private GlobalMenu menuBar;
    private TabbedPane tabbedPane;
    private Table root, container;
    private double[][] renderValues;
    private Const config = Const.Load();
    private boolean render = false;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private float scaleX, scaleY;
    private ArrayList<double[][]> funciones;
    private Color[] colores = {Color.BLUE, Color.GREEN, Color.CYAN, Color.YELLOW, Color.FIREBRICK, Color.ROYAL, Color.RED, Color.SALMON, Color.MAGENTA, Color.LIME, Color.TAN, Color.TEAL, Color.VIOLET};
    private boolean isRootAvailable = false;
    private double raiz = 0;
    private float xoffset, yoffset;
    private OrthographicCamera renderCamera;
    private InputMultiplexer inputMultiplexer;

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
        setStageProperties();
    }

    private void instantiateThings() {
        stage = new Stage(new ScreenViewport());
        // Create the camera that will render stuff
        renderCamera = new OrthographicCamera();
        renderCamera.setToOrtho(false, stage.getViewport().getScreenWidth(), stage.getViewport().getScreenHeight());
        // This one will handle events on the render part
        renderHandler = new RenderInputHandler(new ScreenViewport(), renderCamera);
        // This will handle events for the GUI and Render Movements
        inputMultiplexer = new InputMultiplexer(stage, renderHandler);
        // Set the multiplexer as the input processor
        Gdx.input.setInputProcessor(inputMultiplexer);
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
                if (content instanceof MethodModule.CustomTable) {
                    isRootAvailable = true;
                    renderValues = ((MethodModule)tab).getValues();
                    raiz = ((MethodModule)tab).getRoot();
                    setRenderStatus(true);
                }
                else
                    setRenderStatus(false);
            }
        });
    }

    private void setStageProperties() {

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
            renderHandler.act();
            renderHandler.draw();
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
            shapeRenderer.setProjectionMatrix(renderCamera.combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(config.rootColor);
            shapeRenderer.circle(centerX(raiz * scaleX), centerY(), 5 * renderCamera.zoom, 50);
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

    public Stage getStage() {
        return stage;
    }
}
