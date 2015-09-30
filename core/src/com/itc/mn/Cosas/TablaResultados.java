package com.itc.mn.Cosas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
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
        super(metodo.getTipo() +" | f(x): "+ metodo.getFuncion() +" | ep: "+metodo.get_errorporcentual() + " | Raiz: "+metodo.getRaiz());
        this.metodo = metodo;
        // Inicializamos la tabla interna para los valores
        innerTable = new VisTable();
        closeOnEscape();
        addCloseButton();
        buildTable();
        pane = new VisScrollPane(innerTable);
        add(pane).expand().fill();
    }

    public void show(Stage stage){
        if(!stage.getActors().contains(this, true)) {
            stage.addActor(this);
            fadeIn();
        }
    }

    private void buildTable(){
        switch (metodo.tipo){
            case PUNTO_FIJO:
                innerTable.add("Iteracion").center().expandX().pad(5f);
                innerTable.add("x").center().expandX().pad(5f);
                innerTable.add("g(x)").center().expandX().pad(5f);
                innerTable.add("ep").center().expandX().pad(5f);
                break;
            case BISECCION:
                break;
        }
        innerTable.row();
        for (double[] valores : metodo.getResultados()) {
            for (double valor : valores) innerTable.add(new DecimalFormat("#.########").format(valor)).left().expandX();
            innerTable.row();
        }
        setSize(Gdx.graphics.getWidth()*0.7f, Gdx.graphics.getHeight()*0.5f);
        setPosition((Gdx.graphics.getWidth() - getWidth()) / 2f, (Gdx.graphics.getHeight() - getHeight()) / 2f);
    }
}
