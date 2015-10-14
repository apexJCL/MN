package com.itc.mn.Cosas;

import com.badlogic.gdx.graphics.Color;

/**
 * A class that holds constants
 */
public class Const {
    public static final int WIDTH = 16;
    public static final int HEIGHT = 10;
    public static String pref_name = "mn_prefs";
    public static String id = "general";
    public String format = "#.####";
    public float[] backgroundColor = {0, 0, 0, 1};
    public Color singleGraphic = new Color(0, 0.866f, 1, 1);
    public Color rootColor = new Color(0, 255, 0, 0.7f);
    public Color axisColor = new Color(1, 1, 1, 1);

    public Const(){ }

    public void setFormat(String format) {
        this.format = format;
    }
    public String getFormat() {
        return format;
    }
    public void setBgColor(float r, float g, float b, float a){
        backgroundColor[0] = r;
        backgroundColor[1] = g;
        backgroundColor[2] = b;
        backgroundColor[3] = a;
    }
    public void setSGColor(Color newColor){ singleGraphic = newColor; }
    public void setAxisColor(Color newColor){ axisColor = newColor; }
}
