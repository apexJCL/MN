package com.itc.mn.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.itc.mn.Things.Results;
import com.itc.mn.Methods.Metodo;
import com.kotcrab.vis.ui.widget.VisScrollPane;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisWindow;

import java.text.DecimalFormat;

public class TablaResultados extends VisWindow {

    private Metodo metodo;
    private VisTable innerTable;
    private VisScrollPane pane;


    public TablaResultados(Metodo metodo, FrontEnd gui) {
        super(metodo.getTitulo());
        this.metodo = metodo;
        // Inicializamos la tabla interna para los valores
        innerTable = new VisTable();
        closeOnEscape();
        addCloseButton();
        buildTable(gui.getConfig().getFormat());
        pane = new VisScrollPane(innerTable);
        add(pane).expand().fill();
        setResizable(true);
        setResizeBorder(10);
        pane.pack();
    }

    public TablaResultados(Results res, FrontEnd gui) {
        super(res.getTitulo());
        this.metodo = new Metodo(res);
        innerTable = new VisTable();
        closeOnEscape();
        addCloseButton();
        buildTable(gui.getConfig().getFormat());
        pane = new VisScrollPane(innerTable);
        add(pane).expand().fill();
        setResizable(true);
        setResizeBorder(10);
        pane.pack();
        pane.setScrollbarsOnTop(false);
    }

    public void show(Stage stage) {
        if (!stage.getActors().contains(this, true)) {
            stage.addActor(this);
            fadeIn();
            if (pane.getWidth() < Gdx.graphics.getWidth())
                setSize(pane.getWidth() * 1.2f, Gdx.graphics.getHeight() * 0.2f);
            else
                setSize(pane.getWidth() * 0.8f, Gdx.graphics.getHeight() * 0.2f);
            setPosition((Gdx.graphics.getWidth() - getWidth()) / 2f, (Gdx.graphics.getHeight() - getHeight()) / 2f);
        } else {
            addAction(Actions.sequence(Actions.color(new Color(0, 0.819f, 1, 1), 0.2f), Actions.color(Color.WHITE, 0.2f)));
        }
    }

    private void buildTable(String format) {
        for (String s : metodo.getEncabezados())
            innerTable.add(s).left().expandX().pad(5f);
        innerTable.row();
        for (double[] valores : metodo.getResultados()) {
            for (double valor : valores)
                innerTable.add(new DecimalFormat(format).format(valor)).center().padRight(5f).expandX();
            innerTable.row();
        }
    }
}
