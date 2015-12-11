package com.itc.mn.UI.Modules;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.itc.mn.Methods.Regression;
import com.itc.mn.Structures.Lists.XYList;
import com.itc.mn.Structures.XYHolder;
import com.itc.mn.Things.Const;
import com.itc.mn.UI.Custom.CustomTable;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.*;
import com.kotcrab.vis.ui.widget.tabbedpane.Tab;

import static com.itc.mn.UI.MainScreen.RenderType.SINGLE_PLOT;

public class RegressionModule extends Tab {

    private static Regression regression;
    private final CustomTable content;
    private Table toolHolder;
    private Table dataOutput;
    private Table transparentPane;
    private Table mainPane;
    private VisSplitPane splitPane;
    // UI Things
    private VisTextButton b_add, accept;
    private static VisTextArea x_input;
    private static VisTextArea y_input;
    private static VisSelectBox<String> type;
    private static VisList x_list;
    private static VisList y_list;
    private static VisList xy_list;
    private static VisList x2_list;
    private static VisList yaax_list;
    private static VisList yiy_list;
    // Static labels
    private VisLabel y_function, a0, a1, r, x_avg, y_avg;
    // Output labels
    private static VisLabel o_y_function, o_a0, o_a1, o_r, o_x_avg, o_y_avg;

    public RegressionModule(){
        // Instantiate the transparent pane
        transparentPane = new Table();
        // The main pane holds the user stuff
        mainPane = new Table();
        // This table holds the GUI tools
        toolHolder = new Table();
        // This will hold the data and the labels
        dataOutput = new Table();
        // This table holds the tools and the data list that will show the elements
        content = new CustomTable(SINGLE_PLOT);
        content.setFillParent(true);
        // We instantiate the processing part
        regression = new Regression();
        // Now we build the UI
        buildUI();
        content.left().top();
    }

    private void buildUI(){
        // We define the selector
        type = new VisSelectBox<String>();
        type.setItems(Const.getBundleString("lineal"));
        // Create labels
        VisLabel l_mode = new VisLabel(Const.getBundleString("mode"));
        /* This labels are to show regression data */
        y_function = new VisLabel(Const.getBundleString("function"));
        a0 = new VisLabel("a0: ");
        a1 = new VisLabel("a1: ");
        r = new VisLabel("r: ");
        x_avg = new VisLabel(Const.getBundleString("x_avg"));
        y_avg = new VisLabel(Const.getBundleString("y_avg"));
        o_y_function = new VisLabel(Const.getBundleString("notavailable"));
        o_a0 = new VisLabel(Const.getBundleString("notavailable"));
        o_a1 = new VisLabel(Const.getBundleString("notavailable"));
        o_r = new VisLabel(Const.getBundleString("notavailable"));
        o_x_avg = new VisLabel(Const.getBundleString("notavailable"));
        o_y_avg = new VisLabel(Const.getBundleString("notavailable"));
        // Adding the labels to the dataOutput panel
        dataOutput.add(y_function).left().pad(5f);
        dataOutput.add(o_y_function).left().pad(5f);
        dataOutput.add(r).left().pad(5f);
        dataOutput.add(o_r).left().pad(5f).row();
        dataOutput.add(x_avg).left().pad(5f);
        dataOutput.add(o_x_avg).left().pad(5f);
        dataOutput.add(y_avg).left().pad(5f);
        dataOutput.add(o_y_avg).left().pad(5f).row();
        dataOutput.add(a0).left().pad(5f);
        dataOutput.add(o_a0).left().pad(5f);
        dataOutput.add(a1).left().pad(5f);
        dataOutput.add(o_a1).left().pad(5f).row();
        /* This labels are to show regression data */
        // Create text areas
        x_input = new VisTextArea();
        x_input.setMessageText("x");
        y_input = new VisTextArea();
        y_input.setMessageText("y");
        // create buttons
        b_add = new VisTextButton(Const.getBundleString("add_value"));
        accept = new VisTextButton(Const.getBundleString("accept"));
        // add listener
        b_add.addListener(new Buttonlistener(Buttonlistener.Action.ADD));
        accept.addListener(new Buttonlistener(Buttonlistener.Action.PROCESS));
        // Create value list holder
        x_list = new VisList();
        y_list = new VisList();
        xy_list = new VisList();
        x2_list = new VisList();
        yaax_list = new VisList();
        yiy_list = new VisList();
        // Add listeners
        x_input.setTextFieldFilter(new InputValidator());
        y_input.setTextFieldFilter(new InputValidator());
        // Add stuff to GUI
        toolHolder.add(l_mode).top().left().pad(2f);
        toolHolder.add(type).top().left().pad(2f).row();
        toolHolder.add(x_input).left().pad(2f);
        toolHolder.add(y_input).left().pad(2f);
        toolHolder.add(b_add).left().pad(2f);
        toolHolder.add(accept).center().pad(5f).row();
        // Adding the container to the screen
        mainPane.add(toolHolder).top().expandX().left().pad(5f).colspan(5);
        mainPane.add(dataOutput).top().expandX().left().pad(5f).row();
        // This part will hold all the table stuff
        mainPane.add(x_list).left().expandY().fill().pad(5f);
        mainPane.add(y_list).left().expandY().fill().pad(5f);
        mainPane.add(xy_list).left().expandY().fill().pad(5f);
        mainPane.add(x2_list).left().expandY().fill().pad(5f);
        mainPane.add(yaax_list).left().expandY().fill().pad(5f);
        mainPane.add(yiy_list).left().expandY().fill().pad(5f);
        // Set the mainPane background
        mainPane.setBackground(VisUI.getSkin().getDrawable("window-bg"));
        // Create the splitpane
        splitPane = new VisSplitPane(mainPane, transparentPane, false);
        // We add the splitpane
        content.add(splitPane).center().fill().expand();
        // Set the splitPane to 90% open in the data part
        splitPane.setSplitAmount(0.9f);
        //content.setDebug(true, true);
    }

