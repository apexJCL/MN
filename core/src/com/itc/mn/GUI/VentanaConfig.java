package com.itc.mn.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.itc.mn.Cosas.Const;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisSlider;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.VisWindow;

/**
 * This is the configuration window, it will manage all the basic settings of the app.
 */
public class VentanaConfig extends VisWindow {

    private VisSlider decimales;
    private VisLabel l_decimales, l_decimales_valor;
    private VisTextButton aceptar, cancelar;

    public VentanaConfig(FrontEnd gui) {
        super("Ajustes");
        // Default config
        closeOnEscape();
        addCloseButton();
        construct(gui);
        pack();
    }

    private VisWindow getWindow(){ return this; }

    private void construct(final FrontEnd gui){
        // Load the decimal amount
        String dec = gui.getConfig().getFormat().substring(gui.getConfig().getFormat().indexOf('.')+1);
        // Initialize tags
        l_decimales = new VisLabel("Decimales");
        decimales = new VisSlider(1, 8, 1, false);
        // Set the current value
        decimales.setValue(dec.length());
        l_decimales_valor = new VisLabel(String.valueOf((int)decimales.getValue()));
        // Buttons
        aceptar = new VisTextButton("Aceptar");
        cancelar = new VisTextButton("Cancelar");
        // Adjust listeners
        decimales.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                l_decimales_valor.setText(String.valueOf((int)decimales.getValue()));
            }
        });
        aceptar.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String format = "#.";
                for(int i = 0; i < decimales.getValue(); i++)
                    format+="#";
                Const c = new Const();
                c.setFormat(format);
                gui.setConfig(c);
                fadeOut();
            }
        });
        cancelar.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                getWindow().fadeOut();
            }
        });
        // Adding to Window
        add(l_decimales).pad(3f).left();
        add(decimales).pad(1f).left().expandX();
        add(l_decimales_valor).pad(1f).left().row();
        add(cancelar).right().pad(1f);
        add(aceptar).right().pad(1f).row();
    }

    @Override
    public VisWindow fadeIn() {
        setPosition((Gdx.graphics.getWidth()-getWidth())/2f, (Gdx.graphics.getHeight()-getHeight())/2f);
        return super.fadeIn();
    }
}
