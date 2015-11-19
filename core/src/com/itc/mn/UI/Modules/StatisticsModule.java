package com.itc.mn.UI.Modules;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.itc.mn.Methods.StatisticParser;
import com.itc.mn.Things.Const;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.building.StandardTableBuilder;
import com.kotcrab.vis.ui.building.utilities.Padding;
import com.kotcrab.vis.ui.widget.*;
import com.kotcrab.vis.ui.widget.file.FileChooser;
import com.kotcrab.vis.ui.widget.file.FileChooserAdapter;
import com.kotcrab.vis.ui.widget.tabbedpane.Tab;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class StatisticsModule extends Tab {

    private Table content;
    private StandardTableBuilder builder;
    private String file;
    private FileChooser fileChooser;
    private Table controlPane;
    private VisLabel classesamount, datanumber, o_datanumber, varianze, stdev, o_varianze, o_stdev;
    private VisSelectBox<Integer> classes;
    private StatisticParser statisticParser;
    // To show the values loaded
    private VisScrollPane listScroller;
    private VisTable valuesHolder;

    public StatisticsModule(){
        // Define our table
        content = new VisTable();
        // Setup the control panel
        controlPane = new Table(VisUI.getSkin());
        // Create labels
        createLabels();
        // Adjust file chooser
        setupChooser();
        // BUild UI
        buildUI();
        // Set table background
        controlPane.setBackground(VisUI.getSkin().getDrawable("window-bg"));
        // Add the control pane to the content table
        content.add(controlPane).expand().fillY().left();
    }

    private void createLabels() {
        classesamount = new VisLabel(Const.getBundleString("classesamount"));
        classesamount.setColor(Color.CYAN);
        datanumber = new VisLabel(Const.getBundleString("datanumber"));
        varianze = new VisLabel(Const.getBundleString("varianze"));
        stdev = new VisLabel(Const.getBundleString("stdeviation"));
        o_datanumber = new VisLabel(Const.getBundleString("notavailable"));
        o_varianze = new VisLabel(Const.getBundleString("notavailable"));
        o_stdev =  new VisLabel(Const.getBundleString("notavailable"));
    }

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
                statisticParser = new StatisticParser(file);
                fillValuesList(statisticParser.getValFreqList());
            }
        });
    }

    private void buildUI(){
        // Create values holder
        valuesHolder = new VisTable();
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
        // Define the classes amount selector
        classes = new VisSelectBox<Integer>();
        Integer[] items = {5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20};
        classes.setItems(items);
        classes.setColor(Color.CYAN);
        // Setting up things
        controlPane.top().left().pad(5);
        controlPane.add(new VisLabel(Const.loadBundle().get("load_file_description"))).left().top().colspan(2).row();
        controlPane.add(showChooser).padTop(5).right().padBottom(10).colspan(2).row();
        // Adding labels
        controlPane.add(classesamount).left().padRight(5);
        controlPane.add(classes).left().row();
        controlPane.add(datanumber).left();
        controlPane.add(o_datanumber).left().row();
        controlPane.add(varianze).left();
        controlPane.add(o_varianze).left().row();
        controlPane.add(stdev).left();
        controlPane.add(o_stdev).left().row();
        controlPane.add(listScroller).left().colspan(2).fill().expand();
    }

    private void fillValuesList(ArrayList<Double[]> array){
        valuesHolder.add(Const.getBundleString("values")).left().padRight(10f);
        valuesHolder.add(Const.getBundleString("freq")).left().row();
        Iterator iterator = array.iterator();
        while (iterator.hasNext()){
            Double[] tmp = (Double[]) iterator.next();
            valuesHolder.add(String.valueOf(tmp[0]));
            valuesHolder.add(String.valueOf(tmp[1])).row();
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
}
