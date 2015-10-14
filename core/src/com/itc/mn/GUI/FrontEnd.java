package com.itc.mn.GUI;

import com.badlogic.gdx.*;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.itc.mn.Cosas.Const;
import com.itc.mn.Cosas.Results;
import com.itc.mn.Metodos.Metodo;
import com.itc.mn.Pantallas.Pantalla;
import com.itc.mn.Pantallas.RenderScreen;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.*;
import com.kotcrab.vis.ui.widget.file.FileChooser;
import com.kotcrab.vis.ui.widget.file.FileChooserAdapter;

import java.io.File;

/**
 * This is the class that will hold all the GUI elements, creation and stuff
 */
public class FrontEnd extends Stage {

    protected TablaResultados tabla_res;
    protected boolean isInputVisible = true;
    private final Pantalla pantalla;
    private final VisTable table;
    private final PopupMenu menu;
    private final Game game;
    private PopupMenu m_metodos, m_archivo;
    private MenuItem banner, archivo, a_abrir, a_guardar, a_salir, graficador, tabla_iter, configuracion, metodos_biseccion, metodos_reglafalsa, metodos_nrapson, metodos_PFijo, metodos_secante;
    private VentanaValores ventanaValores;
    private MenuItem metodos, matrices;
    private int lastKey;
    private VisSlider ejeX, ejeY;
    private VisTextField entrada;
    private VisLabel funcion;
    private FileChooser fileChooser;
    private Json json = new Json();
    private String fileToSave;
    private VentanaConfig v_config;
    public Const config;
    private Preferences preferences = Gdx.app.getPreferences(Const.pref_name);

