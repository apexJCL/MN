package com.itc.mn.Cosas;

/**
 * A class that holds constants
 */
public class Const {
    public static final int WIDTH = 16;
    public static final int HEIGHT = 10;
    public static String pref_name = "mn_prefs";
    public static String id = "general";
    public String format = "#.####";

    public Const(){ }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }
}
