package com.itc.mn.Pantallas;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.itc.mn.Cosas.Const;
import com.itc.mn.Cosas.TablaResultados;
import com.itc.mn.Metodos.Metodo;
import com.itc.mn.Metodos.PFijo;
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
    protected final Stage gui_stage, camera_stage;
    protected Game game;
    protected FitViewport viewport;
    protected VisUI visui;
    protected ShapeRenderer shapeRenderer;
    protected boolean debugEnabled;
    protected VisTable table;
    protected double precision;
    protected TablaResultados tabla_res;
    private PopupMenu menu, m_metodos;
    private MenuItem metodos;
    private MenuItem metodos_PFijo;
    private MenuItem graficador;
    protected volatile MenuItem tabla_iter;
    private VisWindow window;
    private InputMultiplexer multiplexer;

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
        // Creamos el gui_stage, el cual albergara los botones entre otras cosas
        gui_stage = new Stage(viewport);
        // Inicializamos el camera_Stage que contendra los elementos de la gui
        camera_stage = new Stage(viewport);
        // Creamos el multiplexer para la captura de eventos
        multiplexer = new InputMultiplexer(gui_stage, camera_stage);
        // Definimos como procesador el multiplexer
        Gdx.input.setInputProcessor(multiplexer);
        // Importamos el VisUI
        visui.load();

        // Agregamos los escuchadores del gui_stage
        camera_stage.addListener(new ActorGestureListener() {
            @Override
            public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
                super.pan(event, x, y, deltaX, deltaY);
                if (y > -230) // Zona muerta para que al interactuar con los sliders o el TextField, no haga panning
                    camera.position.set(camera.position.x - deltaX * camera.zoom, camera.position.y - deltaY * camera.zoom, 0);
            }

            @Override
            public void zoom(InputEvent event, float initialDistance, float distance) {
                super.zoom(event, initialDistance, distance);
                float diff = initialDistance - distance;
                if (diff > 0)
                    camera.zoom += (camera.zoom < 1) ? camera.zoom * 0.01f : 0;
                else
                    camera.zoom -= (camera.zoom > 0.02f) ? camera.zoom * 0.01f : 0;

            }

            @Override
            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // Para eventos tactiles de android, usamos puntero, para PC usamos button
                if (button == 1)
                    menu.showMenu(gui_stage, x, y);
            }

            @Override
            public boolean longPress(Actor actor, float x, float y) {
                menu.showMenu(gui_stage, x, y);
                return true;
            }

            @Override
            public boolean handle(Event e) {
                return super.handle(e);
            }
        });
        camera_stage.addListener(new InputListener() {
            @Override
            public boolean keyTyped(InputEvent event, char character) {
                // Esto se encarga de controlar el zoom con el teclado
                // El Zoom de la camara va de 0f a 1f, siendo 0f lo mas cercano, pero
                // causa problemas al renderizar, por eso se limita a 0.02f
                if (event.getKeyCode() == Input.Keys.DOWN) { // Zoom menos
                    camera.zoom += (camera.zoom < 1) ? 0.01f : 0;
                    return true;
                } else if (event.getKeyCode() == Input.Keys.UP) { // Zoom mas
                    camera.zoom -= (camera.zoom > 0.02f) ? 0.01f : 0;
                    return true;
                }
                return super.keyTyped(event, character);
            }

            @Override
            public boolean scrolled(InputEvent event, float x, float y, int amount) {
                if (amount > 0)
                    camera.zoom += (camera.zoom < 1) ? 0.015f : 0;
                else
                    camera.zoom += (camera.zoom > 0.02f) ? -0.015f : 0;
                return super.scrolled(event, x, y, amount);
            }
        });
    }

    public void construyeGUI(){
        // Una tabla para gobernarlos a todos... muahahahaha
        table = new VisTable();
        table.setSize(Gdx.graphics.getWidth() * 0.95f, Gdx.graphics.getHeight());
        table.setPosition((Gdx.graphics.getWidth() - table.getWidth()) / 2f, (Gdx.graphics.getHeight() - table.getHeight()) / 2f);
        // Agregando un menu en comun
        menu = new PopupMenu();
        // Instanciamos los submenues
        m_metodos = new PopupMenu();
        creaItems();
        // Agregamos la tabla al gui_stage
        gui_stage.addActor(table);
    }

    private void creaItems(){
        // Creamos los elementos del menu
        metodos = new MenuItem("Metodos");
        metodos.setSubMenu(m_metodos);
        graficador = new MenuItem("Graficador");
        tabla_iter = new MenuItem("Tabla iteraciones");
        tabla_iter.setDisabled(true);

        // Instanciamos los elemenos del submenu metodos
        metodos_PFijo = new MenuItem("Punto Fijo");

        // Asignamos eventos a cada item
        asignaEventos();
         // Agregamos los submenues al menu principal
        menu.addItem(metodos);
        menu.addItem(graficador);
        menu.addItem(tabla_iter);
        // Agregamos los elementos de los submenues
        m_metodos.addItem(metodos_PFijo);
    }

    private void asignaEventos(){
        graficador.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                RenderScreen s = new RenderScreen(game);
                game.setScreen(s);
            }
        });
        metodos_PFijo.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (window == null || !gui_stage.getActors().contains(window, true))
                    mpfijo_gui();
            }
        });
        tabla_iter.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!tabla_iter.isDisabled()) {
                    tabla_res.show(gui_stage);
                }
            }
        });
    }

    private void mpfijo_gui(){
        window = new VisWindow("Punto Fijo");
        // Declaramos las cosas de entrada
        VisTextField funcion1 = new VisTextArea();
        VisTextField funcion2 = new VisTextArea();
        VisTextField valor_inicial = new VisTextArea();
        VisTextField ep = new VisTextArea();
        VisTextButton aceptar = new VisTextButton("Aceptar");
        // Agregamos el escuchador al boton
        aceptar.addListener(new Proceso(window, Metodo.Tipo.PUNTO_FIJO));
        // Definimos su hint
        funcion1.setMessageText("Funcion original");
        funcion2.setMessageText("Funcion despejada");
        valor_inicial.setMessageText("Valor inicial");
        ep.setMessageText("Error (0-100)");
        // Identificadores para extraer datos
        funcion1.setName("f1");
        funcion2.setName("f2");
        valor_inicial.setName("vi");
        ep.setName("ep");
        // Agregamos a la ventana
        window.add(funcion1).expandX().center().pad(1f).row();
        window.add(funcion2).expandX().center().pad(1f).row();
        window.add(valor_inicial).expandX().center().pad(1f).row();
        window.add(ep).expandX().center().pad(1f).row();
        window.add(aceptar).expandX().center().pad(1f).row();
        // Agregamos boton de cerrar y cerrar con Esc
        window.closeOnEscape();
        window.addCloseButton();
        // Definimos su posicion
        window.setPosition((gui_stage.getWidth()-window.getWidth())/2f, (gui_stage.getHeight()-window.getHeight())/2f);
        // Ajustamos
        window.pack();
        gui_stage.addActor(window.fadeIn(0.3f));
    }

    @Override
    public void show() {
        gui_stage.setDebugAll(debugEnabled);
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
        visui.dispose();
    }



    private class Proceso extends ClickListener {

        private final Metodo.Tipo tipo;
        private final VisWindow window;
        private String f1, f2, vi, ep;

        public Proceso(VisWindow window, Metodo.Tipo tipo){
            this.window = window;
            this.tipo = tipo;
        }

        @Override
        public void clicked(InputEvent event, float x, float y) {
            switch (tipo){
                case PUNTO_FIJO:
                    String name;
                    for (Actor a : window.getChildren()) {
                        name = a.getName();
                        if(name != null){
                            if (name.equals("f1"))
                                f1 = ((VisTextField) a).getText();
                            else if (name.equals("f2"))
                                f2 = ((VisTextField) a).getText();
                            else if (name.equals("vi"))
                                vi = ((VisTextField) a).getText();
                            else if (name.equals("ep"))
                                ep = ((VisTextField) a).getText();
                        }
                    }
                    try {
                        game.setScreen(new RenderScreen(game, new PFijo(f1, f2, Double.parseDouble(vi), Double.parseDouble(ep) / 100)));
                    } catch (Exception e){
                        window.fadeOut();
                    }
                    break;
                case BISECCION:
                    break;
            }
        }
    }

}
