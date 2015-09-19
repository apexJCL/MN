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
    private static String[] nombreMenu = {"Archivo", "Herramientas"}; // Se encarga de los menues normales
    private static String[][] contenidoMenu = {{"Metodo>", "Salir"}, {"Graficador"}}; // Cada uno es el contenido de cada menu declarado arriba
    private Menu[] menues;
    private MenuItem[] subMenues;

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
        // Definimos el contenedor de menues
        menues = new Menu[nombreMenu.length];
        // Agregamos menues basicos
        for (int i = 0; i < nombreMenu.length; i++)
            menues[i] = new Menu(nombreMenu[i]);
        // Agregamos los menues creados a la barra
        for (Menu menu : menues)
            menubar.addMenu(menu);
        // Agregando el menu al table
        table.add(menubar.getTable()).fillX().expand().top().row();
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
