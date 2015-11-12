package com.itc.mn.UI.Modules;

import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;

/**
 * Created by zero_ on 12/11/2015.
 */
public class WelcomeModule extends VisTable {

    private VisLabel sample;

    public WelcomeModule(){
        sample = new VisLabel("Hello!");
        add(sample).expand().fill();
    }
}
