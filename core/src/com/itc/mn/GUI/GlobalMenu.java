package com.itc.mn.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.I18NBundle;
import com.itc.mn.Things.FuncionX;
import com.itc.mn.UI.MainScreen;
import com.itc.mn.UI.Modules.MatrixModule;
import com.itc.mn.UI.Modules.RenderModule;
import com.itc.mn.UI.Modules.WelcomeModule;
import com.kotcrab.vis.ui.widget.Menu;
import com.kotcrab.vis.ui.widget.MenuBar;
import com.kotcrab.vis.ui.widget.MenuItem;
import com.kotcrab.vis.ui.widget.PopupMenu;

import java.util.Locale;

public class GlobalMenu extends MenuBar {

    private final MainScreen mainScreen;
    private FileHandle fileHandle = Gdx.files.internal("i18n/uilang"); // Loads the language file
    private Locale locale = new Locale(Locale.getDefault().toString().substring(0, Locale.getDefault().toString().indexOf('_'))); // Defines the locale to use
    private I18NBundle bundle = I18NBundle.createBundle(fileHandle, locale);
    private Menu fileMenu, sectionMenu, windowMenu;
    private MenuItem i_exit, i_open, i_save, i_settings;
    private MenuItem s_methods, s_matrix, s_statistics, s_help, s_about;
    private MenuItem m_bisection, m_fauxrule, m_nraphson, m_fixedpoint, m_secant;
    private MenuItem s_render;

    public GlobalMenu(MainScreen mainScreen){
        this.mainScreen = mainScreen;
        createMenus();
        addActions();
        setupTab();
    }

    private void setupTab() {
        mainScreen.getTabbedPane().add(new WelcomeModule(bundle));
        mainScreen.getTabbedPane().add(new RenderModule(new FuncionX("x^2").obtenerRango(-10, 10)));
    }

    private void createMenus () {
        fileMenu = new Menu(bundle.get("m_file"));
        sectionMenu = new Menu(bundle.get("sections"));
        //windowMenu = new Menu("Window");
        Menu helpMenu = new Menu(bundle.get("help"));
        // Creating menu Items
        i_open = new MenuItem(bundle.get("m_openfile"));
        i_save = new MenuItem(bundle.get("m_savefile"));
        i_exit = new MenuItem(bundle.get("m_exit"));
        i_settings = new MenuItem(bundle.get("m_settings")); // Change with i18n file
        fileMenu.addItem(i_open);
        fileMenu.addItem(i_save);
        fileMenu.addItem(i_exit);
        fileMenu.addSeparator();
        fileMenu.addItem(i_settings);

        // Creating items and adding them to the tool menu
        s_methods = new MenuItem(bundle.get("m_methods"));
        s_matrix = new MenuItem(bundle.get("m_matrix"));
        s_statistics = new MenuItem(bundle.get("statistic"));

        // Adding the items to the submenu methods
        s_methods.setSubMenu(createMethodsSubMenu());

        sectionMenu.addItem(s_methods);
        sectionMenu.addItem(s_matrix);
        sectionMenu.addItem(s_statistics);

//        windowMenu.addItem(new MenuItem("menuitem #9"));
//        windowMenu.addItem(new MenuItem("menuitem #10"));
//        windowMenu.addItem(new MenuItem("menuitem #11"));
//        windowMenu.addSeparator();
//        windowMenu.addItem(new MenuItem("menuitem #12"));

        // Creating help menu stuff
        s_about = new MenuItem(bundle.get("l_about"));
        s_help = new MenuItem(bundle.get("m_help"));
        s_render = new MenuItem("Toggle Render");

        helpMenu.addItem(s_help);
        helpMenu.addItem(s_about);
        helpMenu.addItem(s_render);

        addMenu(fileMenu);
        addMenu(sectionMenu);
//        addMenu(windowMenu);
        addMenu(helpMenu);
    }

    private PopupMenu createMethodsSubMenu(){
        PopupMenu menu = new PopupMenu();
        m_bisection = new MenuItem(bundle.get("m_bisection"));
        m_fauxrule = new MenuItem(bundle.get("m_falseposition"));
        m_fixedpoint = new MenuItem(bundle.get("m_fixedpoint"));
        m_nraphson = new MenuItem(bundle.get("m_nr"));
        m_secant = new MenuItem(bundle.get("m_secant"));
        menu.addItem(m_bisection);
        menu.addItem(m_fauxrule);
        menu.addItem(m_fixedpoint);
        menu.addItem(m_nraphson);
        menu.addItem(m_secant);
        return menu;
    }

    private void addActions(){
        s_render.addListener(new MenuListener(ButtonType.RENDER));
        i_exit.addListener(new MenuListener(ButtonType.EXIT));
        s_matrix.addListener(new MenuListener(ButtonType.MATRIX));
    }

    private enum ButtonType{
        RENDER, EXIT, MATRIX
    }

    private class MenuListener extends ClickListener {

        private ButtonType type;

        public MenuListener(ButtonType type){
            this.type = type;
        }

        @Override
        public void clicked(InputEvent event, float x, float y) {
            switch (type){
                case RENDER:
                    mainScreen.setRenderStatus(!mainScreen.getRenderStatus());
                    break;
                case EXIT:
                    Gdx.app.exit();
                    break;
                case MATRIX:
                    mainScreen.getTabbedPane().add(new MatrixModule(bundle));
                    System.out.println("Added");
                    break;
            }
        }
    }
}