    @Override
    public String getTabTitle() {
        return Const.getBundleString("regression");
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
            ADD, PROCESS
        }


        @Override
        public void clicked(InputEvent event, float x, float y) {
            switch (action){
                case ADD:
                    if(x_input.getText().equals("") || y_input.getText().equals("")) {
                        x_input.addAction(Actions.sequence(Actions.color(Color.RED, 0.4f), Actions.color(Color.WHITE, 0.4f)));
                        y_input.addAction(Actions.sequence(Actions.color(Color.RED, 0.4f), Actions.color(Color.WHITE, 0.4f)));
                    }
                    else{
                        try {
                            regression.addValues(Double.parseDouble(x_input.getText()), Double.parseDouble(y_input.getText()));
                            XYHolder arrays = regression.getList().getArrays();
                            x_list.setItems(arrays.x);
                            y_list.setItems(arrays.y);
                        } catch (Exception e) {

                        }
                        x_input.setText("");
                        y_input.setText("");
                    }
                    break;
                case PROCESS:
                    try {
                        // Now we calculate
                        regression.calculateRegression();
                        // Then we add the values to display on screen
                        xy_list.setItems(regression.getXy());
                        x2_list.setItems(regression.getX2());
                        yaax_list.setItems(regression.getYaax());
                        yiy_list.setItems(regression.getYiy());
                        // Labels
                        o_y_function.setText("y = "+regression.getA0()+"+"+regression.getA1()+"x");
                        o_r.setText(String.valueOf(regression.getR()));
                        o_x_avg.setText(String.valueOf(regression.getX_average()));
                        o_y_avg.setText(String.valueOf(regression.getY_average()));
                        o_a0.setText(String.valueOf(regression.getA0()));
                        o_a1.setText(String.valueOf(regression.getA1()));
                    }
                    catch (Exception e){

                    }
                    break;
            }
        }
    }
}
