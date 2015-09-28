package com.itc.mn.Cosas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.itc.mn.Metodos.Metodo;
import com.kotcrab.vis.ui.widget.VisScrollPane;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisWindow;

import java.text.DecimalFormat;


public class TablaResultados extends VisWindow {

    private Metodo metodo;
    private VisTable innerTable;
    private VisScrollPane pane;

    public TablaResultados(Metodo metodo) {
        super(metodo.getTipo() +" | "+ metodo.getFuncion());
        this.metodo = metodo;
        // Inicializamos la tabla interna para los valores
        innerTable = new VisTable();
        closeOnEscape();
        addCloseButton();
        buildTable();
        pane = new VisScrollPane(innerTable);
    }

    private void buildTable(){
        switch (metodo.tipo){
            case PUNTO_FIJO:
                innerTable.add("Iteracion").center().expandX();
                innerTable.add("x").center().expandX();
                innerTable.add("g(x)").center().expandX();
                innerTable.add("ep").center().expandX();
                break;
            case BISECCION:
                break;
        }
        row();
        for (double[] valores : metodo.getResultados()) {
            for (double valor : valores) innerTable.add(new DecimalFormat("#.######").format(valor)).left().expandX();
            row();
        }
        setSize(Gdx.graphics.getWidth()*0.7f, Gdx.graphics.getHeight()*0.5f);
        setPosition((Gdx.graphics.getWidth() - getWidth()) / 2f, (Gdx.graphics.getHeight() - getHeight()) / 2f);
    }

    @Override
    public VisWindow fadeIn() {
        return super.fadeIn();
    }
}
