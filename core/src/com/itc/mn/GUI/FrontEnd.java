package com.itc.mn.GUI;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.itc.mn.Metodos.Metodo;
import com.itc.mn.Pantallas.RenderScreen;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.MenuItem;
import com.kotcrab.vis.ui.widget.PopupMenu;
import com.kotcrab.vis.ui.widget.VisTable;

/**
 * This is the class that will hold all the GUI elements, creation and stuff
 */
public class FrontEnd extends Stage {

    private final VisTable table;
    private final PopupMenu m_metodos;
    private final PopupMenu menu;
    private final Game game;
    private MenuItem banner, graficador, tabla_iter, configuracion, metodos_biseccion, metodos_reglafalsa, metodos_nrapson, metodos_PFijo, metodos_secante;
    private VentanaValores ventanaValores;
    protected TablaResultados tabla_res;
    private MenuItem metodos, matrices;

    public FrontEnd(Viewport viewport, Game game) {
        super(viewport);
        this.game = game;
        VisUI.load();
        // We begin with the GUI creation
        table = new VisTable(); // A general table that will hold all our components
        table.setSize(Gdx.graphics.getWidth() * 0.95f, Gdx.graphics.getHeight());
        table.setPosition((Gdx.graphics.getWidth() - table.getWidth()) / 2f, (Gdx.graphics.getHeight() - table.getHeight()) / 2f);
        addActor(table);
        // We add a common menu between all
        menu = new PopupMenu();
        // Instantiate the submenu
        m_metodos = new PopupMenu();
        createItems();
        // FInally, we add the table to the stage, so everything falls in place
        addActor(table);
    }

    public Stage getStage() {
        return this;
    }

    public PopupMenu getMenu() {
        return menu;
    }

    public TablaResultados getTablaRes() {
        return tabla_res;
    }

    public boolean enableIterTable(boolean flag) {
        tabla_iter.setDisabled(!flag);
        return true;
    }

    public VisTable getTable() {
        return table;
    }

    private void createItems() {
        // We create a banner
        banner = new MenuItem("Graph v0.1a");
        banner.setDisabled(true);
        banner.setColor(Color.CYAN);
        // Create each menu element
        metodos = new MenuItem("Metodo");
        // We create the other element
        matrices = new MenuItem("Matrices");
        metodos.setSubMenu(m_metodos);
        graficador = new MenuItem("Graficador");
        tabla_iter = new MenuItem("Tabla iteraciones");
        tabla_iter.setDisabled(true);
        configuracion = new MenuItem("Configuracion");

        // Instantiate the elements of submenu methods
        metodos_biseccion = new MenuItem("Biseccion");
        metodos_reglafalsa = new MenuItem("Regla Falsa");
        metodos_PFijo = new MenuItem("Punto Fijo");
        metodos_nrapson = new MenuItem("Newton-Raphson");
        metodos_secante = new MenuItem("Secante");

        // Assign events to each element
        asignaEventos();
        // Add the elements to the submenu
        menu.addItem(banner);
        menu.addItem(metodos);
        menu.addItem(matrices);
        menu.addItem(graficador);
        menu.addItem(tabla_iter);
        menu.addSeparator();
        menu.addItem(configuracion);
        // Add the elements to the submenu
        m_metodos.addItem(metodos_biseccion);
        m_metodos.addItem(metodos_reglafalsa);
        m_metodos.addItem(metodos_PFijo);
        m_metodos.addItem(metodos_nrapson);
        m_metodos.addItem(metodos_secante);
    }

    private void asignaEventos() {
        graficador.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                RenderScreen s = new RenderScreen(game);
                game.setScreen(s);
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
        tabla_res = new TablaResultados(metodo);
    }
}
