package com.itc.mn.Pantallas;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.kotcrab.vis.ui.widget.Menu;
import com.kotcrab.vis.ui.widget.MenuBar;
import com.kotcrab.vis.ui.widget.MenuItem;
import com.kotcrab.vis.ui.widget.VisTable;

/**
 * Created by zero_ on 18/09/2015.
 */
public class MenuPrincipal extends Pantalla {

    private Game game;
    private MenuBar menubar;
    private VisTable table;

    public MenuPrincipal(Game game){
        this.game = game;
        switch (Gdx.app.getType()){

            case Android:
                construyeGUIMobile();
                break;
            case HeadlessDesktop:
            case Desktop:
                construyeGUIDesktop();
                break;
        }
        debugEnabled = true;
    }

    private void construyeGUIDesktop(){
        construyeGUI();
        // Creamos el menu
        menubar = new MenuBar();
        // Agregamos menues basicos
        Menu metodos = new Menu("M\u00E9todos");
        // Agregamos submenus padres al menuBar
        menubar.addMenu(metodos);
        // Agregando el menu al table
        table.add(menubar.getTable()).top().fillX().expandX().row();
        stage.addActor(table);
    }

    private void construyeGUIMobile(){
        construyeGUI();
    }

    private void construyeGUI(){
        // Creamos la tabla que contendra el menu principal
        table = new VisTable();
        // Definimos su tamano
        table.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        // Definimos su posicion
        table.setPosition((Gdx.graphics.getWidth() - table.getWidth())/2f, (Gdx.graphics.getHeight() - table.getHeight())/2f);
    }

}
