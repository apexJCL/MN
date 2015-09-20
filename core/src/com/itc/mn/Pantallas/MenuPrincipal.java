package com.itc.mn.Pantallas;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.kotcrab.vis.ui.widget.*;

/**
 * Created by zero_ on 18/09/2015.
 */
public class MenuPrincipal extends Pantalla {

    private Game game;
    private MenuBar menubar;
    private VisTable table;
    private static String[] nombreMenu = {"Metodos", "Herramientas", "Mas"}; // Se encarga de los menues normales
    private Menu[] menues;
    private MenuItem primer_parcial;

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
    }

    private void construyeGUIDesktop(){
        construyeGUI();
        // Creamos el menu
        menubar = new MenuBar();
        // Definimos el contenedor de menues
        menues = new Menu[nombreMenu.length];
        // Agregamos menues basicos
        for (int i = 0; i < nombreMenu.length; i++) {
            menues[i] = new Menu(nombreMenu[i]);
            menues[i].setName(nombreMenu[i]);
        }
        // Agregamos los menues creados a la barra
        for (Menu menu : menues)
            menubar.addMenu(menu);
        // Inicializamos el submenu de los Items
        primer_parcial = new MenuItem("Primer Parcial");
        // Agregamos el submenu de primer_parcial al menu Archivo
        for (Menu menu : menues) {
            if(menu.getName().equals("Metodos")) {
                menu.addItem(primer_parcial);
                break;
            }
        }
        // Agregando el menu al table
        table.add(menubar.getTable()).fillX().expand().top().row();
    }

    private void construyeGUIMobile(){
        construyeGUI();
        VisLabel label = new VisLabel("This is Android");
        table.add(label).expand().center();
    }

    private void construyeGUI(){
        // Creamos la tabla que contendra el menu principal
        table = new VisTable();
        // Definimos su tamano
        table.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        // Definimos su posicion
        table.setPosition((Gdx.graphics.getWidth() - table.getWidth())/2f, (Gdx.graphics.getHeight() - table.getHeight())/2f);
        stage.addActor(table);
    }

}
