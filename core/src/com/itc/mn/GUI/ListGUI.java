package com.itc.mn.GUI;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.I18NBundle;
import com.itc.mn.Structures.Lists.StatisticList;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.VisTextField;
import com.kotcrab.vis.ui.widget.VisWindow;

public class ListGUI extends VisWindow{

    // Logic
    private StatisticList list;
    // Visual
    private VisTextField value;
    private VisLabel l_value;
    private VisTextButton add, print;

    public ListGUI(I18NBundle bundle) {
        super(bundle.get("statistic"));
        list = new StatisticList();
        loadGUI();
        loadListener();
        setResizable(true);
        pack();
        centerWindow();
    }

    private void loadGUI(){
        // Instantiate
        value = new VisTextField();
        l_value = new VisLabel("Valor: ");
        add = new VisTextButton("Agregar");
        print = new VisTextButton("Imprimir");
        add(l_value).expandX().pad(5f);
        add(value).expandX().pad(5f).row();
        add(add).expandX().pad(5f);
        add(print).expandX().pad(5f).row();
    }

    private void loadListener(){
        add.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Adding value "+value.getText());
                list.insertNode(Double.parseDouble(value.getText() + "d"));
                System.out.println("Done!");
            }
        });
        print.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Printing list");
                for(Object node: list.getDoubleArray()){
                    for(int i = 0; i < ((double[])node).length; i++){
                        System.out.print(((double[])node)[i]+"\t");
                    }
                    System.out.println("--------");
                }
                System.out.println("END");
            }
        });
    }


}
