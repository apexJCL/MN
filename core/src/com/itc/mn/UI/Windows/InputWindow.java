package com.itc.mn.UI.Windows;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.I18NBundle;
import com.itc.mn.Things.Const;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.VisTextField;
import com.kotcrab.vis.ui.widget.VisWindow;

/**
 * Created by zero_ on 13/11/2015.
 */
public class InputWindow extends VisWindow {

    private I18NBundle bundle = Const.loadBundle();
    private VisTextButton accept, cancel;

    /**
     * Creates a new window with the indicated input text areas and with a predefined action.
     * @param title Title of the Window
     * @param textfield String array that holds the key to the I18NBundle and a "variable" name to retrieve values later.
     *                  Expected array ex. {{"key", "variable_name"}, {....}}
     */
    public InputWindow(String title, String[][] textfield) {
        super(title, true);
        // Here we add the fields to the window
        for(String[] field: textfield){
            VisTextField tmp = new VisTextField();
            tmp.setMessageText(field[0]);
            tmp.setName(field[1]);
            add(tmp).expandX().center().pad(1f).colspan(2).row();
        }
        accept = new VisTextButton(bundle.get("accept"));
        cancel = new VisTextButton(bundle.get("close"));
        // Add the default buttons to the final layout
        add(cancel).expandX().fillX().pad(10f);
        add(accept).expandX().fillX().pad(10f);
        // Default listener for the cancel button
        cancel.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                fadeOut();
            }
        });
        pack(); // Pack tight the shit out of this window!
        centerWindow();
        addCloseButton();
        closeOnEscape();
    }

    /**
     * Returns the value inside the requested and previously declared and existant TextArea
     * @param variable Name of the variable assigned when created
     * @return the current variable in use
     * @throws Exception Can't find requested variable
     */
    public String getVariable(String variable) throws Exception{
        for(Actor textField: getChildren())
            if (textField.getName() != null)
                if (textField.getName().equals(variable))
                    return ((VisTextField)textField).getText();
        throw new Exception(bundle.get("undefined_variable"));
    }

    public VisTextButton getAcceptButton(){ return accept; }
    public VisTextButton getCancelButton(){ return cancel; }
//
//    private class InputValidator implements TextField.TextFieldListener{
//
//        @Override
//        public void keyTyped(TextField textField, char c) {
//            if(String.valueOf(c).matches("(0-9)*"))
//        }
//    }
}
