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
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.itc.mn.Methods.Method;
import com.itc.mn.Screens.Pantalla;
import com.itc.mn.Screens.RenderScreen;
import com.itc.mn.Things.Const;
import com.itc.mn.Things.FuncionX;
import com.itc.mn.Things.Results;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.*;
import com.kotcrab.vis.ui.widget.file.FileChooser;
import com.kotcrab.vis.ui.widget.file.FileChooserAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * This is the class that will hold all the GUI elements, creation and stuff
 */
public class FrontEnd extends Stage {

    private final Pantalla pantalla;
    private final VisTable table;
    private final PopupMenu menu;
    private final Game game;
    public Const config;
    protected TablaResultados tabla_res;
    protected boolean isInputVisible = true;
    private PopupMenu m_metodos, m_archivo;
    private MenuItem banner, archivo, a_abrir, a_guardar, a_salir, graficador, tabla_iter, configuracion, metodos_biseccion, metodos_reglafalsa, metodos_nrapson, metodos_PFijo, metodos_secante, statistic;
    private VentanaValores ventanaValores;
    private MenuItem metodos, matrices;
    private int lastKey;
    private VisSlider ejeX, ejeY;
    private VisTextField entrada;
    private VisLabel funcion;
    private FileChooser fileChooser;
    private String fileToSave;
    private VentanaConfig v_config;
    private FileHandle fileHandle = Gdx.files.internal("i18n/uilang"); // Loads the language file
    private Locale locale = new Locale(Locale.getDefault().toString().substring(0, Locale.getDefault().toString().indexOf('_'))); // Defines the locale to use
    private I18NBundle bundle = I18NBundle.createBundle(fileHandle, locale);
    private Json json = new Json();


    public FrontEnd(Viewport viewport, Game game, Pantalla pantalla) {
        super(viewport);
        config = Const.Load();
        this.game = game;
        this.pantalla = pantalla;
        VisUI.load(VisUI.SkinScale.X2);
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
        config = Const.Load();
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
        Const tmp = Const.Load();
        return tmp;
    }

    public void setConfig(Const config) {
        this.config = config;
    }

