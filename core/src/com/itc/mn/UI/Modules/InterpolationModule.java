package com.itc.mn.UI.Modules;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.itc.mn.Methods.Interpolation;
import com.itc.mn.Things.Const;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisSelectBox;
import com.kotcrab.vis.ui.widget.VisTextArea;
import com.kotcrab.vis.ui.widget.VisTextField;
import com.kotcrab.vis.ui.widget.tabbedpane.Tab;

import java.util.ArrayList;

public class InterpolationModule extends Tab {

    private Interpolation interpolation;
    private final Table content;
    // UI Things
    private VisSelectBox<String> type;
    private VisTextArea x_input, y_input;

    public InterpolationModule(){
        content = new Table();
        content.setBackground(VisUI.getSkin().getDrawable("menu-bg"));
        content.setFillParent(true);
        // We instantiate the processing part
        interpolation = new Interpolation();
        // Now we build the UI
        buildUI();
        content.left().top();
    }

    private void buildUI(){
        // We define the selector
        type = new VisSelectBox<String>();
        type.setItems(new String[]{Const.getBundleString("lineal"), Const.getBundleString("quadratic")});
        // Create labels
        VisLabel l_mode = new VisLabel(Const.getBundleString("mode"));
        // Create text areas
        x_input = new VisTextArea();
        x_input.setMessageText("x");
        y_input = new VisTextArea();
        y_input.setMessageText("y");
        // Add listeners
        x_input.setTextFieldFilter(new InputValidator());
        y_input.setTextFieldFilter(new InputValidator());
        // Add stuff to GUI
        content.add(l_mode).top().left().pad(5f);
        content.add(type).top().left().pad(5f).row();
        content.add(x_input).left().expandY().pad(5f);
        content.add(y_input).left().expandY().pad(5f).row();
    }

    @Override
    public String getTabTitle() {
        return Const.getBundleString("interpolation");
    }

    @Override
    public Table getContentTable() {
        return content;
    }

    private class InputValidator implements VisTextField.TextFieldFilter {

        @Override
        public boolean acceptChar(VisTextField visTextField, char c) {
            System.out.println(visTextField.getText()+c);
            return (visTextField.getText()+c).matches("[-|+]?[0-9]*\\.?[0-9]*");
        }
    }
}
