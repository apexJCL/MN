package com.itc.mn.GUI;

import com.badlogic.gdx.Gdx;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisWindow;

/**
 * Created by zero_ on 09/10/2015.
 */
public class VentanaConfig extends VisWindow {

    private static final VisLabel mensaje = new VisLabel("Esperalo.");

    public VentanaConfig() {
        super("Configuracion");
        closeOnEscape();
        addCloseButton();
        add(mensaje).center().expand();
        pack();
        setSize(getWidth() * 2f, getHeight());
        setPosition((Gdx.graphics.getWidth() - getWidth()) / 2f, (Gdx.graphics.getHeight() - getHeight()) / 2f);
    }
}