    public void reloadConfig(){
        config = Const.Load();
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
        funcion = new VisLabel(bundle.get("function")+": "); // change with i18n file
        entrada = new VisTextField();
        entrada.setMessageText("f(x) = ");
        entrada.pack();
        // Le agregamos un nombre para que pueda ser identificado
        entrada.setName(bundle.get("input"));
        // Los agregamos a la tabla
        table.add(funcion).bottom().left().pad(4);
        table.add(entrada).expand().bottom().left().pad(4);
        // Para ajustar la grafica
        ejeX = new VisSlider(0.1f, config.maxScaleX, 0.001f, false);
        ejeX.setValue(pantalla.scaleX);
        // Agregamos un escuchador de eventos
        ejeX.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                pantalla.scaleX = ((VisSlider) actor).getValue();
            }
        });
        ejeY = new VisSlider(0.1f, config.maxScaleY, 0.001f, false);
        ejeY.setValue(pantalla.scaleY);
        // Agregamos un escuchador de eventos
        ejeY.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                pantalla.scaleY = ((VisSlider) actor).getValue();
            }
        });
        // Los agregamos a la tabla
        VisLabel ajuste = new VisLabel(bundle.get("l_axisadjust")); // change with i18n file
        table.add(ajuste).bottom().right().pad(4f);
        table.add(ejeX).expandY().bottom().left().pad(5f);
        table.add(ejeY).expandY().bottom().left().pad(5f);
        // Para reestablecer escala
        VisTextButton restablece = new VisTextButton(bundle.get("b_restoreaxis")); // change with i18n file
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
        entrada.addListener(new InputManager(entrada));
        // Agregamos el boton
        table.add(restablece).right().expandY().bottom().pad(5f);
    }

    public VisTextField getInputField() {
        return entrada;
    }

    public VisSlider getEjeX() {
        return ejeX;
    }

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
        banner = new MenuItem("Graph v"+Const.version);
        banner.setDisabled(true);
        banner.setColor(Color.CYAN);
        // Create each menu element
        metodos = new MenuItem(bundle.get("m_methods")); // Change with i18n file
        archivo = new MenuItem(bundle.get("m_file")); // Change with i18n file
        // We define the submenues
        metodos.setSubMenu(m_metodos);
        archivo.setSubMenu(m_archivo);
        // We create the other element
        matrices = new MenuItem(bundle.get("m_matrix")); // Change with i18n file
        graficador = new MenuItem(bundle.get("m_grapher")); // Change with i18n file
        tabla_iter = new MenuItem(bundle.get("m_itertable")); // Change with i18n file
        tabla_iter.setDisabled(true); // Change with i18n file
        configuracion = new MenuItem(bundle.get("m_settings")); // Change with i18n file
        // Instantiate the elements of submenu methods
        a_abrir = new MenuItem(bundle.get("m_openfile")); // Change with i18n file
        a_abrir.setShortcut("Ctrl+O");
        a_guardar = new MenuItem(bundle.get("m_savefile")); // Change with i18n file
        a_guardar.setShortcut("Ctrl+S");
        a_salir = new MenuItem(bundle.get("m_exit")); // Change with i18n file
        a_salir.setShortcut("Ctrl+E");
        metodos_biseccion = new MenuItem(bundle.get("m_bisection")); // Change with i18n file
        metodos_reglafalsa = new MenuItem(bundle.get("m_falseposition")); // Change with i18n file
        metodos_PFijo = new MenuItem(bundle.get("m_fixedpoint")); // Change with i18n file
        metodos_nrapson = new MenuItem(bundle.get("m_nr")); // Change with i18n file
        metodos_secante = new MenuItem(bundle.get("m_secant")); // Change with i18n file
        statistic = new MenuItem(bundle.get("statistic"));
        // Assign events to each element
        asignaEventos();
        // Add the elements to the submenu
        menu.addItem(banner);
        menu.addItem(archivo);
        menu.addItem(metodos);
        menu.addItem(matrices);
        menu.addItem(graficador);
        menu.addItem(tabla_iter);
        menu.addItem(statistic);
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
                addActor(new VentanaMensajes(bundle.get("l_about"), "Creado por Jos? Carlos Lopez\nGithub: nchuck\nRepo: MN\n2015\nPowered by:\nJEP 2.24GPL\nlibGDX\nVisUI").fadeIn()); // Change with i18n file
            }
        });
        matrices.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                addActor(new Matrix(bundle).fadeIn());
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
                    if (ventanaValores.getTipo() != Method.Tipo.BISECCION) {
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
                    if (ventanaValores.getTipo() != Method.Tipo.REGLA_FALSA) {
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
                    if (ventanaValores.getTipo() != Method.Tipo.PUNTO_FIJO) {
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
                    if (ventanaValores.getTipo() != Method.Tipo.NEWTON_RAPHSON) {
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
                    if (ventanaValores.getTipo() != Method.Tipo.PUNTO_FIJO) {
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
        statistic.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                addActor(new ListGUI(bundle));
            }
        });
    }

    private void mostrar_secante() {
        String[][] campos = new String[][]{{bundle.get("original_function"), "fx"}, {"xi-1", "xi_1"}, {"xi", "xi"}, {bundle.get("error")+"(0-100)", "ep"}}; // Change with i18n file
        ventanaValores = new VentanaValores(bundle.get("m_secant"), campos, game, Method.Tipo.NEWTON_RAPHSON, bundle);
        ventanaValores.asignaEvento(Method.Tipo.SECANTE);
        addActor(ventanaValores.fadeIn(0.3f));
    }

    private void mostrar_pfijo() {
        String[][] campos = new String[][]{{bundle.get("original_function"), "f1"}, {bundle.get("gx"), "f2"}, {bundle.get("initial_value"), "vi"}, {bundle.get("error")+"(0-100)", "ep"}}; // Change with i18n file
        ventanaValores = new VentanaValores(bundle.get("m_fixedpoint"), campos, game, Method.Tipo.PUNTO_FIJO, bundle);
        ventanaValores.asignaEvento(Method.Tipo.PUNTO_FIJO);
        addActor(ventanaValores.fadeIn(0.3f));
    }

    private void mostrar_nr() {
        String[][] campos = new String[][]{{bundle.get("original_function"), "fx"}, {bundle.get("first_derivative"), "f'x"}, {bundle.get("initial_value"), "vi"}, {bundle.get("error")+"(0-100)", "ep"}}; // Change with i18n file
        ventanaValores = new VentanaValores(bundle.get("m_nr"), campos, game, Method.Tipo.NEWTON_RAPHSON, bundle);
        ventanaValores.asignaEvento(Method.Tipo.NEWTON_RAPHSON);
        addActor(ventanaValores.fadeIn(0.3f));
    }

    private void mostrar_biseccion() {
        String[][] campos = new String[][]{{bundle.get("function"), "f"}, {bundle.get("a_value"), "a"}, {bundle.get("b_value"), "b"}, {bundle.get("error")+"(0-100)", "ep"}}; // Change with i18n file
        ventanaValores = new VentanaValores(bundle.get("m_bisection"), campos, game, Method.Tipo.BISECCION, bundle);
        ventanaValores.asignaEvento(Method.Tipo.BISECCION);
        addActor(ventanaValores.fadeIn(0.3f));
    }

    private void mostrar_rf() {
        String[][] campos = new String[][]{{bundle.get("function"), "f"}, {bundle.get("a_value"), "a"}, {bundle.get("b_value"), "b"}, {bundle.get("error")+"(0-100)", "ep"}}; // Change with i18n file
        ventanaValores = new VentanaValores(bundle.get("m_falseposition"), campos, game, Method.Tipo.REGLA_FALSA, bundle);
        ventanaValores.asignaEvento(Method.Tipo.REGLA_FALSA);
        addActor(ventanaValores.fadeIn(0.3f));
    }

    public void createTablaRes(Method metodo) {
        tabla_res = new TablaResultados(metodo, this);
    }

    public void createTableRes(Results res) {
        tabla_res = new TablaResultados(res, this);
    }

    public void showTablaIter(){ tabla_res.show(getStage());}

    public I18NBundle getBundle() {
        return bundle;
    }

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
                        addActor(new VentanaMensajes(bundle.get("l_nodatatosavetitle"), bundle.get("l_nodatatosavemessage")).fadeIn());
                    else {
                        fileToSave = buildJson(actual.getMetodo());
                        addActor(fileChooser.fadeIn());
                    }
                    break;
                case CERRAR:
                    Gdx.app.exit()
                    ;
                    break;
            }
        }

        private String buildJson(Method metodo) {
            Results results = new Results();
            results.setTipo(metodo._getTipo());
            results.setEncabezados(metodo.getTitles());
            results.setFuncion(metodo.getFuncion());
            results.setRaiz(metodo.raiz);
            results.setResultados(metodo.getResultados());
            results.setTitulo(metodo.getWindowTitle());
            return json.prettyPrint(results);
        }
    }

    private class InputManager extends ClickListener implements Input.TextInputListener{

        private VisTextField field;

        public InputManager(VisTextField field){
            this.field = field;
        }

        @Override
        public void clicked(InputEvent event, float x, float y) {
            if(Gdx.app.getType().equals(Application.ApplicationType.Android))
                Gdx.input.getTextInput(new InputManager(field), field.getName(), field.getText(), field.getMessageText());
        }

        @Override
        public void input(String text) {
            field.setText(text);
            if(!text.matches("(.*,+.*)*"))
                singlePlot();
            else
                multiPlot();
        }

        @Override
        public boolean keyTyped(InputEvent event, char character) {
            if(event.getKeyCode() == Input.Keys.ENTER){
                if(!field.getText().matches("(.*,+.*)*"))
                    return singlePlot();
                else
                    return multiPlot();
            }
            return false;
        }

        @Override
        public void canceled() {

        }

        private boolean singlePlot(){
            final FuncionX fx = new FuncionX(field.getText());
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    ((RenderScreen)game.getScreen()).updateValores(fx.obtenerRango(-10, 10, config.maxPoints));
                }
            });
            ejeX.setValue(Const.XY_AXIS_DEFAULT);
            ejeY.setValue(Const.XY_AXIS_DEFAULT);
            return true;
        }

        private boolean multiPlot(){
            StringTokenizer stk = new StringTokenizer(field.getText(), ",", false);
            final ArrayList<double[][]> functions = new ArrayList(0);
            while(stk.hasMoreElements()){
                FuncionX tmp = new FuncionX(stk.nextToken());
                functions.add(tmp.obtenerRango(-10, 10, config.maxPoints));
            }
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    ((RenderScreen)game.getScreen()).updateMulti(functions);
                }
            });
            //game.setScreen(new RenderScreen(game, functions, true));
            ejeX.setValue(Const.XY_AXIS_DEFAULT);
            ejeY.setValue(Const.XY_AXIS_DEFAULT);
            return true;
        }
    }
}
