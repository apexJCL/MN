package com.itc.mn.UI.Modules;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.itc.mn.Methods.Bisection;
import com.itc.mn.Methods.Method;
import com.itc.mn.Methods.PFijo;
import com.itc.mn.Methods.ReglaFalsa;
import com.itc.mn.Things.Const;
import com.itc.mn.UI.GlobalMenu;
import com.itc.mn.UI.Windows.InputWindow;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisScrollPane;
import com.kotcrab.vis.ui.widget.VisSplitPane;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.tabbedpane.Tab;

import java.text.DecimalFormat;

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
    private CustomTable content;
    private VisTable transparentPane, resultPane;
    private VisSplitPane splitPane;
    private VisScrollPane resultScrollPane;
    private String format = Const.Load().format;

    public MethodModule(GlobalMenu.ActionType action, InputWindow window){
        this.action = action;
        this.window = window;
        buildUI();
        try{
            LoadMethod();
        }
        catch (Exception e){ e.printStackTrace(); }
    }

    private void buildUI() {
        // We create our holder for the split pane, also it holds a flag to indicate rendering must be enabled
        content = new CustomTable();
        transparentPane = new VisTable();
        resultPane = new VisTable();
        // Create the scrollable pane so it can show values and move along the list
        resultScrollPane = new VisScrollPane(resultPane);
        // We create the splitpane so it holds the two tables with a vertical tab slider
        splitPane = new VisSplitPane(transparentPane, resultScrollPane, false);
        // We set the resultPane with a background so it shows all
        resultPane.setBackground(VisUI.getSkin().getDrawable("window-bg"));
        // Add the splitpane to the content table
        content.add(splitPane).expand().fill();
    }

    private void LoadMethod()throws Exception{
        switch (action){
            case BISECTION:
                method = new Bisection(window.getVariable("f"), Double.parseDouble(window.getVariable("a")+"d"), Double.parseDouble(window.getVariable("b")+"d"), Double.parseDouble(window.getVariable("ep")+"d"));
                values = method.getRange();
                break;
            case REGULI:
                method = new ReglaFalsa(window.getVariable("f"), Double.parseDouble(window.getVariable("a")+"d"), Double.parseDouble(window.getVariable("b")+"d"), Double.parseDouble(window.getVariable("ep")+"d"));
                values = method.getRange();
                break;
            case FIXED_POINT:
                method = new PFijo(window.getVariable("f_1"), window.getVariable("f_2"), Double.parseDouble(window.getVariable("initial")+"d"), Double.parseDouble(window.getVariable("ep")+"d"));
                break;
            case NR:
                break;
            case SECANT:
                break;
        }
        buildResuts(method);
    }

    private void buildResuts(Method method){
        for(String s: method.getTitles())
            resultPane.add(s).left().expandX().pad(5f);
        resultPane.row();
        for (double[] values: method.getResultados()){
            for(double value: values)
                resultPane.add(new DecimalFormat(format).format(value)).center().padRight(5f).expandX();
            resultPane.row();
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
        return method.getTabTitle();
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
