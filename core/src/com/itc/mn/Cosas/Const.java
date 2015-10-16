package com.itc.mn.Cosas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Json;

/**
 * A class that holds constants
 */
public class Const {
    public static final int WIDTH = 16;
    public static final int HEIGHT = 10;
    public static String version = "0.5a";
    public static float XY_AXIS_DEFAULT = 10f;
    public static String pref_name = "mn_prefs";
    public static String id = "general";
    public static float maxPoints = 0.001f;
    public static float minPoints = 0.00001f;
    public static Const config;
    public String format = "#.######";
    public float[] backgroundColor = {0.19607843f, 0.19607843f, 0.19607843f, 1};
    public Color singleGraphic = new Color(0, 1, 0.8f, 1);
    public Color rootColor = new Color(0, 255, 0, 0.7f);
    public Color axisColor = new Color(0.7294118f, 0.21960784f, 0.37254903f, 1);
    public float currentPoints = 0.001f;
    public float maxScaleX = 100f;
    public float maxScaleY = 100f;

    public Const() {
    }

    public static Const Load() {
        config = new Json().fromJson(Const.class, Gdx.app.getPreferences(pref_name).getString(id));
        return config;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setBgColor(float r, float g, float b, float a) {
        backgroundColor[0] = r;
        backgroundColor[1] = g;
        backgroundColor[2] = b;
        backgroundColor[3] = a;
    }

    public void setSGColor(Color newColor) {
        singleGraphic = newColor;
    }

    public void setAxisColor(Color newColor) {
        axisColor = newColor;
    }

    public void setCurrentPoints(float points) {
        this.currentPoints = points;
    }
}
