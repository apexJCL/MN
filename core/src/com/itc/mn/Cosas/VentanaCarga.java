package com.itc.mn.Cosas;

import com.badlogic.gdx.Gdx;
import com.kotcrab.vis.ui.widget.VisWindow;

/**
 * Created by zero_ on 05/10/2015.
 */
public class VentanaCarga extends VisWindow {

    private static loadingIcon li = new loadingIcon();

    public VentanaCarga() {
        super("");
        add(li).expand().pad(10f);
        setSize(li.getWidth()*1.2f, li.getHeight()*0.5f);
        setPosition((Gdx.graphics.getWidth()-getWidth())/2f, (Gdx.graphics.getHeight()-getHeight())/2f);
        pack();
    }
}
