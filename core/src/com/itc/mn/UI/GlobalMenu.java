package com.itc.mn.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.I18NBundle;
import com.itc.mn.Things.Const;
import com.itc.mn.UI.Modules.MatrixModule;
import com.itc.mn.UI.Modules.MethodModule;
import com.itc.mn.UI.Modules.StatisticsModule;
import com.itc.mn.UI.Modules.WelcomeModule;
import com.itc.mn.UI.Windows.InputWindow;
import com.kotcrab.vis.ui.widget.*;

public class GlobalMenu extends MenuBar {

    private final MainScreen mainScreen;
    private Menu fileMenu, sectionMenu, windowMenu;
    private MenuItem i_exit, i_open, i_save, i_settings;
    private MenuItem s_methods, s_matrix, s_statistics, s_help, s_about;
    private MenuItem m_bisection, m_fauxrule, m_nraphson, m_fixedpoint, m_secant;
    private MenuItem s_render;
    private I18NBundle bundle = Const.loadBundle();

    public GlobalMenu(MainScreen mainScreen){
        this.mainScreen = mainScreen;
        createMenus();
        addActions();
        setupTab();
    }

    private void setupTab() {
        mainScreen.getTabbedPane().add(new WelcomeModule(bundle));
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
        s_render.addListener(new MenuListener(ActionType.RENDER));
        i_exit.addListener(new MenuListener(ActionType.EXIT));
        s_matrix.addListener(new MenuListener(ActionType.MATRIX));
        s_statistics.addListener(new MenuListener(ActionType.STATISTICS));
        // Methods
        m_bisection.addListener(new MenuListener(ActionType.BISECTION));
        m_fauxrule.addListener(new MenuListener(ActionType.REGULI));
        m_fixedpoint.addListener(new MenuListener(ActionType.FIXED_POINT));
        m_nraphson.addListener(new MenuListener(ActionType.NR));
        m_secant.addListener(new MenuListener(ActionType.SECANT));
    }

    public enum ActionType {
        RENDER, EXIT, MATRIX, BISECTION, REGULI, FIXED_POINT, NR, SECANT, STATISTICS
    }

    private class MenuListener extends ClickListener {

        private ActionType type;

        public MenuListener(ActionType type){
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
                    mainScreen.getTabbedPane().add(new MatrixModule());
                    System.out.println("Added");
                    break;
                case STATISTICS:
                    mainScreen.getTabbedPane().add(new StatisticsModule());
                    break;
                case SECANT:
                case NR:
                case REGULI:
                case FIXED_POINT:
                case BISECTION:
                    MethodWindow();
                    break;
            }
        }

        /**
         * This wii hold all the logic for the show-call windows that executes methods
         */
        private void MethodWindow(){
            InputWindow tmp = null;
            switch (type){
                case BISECTION:
                    tmp = new InputWindow(bundle.get("m_bisection"), new String[][]{{bundle.get("function"), "f"}, {bundle.get("a_value"), "a"}, {bundle.get("b_value"), "b"}, {bundle.get("error")+"(0-100)", "ep"}});
                    break;
                case REGULI:
                    tmp = new InputWindow(bundle.get("m_falseposition"), new String[][]{{bundle.get("function"), "f"}, {bundle.get("a_value"), "a"}, {bundle.get("b_value"), "b"}, {bundle.get("error"), "ep"}});
                    break;
                case FIXED_POINT:
                    tmp = new InputWindow(bundle.get("m_fixedpoint"), new String[][]{{bundle.get("original_function"), "f"}, {bundle.get("x_function"), "f_1"}, {bundle.get("initial_value"), "initial"}, {bundle.get("error"), "ep"}});
                    break;
                case NR:
                    tmp = new InputWindow(bundle.get("m_nr"), new String[][]{{bundle.get("original_function"), "f"}, {bundle.get("first_derivative"), "f_1"}, {bundle.get("initial_value"), "initial"}, {bundle.get("error"), "ep"}});
                    break;
                case SECANT:
                    tmp = new InputWindow(bundle.get("m_secant"), new String[][]{{bundle.get("function"), "f"}, {bundle.get("x_i"), "x_i"}, {bundle.get("xi"), "xi"}, {bundle.get("error"), "ep"}});
                    break;
            }
            VisTextButton accept = tmp.getAcceptButton();
            accept.addListener(new AcceptButtonListener(type, tmp));
            if (tmp != null)
                mainScreen.getStage().addActor(tmp.fadeIn());
        }
    }

    private class AcceptButtonListener extends ClickListener {

        private final ActionType action;
        private InputWindow window;

        public AcceptButtonListener(ActionType action, InputWindow window){
            this.action = action;
            this.window = window;
        }

        @Override
        public void clicked(InputEvent event, float x, float y) {
            switch (action){
                case SECANT:
                case FIXED_POINT:
                case NR:
                case REGULI:
                case BISECTION:
                    mainScreen.getTabbedPane().add(new MethodModule(action, window));
                    window.fadeOut();
                    break;
            }
        }
    }
}
