package com.itc.mn.UI.Modules;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.itc.mn.Methods.Interpolation;
import com.itc.mn.Structures.Lists.XYList;
import com.itc.mn.Structures.XYHolder;
import com.itc.mn.Things.Const;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.*;
import com.kotcrab.vis.ui.widget.tabbedpane.Tab;

public class InterpolationModule extends Tab {

    private static Interpolation interpolation;
    private final Table content;
    private Table toolHolder;
    private static XYList list;
    // UI Things
    private VisTextButton b_add, interpolate;
    private static VisTextArea x_input;
    private static VisTextArea y_input;
    private static VisSelectBox<String> type;
    private static VisList x_list;
    private static VisList y_list;

    public InterpolationModule(){
        // This table holds the GUI tools
        toolHolder = new Table();
        // This table holds the tools and the data list that will show the elements
        content = new Table();
        content.setBackground(VisUI.getSkin().getDrawable("window-bg"));
        content.setFillParent(true);
        // We instantiate the processing part
        interpolation = new Interpolation();
        // List to hold values
        list = new XYList();
        // Now we build the UI
        buildUI();
        content.left().top();
    }

    private void buildUI(){
        // We define the selector
        type = new VisSelectBox<String>();
        type.setItems(Const.getBundleString("lineal"), Const.getBundleString("quadratic"));
        // Create labels
        VisLabel l_mode = new VisLabel(Const.getBundleString("mode"));
        // Create text areas
        x_input = new VisTextArea();
        x_input.setMessageText("x");
        y_input = new VisTextArea();
        y_input.setMessageText("y");
        // create buttons
        b_add = new VisTextButton(Const.getBundleString("add_value"));
        interpolate = new VisTextButton(Const.getBundleString("interpolate"));
        // add listener
        b_add.addListener(new Buttonlistener(Buttonlistener.Action.ADD));
        interpolate.addListener(new Buttonlistener(Buttonlistener.Action.INTERPOLATE));
        // Create value list holder
        x_list = new VisList();
        y_list = new VisList();
        // Add listeners
        x_input.setTextFieldFilter(new InputValidator());
        y_input.setTextFieldFilter(new InputValidator());
        // Add stuff to GUI
        toolHolder.add(l_mode).top().left().pad(2f);
        toolHolder.add(type).top().left().pad(2f).row();
        toolHolder.add(x_input).left().pad(2f);
        toolHolder.add(y_input).left().pad(2f);
        toolHolder.add(b_add).left().pad(2f);
        toolHolder.add(interpolate).center().pad(5f).row();
        // Adding the container to the screen
        content.add(toolHolder).top().left().colspan(2).row();
        content.add(x_list).left().expand().fill();
        content.add(y_list).left().expand().fill();
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
            return (visTextField.getText()+c).matches("[-|+]?[0-9]*\\.?[0-9]*");
        }
    }

    private static class Buttonlistener extends ClickListener {

        private Action action;

        public Buttonlistener(Action action){
            this.action = action;
        }

        public enum Action{
            ADD, INTERPOLATE
        }


        @Override
        public void clicked(InputEvent event, float x, float y) {
            switch (action){
                case ADD:
                    if(x_input.getText().equals(""))
                        x_input.addAction(Actions.sequence(Actions.color(Color.RED, 0.4f), Actions.color(Color.WHITE, 0.4f)));
                    else{
                        if(y_input.getText().equals(""))
                            list.insert(Double.parseDouble(x_input.getText()), null);
                        else
                            list.insert(Double.parseDouble(x_input.getText()), Double.parseDouble(y_input.getText()));
                    }
                    setValues(list);
                    break;
                case INTERPOLATE:
                    if(type.getSelected().equals(Const.getBundleString("lineal"))) {
                        try {
                            XYList nodeXies = interpolation.l_interpolate_list(list);
                            setValues(nodeXies);
                        } catch (Exception e) {
                        }
                    }
                    else
                        try {
                            XYList nodeXies = interpolation.q_interpolate(list);
                            setValues(nodeXies);
                        } catch (Exception e) {
                        }
                    break;
            }
        }

        private void setValues(XYList list){
            try {
                XYHolder arrays = list.getArrays();
                x_list.setItems(arrays.x);
                y_list.setItems(arrays.y);
                x_list.setSelectedIndex(0);
                y_list.setSelectedIndex(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
