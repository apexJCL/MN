package com.itc.mn.GUI;

import com.badlogic.gdx.Gdx;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisWindow;

/**
 * Created by zero_ on 09/10/2015.
 */
public class VentanaMensajes extends VisWindow {

    private final VisLabel mensaje;

    public VentanaMensajes(String title, String contenido) {
        super(title);
        mensaje = new VisLabel(contenido);
        closeOnEscape();
        addCloseButton();
        add(mensaje).center().expand().pad(5f);
        pack();
        setPosition((Gdx.graphics.getWidth() - getWidth()) / 2f, (Gdx.graphics.getHeight() - getHeight()) / 2f);
    }
}
