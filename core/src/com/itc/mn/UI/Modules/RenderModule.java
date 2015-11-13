package com.itc.mn.UI.Modules;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.tabbedpane.Tab;

/**
 * RenderModule it's just a blank tab, no background, that lets the view to render under the hood.
 * We implement a custom Table class with just an extra paremeter, to toggle renderization under the GUI
 */
public class RenderModule extends Tab{

    private final double[][] values;
    private CustomTable content = new CustomTable();

    public RenderModule(double[][] values){
        this.values = values;
    }

    public double[][] getValues(){ return values; }

    @Override
    public String getTabTitle() {
        return "Render Screen";
    }

    @Override
    public Table getContentTable() {
        return content;
    }

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
