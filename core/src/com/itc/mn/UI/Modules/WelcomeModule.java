package com.itc.mn.UI.Modules;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.I18NBundle;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.tabbedpane.Tab;

/**
 * Created by zero_ on 12/11/2015.
 */
public class WelcomeModule extends Tab {

    private VisLabel sample;
    private Table content;
    private String title;

    public WelcomeModule(I18NBundle bundle){
        super(false, false);
        this.title = bundle.get("welcome_title");
        content = new Table();
        content.setSkin(VisUI.getSkin());
        content.setBackground(VisUI.getSkin().getDrawable("menu-bg"));
        sample = new VisLabel(bundle.get("welcome_text"));
        content.add(sample).center();
    }

    @Override
    public String getTabTitle() {
        return title;
    }

    @Override
    public Table getContentTable() {
        return content;
    }
}