    public FrontEnd(Viewport viewport, Game game, Pantalla pantalla) {
        super(viewport);
        config = json.fromJson(Const.class, Gdx.app.getPreferences(Const.pref_name).getString(Const.id));
        this.game = game;
        this.pantalla = pantalla;
        VisUI.load();
        loadFileChooser(); // Setup the FileChooser
        v_config = new VentanaConfig(this); // Instantiate the preferences Window
        // We begin with the GUI creation
        table = new VisTable(); // A general table that will hold all our components
        table.setSize(Gdx.graphics.getWidth() * 0.95f, Gdx.graphics.getHeight());
        table.setPosition((Gdx.graphics.getWidth() - table.getWidth()) / 2f, (Gdx.graphics.getHeight() - table.getHeight()) / 2f);
        createGeneralGUI();
        // We add a common menu between all
        menu = new PopupMenu();
        // Instantiate the submenu
        m_metodos = new PopupMenu();
        createMenu();
        // FInally, we add the table to the stage, so everything falls in place
        addActor(table);
        // A keyboard manager
        addListener(new InputListener() {

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (lastKey == Input.Keys.CONTROL_LEFT)
                    switch (keycode) {
                        case Input.Keys.M:
                            menu.showMenu(getStage(), menu.getWidth() / 2f, getHeight());
                            break;
                    }
                return super.keyDown(event, keycode);
            }

            @Override
            public boolean keyTyped(InputEvent event, char character) {
                lastKey = event.getKeyCode();
                return super.keyTyped(event, character);
            }

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                return super.keyUp(event, keycode);
            }
        });
        Gdx.input.setCatchMenuKey(true);
    }

    public FrontEnd(Viewport viewport, Game game, Pantalla pantalla, boolean isInputVisible) {
        super(viewport);
        config = json.fromJson(Const.class, Gdx.app.getPreferences(Const.pref_name).getString(Const.id));
        this.game = game;
        this.pantalla = pantalla;
        this.isInputVisible = isInputVisible;
        VisUI.load();
        loadFileChooser(); // Instantiate file chooser
        v_config = new VentanaConfig(this); // Instantiate the preferences Window
        // We begin with the GUI creation
        table = new VisTable(); // A general table that will hold all our components
        table.setSize(Gdx.graphics.getWidth() * 0.95f, Gdx.graphics.getHeight());
        table.setPosition((Gdx.graphics.getWidth() - table.getWidth()) / 2f, (Gdx.graphics.getHeight() - table.getHeight()) / 2f);
        createGeneralGUI();
        // We add a common menu between all
        menu = new PopupMenu();
        // Instantiate the submenu
        m_metodos = new PopupMenu();
        m_archivo = new PopupMenu();
        createMenu();
        // FInally, we add the table to the stage, so everything falls in place
        addActor(table);
        // A keyboard manager
        addListener(new InputListener() {

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (lastKey == Input.Keys.CONTROL_LEFT)
                    switch (keycode) {
                        case Input.Keys.M:
                            menu.showMenu(getStage(), menu.getWidth() / 2f, getHeight());
                            break;
                    }
                return super.keyDown(event, keycode);
            }

            @Override
            public boolean keyTyped(InputEvent event, char character) {
                lastKey = event.getKeyCode();
                return super.keyTyped(event, character);
            }

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                return super.keyUp(event, keycode);
            }
        });
        Gdx.input.setCatchMenuKey(true);
    }

    public Const getConfig(){
        Const tmp = json.fromJson(Const.class, preferences.getString(Const.id));
        return tmp;
    }

    public void reloadConfig(){
        config = json.fromJson(Const.class, Gdx.app.getPreferences(Const.pref_name).getString(Const.id));
        ((RenderScreen)game.getScreen()).reloadConfig();
    }

    /**
     * This method instantiates and defines our Desktop FileChooser
     */
    private void loadFileChooser() {
        fileChooser = new FileChooser(new FileHandle(Gdx.files.getExternalStoragePath()), FileChooser.Mode.OPEN); // By default in Open mode
        fileChooser.setDirectory(Gdx.files.getExternalStoragePath());
        fileChooser.setMultiSelectionEnabled(false); // We disable multiselection
        fileChooser.setFileFilter(new FileChooser.DefaultFileFilter(fileChooser) {
            @Override
            public boolean accept(File f) {
                return f.getName().matches("(.*\\.mn*)") || f.isDirectory();
            }
        });
        fileChooser.setListener(new FileChooserAdapter() {
            @Override
            public void selected(FileHandle file) {
                System.out.println(fileChooser.getMode());
                if (fileChooser.getMode().equals(FileChooser.Mode.SAVE)) {
                    if (file.name().matches("(.*\\.mn*)")) {
                        file.writeString(fileToSave, false);
                    } else {
                        file.writeString(fileToSave, false);
                        FileHandle newFile = Gdx.files.getFileHandle(file.path(), Files.FileType.Absolute);
                        newFile.moveTo(Gdx.files.getFileHandle(file.path() + ".mn", Files.FileType.Absolute));
                    }
                } else if (fileChooser.getMode().equals(FileChooser.Mode.OPEN)) {
                    Object results = json.fromJson(Results.class, file);
                    game.setScreen(new RenderScreen(game, (Results) results));
                }
            }
        });
    }

    public boolean setInputVisible(boolean inputVisible) {
        funcion.setVisible(inputVisible);
        entrada.setVisible(inputVisible);
        return inputVisible;
    }

    private void createGeneralGUI() {
        // Un panel de entrada para re-evaluar
        funcion = new VisLabel("Funcion: ");
        entrada = new VisTextField();
        entrada.setMessageText("f(x) = ");
        entrada.pack();
        // Le agregamos un nombre para que pueda ser identificado
        entrada.setName("entrada");
        // Los agregamos a la tabla
        table.add(funcion).bottom().left().pad(4);
        table.add(entrada).expand().bottom().left().pad(4);
        // Para ajustar la grafica
        ejeX = new VisSlider(0.1f, 50, 0.001f, false);
        ejeX.setValue(pantalla.scaleX);
        // Agregamos un escuchador de eventos
        ejeX.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                pantalla.scaleX = ((VisSlider) actor).getValue();
            }
        });
        ejeY = new VisSlider(0.1f, 50, 0.001f, false);
        ejeY.setValue(pantalla.scaleY);
        // Agregamos un escuchador de eventos
        ejeY.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                pantalla.scaleY = ((VisSlider) actor).getValue();
            }
        });
        // Los agregamos a la tabla
        VisLabel ajuste = new VisLabel("Ajuste ejes");
        table.add(ajuste).bottom().right().pad(4f);
        table.add(ejeX).expandY().bottom().left().pad(5f);
        table.add(ejeY).expandY().bottom().left().pad(5f);
        // Para reestablecer escala
        VisTextButton restablece = new VisTextButton("Reinicia ejes");
        restablece.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        ejeX.setValue(10);
                        ejeY.setValue(10);
                    }
                });
                return true;
            }
        });
        // Agregamos el boton
        table.add(restablece).right().expandY().bottom().pad(5f);
    }

    public VisTextField getInputField() {
        return entrada;
    }

    public VisSlider getEjeX() {
        return ejeX;
    }

    public void setConfig(Const config){ this.config = config; }

    public VisSlider getEjeY() {
        return ejeY;
    }

    public Stage getStage() {
        return this;
    }

    public PopupMenu getMenu() {
        return menu;
    }

    public boolean enableIterTable(boolean flag) {
        tabla_iter.setDisabled(!flag);
        return true;
    }

    private void createMenu() {
        // We create a banner
        banner = new MenuItem("Graph v0.1a");
        banner.setDisabled(true);
        banner.setColor(Color.CYAN);
        // Create each menu element
        metodos = new MenuItem("Metodo");
        archivo = new MenuItem("Archivo");
        // We define the submenues
        metodos.setSubMenu(m_metodos);
        archivo.setSubMenu(m_archivo);
        // We create the other element
        matrices = new MenuItem("Matrices");
        graficador = new MenuItem("Graficador");
        tabla_iter = new MenuItem("Tabla iteraciones");
        tabla_iter.setDisabled(true);
        configuracion = new MenuItem("Ajustes");
        // Instantiate the elements of submenu methods
        a_abrir = new MenuItem("Abrir");
        a_abrir.setShortcut("Ctrl+O");
        a_guardar = new MenuItem("Guardar");
        a_guardar.setShortcut("Ctrl+S");
        a_salir = new MenuItem("Salir");
        a_salir.setShortcut("Ctrl+E");
        metodos_biseccion = new MenuItem("Biseccion");
        metodos_reglafalsa = new MenuItem("Regla Falsa");
        metodos_PFijo = new MenuItem("Punto Fijo");
        metodos_nrapson = new MenuItem("Newton-Raphson");
        metodos_secante = new MenuItem("Secante");
        // Assign events to each element
        asignaEventos();
        // Add the elements to the submenu
        menu.addItem(banner);
        menu.addItem(archivo);
        menu.addItem(metodos);
        menu.addItem(matrices);
        menu.addItem(graficador);
        menu.addItem(tabla_iter);
        menu.addSeparator();
        menu.addItem(configuracion);
        // Add the elements to the submenu
        m_archivo.addItem(a_abrir);
        m_archivo.addItem(a_guardar);
        m_archivo.addItem(a_salir);
        m_metodos.addItem(metodos_biseccion);
        m_metodos.addItem(metodos_reglafalsa);
        m_metodos.addItem(metodos_PFijo);
        m_metodos.addItem(metodos_nrapson);
        m_metodos.addItem(metodos_secante);
    }

    private void asignaEventos() {
        a_abrir.addListener(new FileAction(Accion.ABRIR));
        a_guardar.addListener(new FileAction(Accion.GUARDAR));
        a_salir.addListener(new FileAction(Accion.CERRAR));
        graficador.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                RenderScreen s = new RenderScreen(game);
                game.setScreen(s);
            }
        });
        banner.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                addActor(new VentanaMensajes("Acerca de", "Creado por Jose Carlos Lopez\nGithub: nchuck\nRepo: MN\n2015\nPowered by JEP 2.24GPL").fadeIn());
            }
        });
        matrices.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                addActor(new Matrix().fadeIn());
            }
        });
        configuracion.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                addActor(v_config.fadeIn());
            }
        });
        //Para biseccion
        metodos_biseccion.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (ventanaValores == null || !getActors().contains(ventanaValores, true))
                    mostrar_biseccion();
                else {
                    if (ventanaValores.getTipo() != Metodo.Tipo.BISECCION) {
                        ventanaValores.fadeOut();
                        mostrar_biseccion();
                    } else
                        ventanaValores.parpadear();
                }
            }
        });
        // para regla falsa
        metodos_reglafalsa.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (ventanaValores == null || !getActors().contains(ventanaValores, true))
                    mostrar_rf();
                else {
                    if (ventanaValores.getTipo() != Metodo.Tipo.REGLA_FALSA) {
                        ventanaValores.fadeOut();
                        mostrar_rf();
                    } else
                        ventanaValores.parpadear();
                }
            }
        });
        // Para punto fijo
        metodos_PFijo.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (ventanaValores == null || !getActors().contains(ventanaValores, true))
                    mostrar_pfijo();
                else {
                    if (ventanaValores.getTipo() != Metodo.Tipo.PUNTO_FIJO) {
                        ventanaValores.fadeOut();
                        mostrar_pfijo();
                    } else
                        ventanaValores.parpadear();
                }
            }
        });
        // Para Newton Raphson
        metodos_nrapson.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (ventanaValores == null || !getActors().contains(ventanaValores, true))
                    mostrar_nr();
                else {
                    if (ventanaValores.getTipo() != Metodo.Tipo.NEWTON_RAPHSON) {
                        ventanaValores.fadeOut();
                        mostrar_nr();
                    } else
                        ventanaValores.parpadear();
                }
            }
        });
        // Para secante
        metodos_secante.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (ventanaValores == null || !getActors().contains(ventanaValores, true))
                    mostrar_secante();
                else {
                    if (ventanaValores.getTipo() != Metodo.Tipo.PUNTO_FIJO) {
                        ventanaValores.fadeOut();
                        mostrar_secante();
                    } else
                        ventanaValores.parpadear();
                }
            }
        });
        tabla_iter.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!tabla_iter.isDisabled()) {
                    tabla_res.show(getStage());
                }
            }
        });
    }

    private void mostrar_secante() {
        String[][] campos = new String[][]{{"Funcion Original", "fx"}, {"xi-1", "xi_1"}, {"xi", "xi"}, {"Error (0-100)", "ep"}};
        ventanaValores = new VentanaValores("Secante", campos, game, Metodo.Tipo.NEWTON_RAPHSON);
        ventanaValores.asignaEvento(Metodo.Tipo.SECANTE);
        addActor(ventanaValores.fadeIn(0.3f));
    }

    private void mostrar_pfijo() {
        String[][] campos = new String[][]{{"Funcion Original", "f1"}, {"Funcion Despejada", "f2"}, {"Valor inicial", "vi"}, {"Error (0-100)", "ep"}};
        ventanaValores = new VentanaValores("Punto Fijo", campos, game, Metodo.Tipo.PUNTO_FIJO);
        ventanaValores.asignaEvento(Metodo.Tipo.PUNTO_FIJO);
        addActor(ventanaValores.fadeIn(0.3f));
    }

    private void mostrar_nr() {
        String[][] campos = new String[][]{{"Funcion Original", "fx"}, {"Primer Derivada", "f'x"}, {"Valor inicial", "vi"}, {"Error (0-100)", "ep"}};
        ventanaValores = new VentanaValores("Newton-Raphson", campos, game, Metodo.Tipo.NEWTON_RAPHSON);
        ventanaValores.asignaEvento(Metodo.Tipo.NEWTON_RAPHSON);
        addActor(ventanaValores.fadeIn(0.3f));
    }

    private void mostrar_biseccion() {
        String[][] campos = new String[][]{{"Funcion", "f"}, {"Valor a", "a"}, {"Valor b", "b"}, {"Error (0-100)", "ep"}};
        ventanaValores = new VentanaValores("Biseccion", campos, game, Metodo.Tipo.BISECCION);
        ventanaValores.asignaEvento(Metodo.Tipo.BISECCION);
        addActor(ventanaValores.fadeIn(0.3f));
    }

    private void mostrar_rf() {
        String[][] campos = new String[][]{{"Funcion", "f"}, {"Valor a", "a"}, {"Valor b", "b"}, {"Error (0-100)", "ep"}};
        ventanaValores = new VentanaValores("Regla Falsa", campos, game, Metodo.Tipo.REGLA_FALSA);
        ventanaValores.asignaEvento(Metodo.Tipo.REGLA_FALSA);
        addActor(ventanaValores.fadeIn(0.3f));
    }

    public void createTablaRes(Metodo metodo) {
        tabla_res = new TablaResultados(metodo, this);
    }

    public void createTableRes(Results res) {
        tabla_res = new TablaResultados(res, this);
    }

    public void showTablaIter(){ tabla_res.show(getStage());}

    public enum Accion {
        ABRIR, GUARDAR, CERRAR
    }

    private class FileAction extends ClickListener {

        private Accion accion;

        public FileAction(Accion accion) {
            this.accion = accion;
        }

        private FileChooser.Mode setMode() {
            switch (accion) {
                case ABRIR:
                    return FileChooser.Mode.OPEN;
                case GUARDAR:
                    return FileChooser.Mode.SAVE;
            }
            return null;
        }

        @Override
        public void clicked(InputEvent event, float x, float y) {
            fileChooser.setMode(setMode());
            fileChooser.setSize(Gdx.graphics.getWidth()*0.4f, Gdx.graphics.getHeight()*0.6f);
            switch (accion) {
                case ABRIR:
                    addActor(fileChooser.fadeIn());
                    break;
                case GUARDAR:
                    RenderScreen actual = (RenderScreen) game.getScreen();
                    if (actual.getMetodo() == null)
                        addActor(new VentanaMensajes("Lo sentimos.", "No hay algo que guardar.").fadeIn());
                    else {
                        fileToSave = buildJson(actual.getMetodo());
                        addActor(fileChooser.fadeIn());
                    }
                    break;
                case CERRAR:
                    Gdx.app.exit();
                    break;
            }
        }

        private String buildJson(Metodo metodo) {
            Results results = new Results();
            results.setTipo(metodo._getTipo());
            results.setEncabezados(metodo.getEncabezados());
            results.setFuncion(metodo.getFuncion());
            results.setRaiz(metodo.raiz);
            results.setResultados(metodo.getResultados());
            results.setTitulo(metodo.getTitulo());
            return json.prettyPrint(results);
        }
    }
}
