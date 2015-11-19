package com.itc.mn.UI.Modules;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.itc.mn.Screens.RenderScreen;
import com.itc.mn.Things.Const;
import com.itc.mn.Things.Results;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.building.StandardTableBuilder;
import com.kotcrab.vis.ui.building.utilities.Padding;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.file.FileChooser;
import com.kotcrab.vis.ui.widget.file.FileChooserAdapter;
import com.kotcrab.vis.ui.widget.tabbedpane.Tab;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StatisticsModule extends Tab {

    private Table content;
    private StandardTableBuilder builder;
    private String file;
    private FileChooser fileChooser;

    public StatisticsModule(){
        // Define our table
        content = new VisTable();
        // We use a builder so it  works better
        builder = new StandardTableBuilder(new Padding(2, 3));
        // Adjust file chooser
        setupChooser();
        // BUild UI
        buildUI();
        // Get the table from builder
        content = builder.build();
        // Set table background
        content.setBackground(VisUI.getSkin().getDrawable("window-bg"));
    }
    
    private void setupChooser(){
        fileChooser = new FileChooser(new FileHandle(Gdx.files.getExternalStoragePath()), FileChooser.Mode.OPEN);
        fileChooser.setDirectory(Gdx.files.getExternalStoragePath());
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
                fileToSout(file);
            }
        });
    }

    private void fileToSout(FileHandle file){
        try{
            InputStream read = file.read();
            BufferedReader reader = new BufferedReader(new InputStreamReader(read));
            String line = "";
            while((line = reader.readLine()) != null)
                System.out.println(line);
        }
        catch (Exception e){ e.printStackTrace(); }
    }

    private void buildUI(){
        VisTextButton showChooser = new VisTextButton("Open");
        builder.append(showChooser);
        showChooser.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                content.getStage().addActor(fileChooser.fadeIn());
            }
        });
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
