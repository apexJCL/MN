package com.itc.mn.Cosas;

import com.badlogic.gdx.Gdx;
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
        super(metodo.getTipo() +" | f(x): "+ metodo.getFuncion() +" | ep: "+metodo.get_errorporcentual() + " | Raiz: "+metodo.getRaices()[0]);
        this.metodo = metodo;
        // Inicializamos la tabla interna para los valores
        innerTable = new VisTable();
        closeOnEscape();
        addCloseButton();
        buildTable();
        pane = new VisScrollPane(innerTable);
        add(pane).expand().fill();
    }

    @Override
    protected void close() {
        setVisible(false);

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
            for (double valor : valores) innerTable.add(new DecimalFormat("#.#######").format(valor)).left().expandX();
            innerTable.row();
        }
        setSize(Gdx.graphics.getWidth()*0.7f, Gdx.graphics.getHeight()*0.5f);
        setPosition((Gdx.graphics.getWidth() - getWidth()) / 2f, (Gdx.graphics.getHeight() - getHeight()) / 2f);
    }

    @Override
    public VisWindow fadeIn() {
        return super.fadeIn();
    }
}
