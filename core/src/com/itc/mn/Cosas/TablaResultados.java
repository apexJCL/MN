package com.itc.mn.Cosas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
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
        super(metodo.getTitulo());
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
        for(String s: metodo.getEncabezados())
            innerTable.add(s).left().expandX().pad(5f);
        innerTable.row();
        for (double[] valores : metodo.getResultados()) {
            for (double valor : valores) innerTable.add(new DecimalFormat("#.########").format(valor)).left().expandX();
            innerTable.row();
        }
        setSize(Gdx.graphics.getWidth()*0.8f, Gdx.graphics.getHeight()*0.5f);
        setPosition((Gdx.graphics.getWidth() - getWidth()) / 2f, (Gdx.graphics.getHeight() - getHeight()) / 2f);
    }
}
