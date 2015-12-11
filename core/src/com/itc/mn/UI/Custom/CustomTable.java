package com.itc.mn.UI.Custom;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.itc.mn.UI.MainScreen;
import com.kotcrab.vis.ui.VisUI;

/**
 * It's a normal CustomTable, just with an extra flag that enables the rendering on the upper controlling system
 */

public class CustomTable extends Table{

    public MainScreen.RenderType type;

    public CustomTable(MainScreen.RenderType type){
        super(VisUI.getSkin());
        this.type = type;
    }
}
