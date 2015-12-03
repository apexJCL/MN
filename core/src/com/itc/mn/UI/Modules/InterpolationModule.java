package com.itc.mn.UI.Modules;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.itc.mn.Methods.Interpolation;
import com.itc.mn.Things.Const;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisSelectBox;
import com.kotcrab.vis.ui.widget.tabbedpane.Tab;

public class InterpolationModule extends Tab {

    private final Table content;
    private Interpolation interpolation;
    // UI Things
    private VisSelectBox<String> type;

    public InterpolationModule(){
        content = new Table();
        content.setBackground(VisUI.getSkin().getDrawable("menu-bg"));
        content.setFillParent(true);
        // We instantiate the processing part
        interpolation = new Interpolation();
        // Now we build the UI
        buildUI();
        content.setDebug(true);
    }

    private void buildUI(){
        // We define the selector
        type = new VisSelectBox<String>();
        type.setItems(new String[]{Const.getBundleString("lineal"), Const.getBundleString("quadratic")});
        // Create labels
        VisLabel l_mode = new VisLabel(Const.getBundleString("mode"));
        // Add stuff to GUI
        content.add(l_mode).expandY().top().left();
        content.add(type).expandY().top().left();
    }

    @Override
    public String getTabTitle() {
        return Const.getBundleString("interpolation");
    }

    @Override
    public Table getContentTable() {
        return content;
    }
}
