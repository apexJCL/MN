package com.itc.mn.UI.Modules;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.itc.mn.Methods.StatisticParser;
import com.itc.mn.Structures.GraphingData;
import com.itc.mn.Things.Const;
import com.itc.mn.UI.MainScreen;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.*;
import com.kotcrab.vis.ui.widget.file.FileChooser;
import com.kotcrab.vis.ui.widget.file.FileChooserAdapter;
import com.kotcrab.vis.ui.widget.tabbedpane.Tab;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class StatisticsModule extends Tab {

    private CustomTable content;
    private FileChooser fileChooser;
    private Table controlPane;
    private VisTextArea input, frequency;
    private VisLabel value, mode_s, o_mode_s, mean, o_mean, classWidth, o_classWidth, mode, classesamount, datanumber, o_datanumber, varianze, stdev, o_varianze, o_stdev;
    private VisSelectBox<Integer> classes;
    private VisSelectBox<String> modeSelector;
    private StatisticParser statisticParser;
    // To show the values loaded
    private VisScrollPane listScroller;
    private VisTable valuesHolder;
    private GraphingData data;
    private MainScreen screen;

    /**
     * This module holds a "pane" with basic content and a render portion, so it can render a graphic
     * @param mainScreen
     */
    public StatisticsModule(MainScreen mainScreen){
        // To update our data
        screen = mainScreen;
        // Define our table
        content = new CustomTable(VisUI.getSkin(), data);
        // Setup the control panel
        controlPane = new Table(VisUI.getSkin());
        // Create labels
        createLabels();
        // Adjust file chooser
        setupChooser();
        // Setup SelectBox listener
        setupSelecBoxes();
        // BUild UI
        buildUI();
        // Set the focus handler
        scrollFocus();
        // Set table background
        controlPane.setBackground(VisUI.getSkin().getDrawable("window-bg"));
        valuesHolder.setBackground(VisUI.getSkin().getDrawable("white"));
        valuesHolder.setColor(Color.GRAY);
        // Add the control pane to the content table
        content.add(controlPane).expand().fillY().left();
    }

    /**
     * Creates the labels... maybe
     */
    private void createLabels() {
        value = new VisLabel(Const.getBundleString("input_value_des"));
        mode = new VisLabel(Const.getBundleString("mode"));
        mode.setColor(Color.CYAN);
        mean = new VisLabel(Const.getBundleString("mean"));
        mode_s = new VisLabel(Const.getBundleString("mode_s"));
        classWidth = new VisLabel(Const.getBundleString("classwidth"));
        classesamount = new VisLabel(Const.getBundleString("classesamount"));
        classesamount.setColor(Color.CYAN);
        datanumber = new VisLabel(Const.getBundleString("datanumber"));
        varianze = new VisLabel(Const.getBundleString("varianze"));
        stdev = new VisLabel(Const.getBundleString("stdeviation"));
        // Setting output labels
        o_mean = new VisLabel(Const.getBundleString("notavailable"));
        o_mode_s = new VisLabel(Const.getBundleString("notavailable"));
        o_classWidth = new VisLabel(Const.getBundleString("notavailable"));
        o_datanumber = new VisLabel(Const.getBundleString("notavailable"));
        o_varianze = new VisLabel(Const.getBundleString("notavailable"));
        o_stdev =  new VisLabel(Const.getBundleString("notavailable"));
    }

    /**
     * It setups... well.. the fileChooser, so it performs as desired.
     */
    private void setupChooser(){
        fileChooser = new FileChooser(new FileHandle(Gdx.files.getExternalStoragePath()), FileChooser.Mode.OPEN);
        fileChooser.setDirectory(Gdx.files.getExternalStoragePath());
        fileChooser.setModal(true);
        fileChooser.setMultiSelectionEnabled(false); // We disable multiselection
        fileChooser.setFileFilter(new FileChooser.DefaultFileFilter(fileChooser) {
            @Override
            public boolean accept(File f) {
                return f.getName().matches("(.*\\.txt*)") || f.isDirectory();
            }
        });
        fileChooser.setListener(new FileChooserAdapter() {
            @Override
            public void selected(FileHandle file) {
                try{
                    statisticParser = new StatisticParser(file);
                    statisticParser.setClasses(classes.getSelected());
                    fillValuesList(statisticParser.getValFreqList());
                    o_datanumber.setText(statisticParser.getDataAmount());
                    o_varianze.setText(statisticParser.getVarianze(getMode()));
                    o_stdev.setText(statisticParser.getStdDeviation(getMode()));
                    o_classWidth.setText(String.valueOf(statisticParser.getClassWidth(classes.getSelected())));
                    o_mean.setText(statisticParser.getMean());
                    o_mode_s.setText(statisticParser.getMode());
                    statisticParser.getMedian();
                    data = statisticParser.getGraphingData();
                    refreshData();
                }
                catch (Exception e){}
            }
        });
    }

    private void scrollFocus(){
        listScroller.addListener(new InputListener(){
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                listScroller.getStage().setScrollFocus(listScroller);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                listScroller.getStage().unfocus(listScroller);
            }
        });
    }

    private StatisticParser.MODE getMode() {
        if(modeSelector.getSelected().equals(Const.getBundleString("demographic")))
            return StatisticParser.MODE.DEMOGRAPHIC;
        else
            return StatisticParser.MODE.SAMPLE;
    }

    /**
     * It.. well... builds UI?
     */
    private void buildUI(){
        // Create values holder
        valuesHolder = new VisTable();
        valuesHolder.setTouchable(Touchable.childrenOnly);
        valuesHolder.left().top();
        listScroller = new VisScrollPane(valuesHolder);
        // Define the open button
        VisTextButton showChooser = new VisTextButton("Open");
        showChooser.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                content.getStage().addActor(fileChooser.fadeIn());
            }
        });
        // Define the add value button and the widgets that manage user input for values
        VisTextButton addvalue = new VisTextButton(Const.getBundleString("insert"));
        input = new VisTextArea();
        input.setTextFieldFilter(new VisTextField.TextFieldFilter() {
            @Override
            public boolean acceptChar(VisTextField textField, char c) {
                if((textField.getText()+c).matches("[\\+|\\-]?[0-9]+\\.?[0-9]*")) {
                    textField.setColor(Color.WHITE);
                    return true;
                }
                else{
                    textField.setColor(Color.RED);
                    return false;
                }
            }
        });
        input.setMessageText(Const.getBundleString("value"));
        frequency = new VisTextArea();
        frequency.setMessageText(Const.getBundleString("freq"));
        frequency.setTextFieldFilter(new VisTextField.TextFieldFilter() {
            @Override
            public boolean acceptChar(VisTextField textField, char c) {
                if((textField.getText()+c).matches("[1-9]+[0-9]*")){
                    textField.setColor(Color.WHITE);
                    return true;
                }
                textField.setColor(Color.RED);
                return false;
            }
        });
        // Setting up things
        controlPane.top().left().pad(5);
        controlPane.add(new VisLabel(Const.loadBundle().get("load_file_description"))).left().top();
        controlPane.add(showChooser).padTop(5).right().padBottom(10).row();
        // Add the manual input loader
        controlPane.add(value).left().row();
        controlPane.add(input).left().pad(3f);
        controlPane.add(frequency).left().pad(3f).row();
        controlPane.add(addvalue).right().colspan(2).pad(3f).row();
        // Adding SelectBoxes
        controlPane.add(mode).left().padRight(5);
        controlPane.add(modeSelector).right().padBottom(5f).row();
        controlPane.add(classesamount).left().padRight(5);
        controlPane.add(classes).right().row();
        // Adding labels
        controlPane.add(classWidth).left();
        controlPane.add(o_classWidth).right().row();
        controlPane.add(datanumber).left();
        controlPane.add(o_datanumber).right().row();
        // HEre goes basic output stuff
        controlPane.add(mean).left().padRight(3);
        controlPane.add(o_mean).right().row();
        controlPane.add(mode_s).left().padRight(3);
        controlPane.add(o_mode_s).right().row();
        controlPane.add(varianze).left().padRight(3);
        controlPane.add(o_varianze).right().row();
        controlPane.add(stdev).left().padRight(3);
        controlPane.add(o_stdev).right().row();
        controlPane.add(listScroller).left().colspan(2).fill().expand();
    }

    /**
     * It fills the list with values and frequency data
     * @param array Array of data
     */
    private void fillValuesList(ArrayList<Double[]> array){
        valuesHolder.clearChildren();
        VisLabel values = new VisLabel(Const.getBundleString("values"));
        VisLabel freq = new VisLabel(Const.getBundleString("freq"));
        values.setColor(Color.CYAN);
        freq.setColor(Color.CYAN);
        valuesHolder.add(values).left().padRight(10f).padLeft(5);
        valuesHolder.add(freq).left().row();
        Iterator iterator = array.iterator();
        while (iterator.hasNext()){
            Double[] tmp = (Double[]) iterator.next();
            valuesHolder.add(String.valueOf(tmp[0]));
            valuesHolder.add(String.valueOf(tmp[1])).row();
        }
    }

    /**
     * It defines the behaviour of the selectboxes
     */
    private void setupSelecBoxes() {
        // Define statistic mode
        modeSelector = new VisSelectBox<String>();
        String[] modes = {Const.getBundleString("sample"), Const.getBundleString("demographic")};
        modeSelector.setItems(modes);
        modeSelector.setColor(Color.CYAN);
        // Define the classes amount selector
        classes = new VisSelectBox<Integer>();
        Integer[] items = {5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20};
        classes.setItems(items);
        classes.setColor(Color.CYAN);
        classes.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(statisticParser != null){
                    statisticParser.setClasses(classes.getSelected());
                    o_classWidth.setText(String.valueOf(statisticParser.getClassWidth(classes.getSelected())));
                    refreshData();
                }
            }
        });

        modeSelector.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(statisticParser != null) {

                }
            }
        });
    }

    private void refreshData() {
        statisticParser.refreshData();
        data = statisticParser.getGraphingData();
        try{
            screen.refreshStatisticData(data);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public String getTabTitle() {
        return Const.loadBundle().get("statistic");
    }

    @Override
    public Table getContentTable() {
        return content;
    }

    public class CustomTable extends Table{

        private GraphingData data;

        public CustomTable(Skin skin, GraphingData data) {
            super(skin);
            this.data = data;
        }

        public GraphingData getData(){
            return refreshData();
        }

        public GraphingData refreshData(){
            try{
                statisticParser.refreshData();
                this.data = statisticParser.getGraphingData();
                return this.data;
            }
            catch (Exception e){
                return null;
            }
        }
    }
}