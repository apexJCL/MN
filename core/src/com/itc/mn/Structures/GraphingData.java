package com.itc.mn.Structures;

public class GraphingData {

    public int classWidth;
    public int starting_value;
    public int classesAmount;

    public GraphingData(int classWidth, int classesAmount, int starting_value){
        this.classWidth = classWidth;
        this.classesAmount = classesAmount;
        this.starting_value = starting_value;
    }

    public int getClassWidth() { return classWidth; }
    public void setClassWidth(int classWidth) { this.classWidth = classWidth; }
    public int getStarting_value() { return starting_value; }
    public void setStarting_value(int starting_value) { this.starting_value = starting_value; }
    public int getClassesAmount() { return classesAmount; }
    public void setClassesAmount(int classesAmount) { this.classesAmount = classesAmount; }

}
