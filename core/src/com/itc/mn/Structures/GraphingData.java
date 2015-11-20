package com.itc.mn.Structures;

import com.itc.mn.Methods.StatisticParser;

public class GraphingData {

    public int classWidth;
    public int starting_value;
    public int classesAmount;
    public double[][] freqData;
    private StatisticParser parser;

    public GraphingData(int classWidth, int classesAmount, int starting_value, StatisticParser parser){
        this.parser = parser;
        this.classWidth = classWidth;
        this.classesAmount = classesAmount;
        this.starting_value = starting_value;
    }

    public void setFreqData(double[][] freqData){ this.freqData = freqData; }
    public int getClassWidth() { return classWidth; }
    public void setClassWidth(int classWidth) { this.classWidth = classWidth; }
    public int getStarting_value() { return starting_value; }
    public void setStarting_value(int starting_value) { this.starting_value = starting_value; }
    public int getClassesAmount() { return classesAmount; }
    public void setClassesAmount(int classesAmount) { this.classesAmount = classesAmount; }

    public GraphingData refreshData() {
        return parser.getGraphingData();
    }
}
