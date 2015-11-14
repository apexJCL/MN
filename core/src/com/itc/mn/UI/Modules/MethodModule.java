package com.itc.mn.UI.Modules;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.itc.mn.Methods.Bisection;
import com.itc.mn.Methods.Method;
import com.itc.mn.UI.GlobalMenu;
import com.itc.mn.UI.Windows.InputWindow;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.tabbedpane.Tab;

/**
 * This module contains the things needed by methods to execute and deliver.
 * It basically links the visual and logic.
 * Visually it provides a transparent Tab so the rendering system can be seen
 */
public class MethodModule extends Tab{

    private Method method;
    private final InputWindow window;
    private double[][] values = null;
    private final GlobalMenu.ActionType action;
    private CustomTable content = new CustomTable();

    public MethodModule(GlobalMenu.ActionType action, InputWindow window){
        this.action = action;
        this.window = window;
        try{
            LoadMethod();
        }
        catch (Exception e){ e.printStackTrace(); }
    }

    private void LoadMethod()throws Exception{
        switch (action){
            case BISECTION:
                method = new Bisection(window.getVariable("f"), Double.parseDouble(window.getVariable("a")), Double.parseDouble(window.getVariable("b")), Double.parseDouble(window.getVariable("ep")));
                values = method.getRange();
                break;
        }
    }

    /**
     * Returns the values from the method executed
     * @return
     */
    public double[][] getValues(){ return values; }

    /**
     * Returns the tab title
     * @return
     */
    @Override
    public String getTabTitle() {
        return "Render Screen";
    }

    /**
     * Returns the table content for the VisTabbedPane
     * @return
     */
    @Override
    public Table getContentTable() {
        return content;
    }

    public double getRoot() {
        return method.getRoot();
    }

    /**
     * It's a normal Table, just with an extra flag that enables the rendering on the upper controlling system
     */
    public class CustomTable extends Table{

        boolean allowRender = true;

        public CustomTable(){
            super(VisUI.getSkin());
        }

        public boolean enableRender(){
            return allowRender;
        }

    }
}
