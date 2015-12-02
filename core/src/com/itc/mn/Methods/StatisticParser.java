package com.itc.mn.Methods;

import com.badlogic.gdx.files.FileHandle;
import com.itc.mn.Structures.GraphingData;
import com.itc.mn.Structures.Lists.StatisticList;
import com.itc.mn.Structures.Node;
import com.itc.mn.Things.Const;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class loads a text file with data (one per line), loads them to a list and gives the basic statistic values.
 * Or it can receive data manually and process it.
 *
 * If no amount of classes is given, it defaults to the minimum amount (5).
 */
public class StatisticParser {

    private FileHandle fileHandle;
    private StatisticList list;
    private static int MINIMUM_CLASSES = 5;
    private static int MAXIMUM_CLASSES = 20;
    private int classes = MINIMUM_CLASSES;
    private GraphingData data;

    public StatisticParser(FileHandle file){
        this.fileHandle = file;
        list = new StatisticList();
        load();
        refreshData();
    }

    public StatisticParser(){
        list = new StatisticList();
    }

    /**
     * Loads the text file to a list
     */
    private void load(){
        try{
            InputStream read = fileHandle.read();
            BufferedReader reader = new BufferedReader(new InputStreamReader(read));
            String line = null;
            while((line = reader.readLine()) != null) {
                if(line.matches("[\\+|\\-]?[0-9]+\\.?[0-9]*"))
                    list.insertNode(Double.parseDouble(line));
                else
                    System.out.println("Invalid value: "+line);
            }
        }
        catch (Exception e){ e.printStackTrace(); }
    }

    /**
     * Returns the data range, given the max value and the min value
     * @return A double value, the range between two numbers.
     */
    public double getRange(){
        try{
            Node root = list.getRoot();
            Node last = list.getLastNode();
            return last.getValue() - root.getValue();
        }
        catch (Exception e){
            return 0;
        }
    }

    /**
     * Returns the class width, given the range of data and the desired amount of classes
     * @param desiredClasses Amount of desired classes
     * @return An integer, that defines the separation between bounds of classes
     */
    public int getClassWidth(double desiredClasses){
        try{
            return (int)Math.ceil((getRange()/desiredClasses));
        }
        catch (Exception e) {
            return 0;
        }
    }

    public int getLowerBound()throws NullPointerException{
        if(list.isListEmpty())
            throw new NullPointerException("Empty list.");
        else {
            return (int)Math.floor(list.getRoot().getValue());
        }
    }

    public String getVarianze(MODE mode){
        return new DecimalFormat(Const.Load().format).format(list.getVarianze(mode));
    }

    public String getStdDeviation(MODE mode){
        return new DecimalFormat(Const.Load().format).format(list.getStdDeviation(mode));
    }

    /**
     * Returs an array, containing class-grouped values of the given data
     * @return
     * @throws NullPointerException
     */
    public double[][] getValuesFreqData()throws NullPointerException{
        if(list.isListEmpty())
            throw new NullPointerException("Empty List.");
        else{
            double[][] valueFreqData = initializeEmptyArray(classes, 1);
            // Here we process all the data to group in classes
            Node tmp = list.getRoot();
            for(int i = 0; i < classes; i++){ // We're going to calculate the classes
                int[] actualBounds = getClassBound(i);
                while (tmp.getNext() != null && tmp.getValue() < actualBounds[1]){
                    System.out.println("Added value: "+tmp.getValue());
                    valueFreqData[i][0] += tmp.getFrequency();
                    tmp = tmp.getNext();
                }
                if(tmp.getNext() == null && tmp.getValue() < actualBounds[1]){
                    System.out.println("New Added value: "+tmp.getValue());
                    valueFreqData[i][0] += tmp.getFrequency();
                }
                System.out.println("Class "+i+" upper bound is: "+actualBounds[1]);
            }
            //Then we return it as an array
            return valueFreqData;
        }
    }

    private double[][] initializeEmptyArray(int col, int rows){
        double[][] tmp = new double[col][rows];
        for(double[] row: tmp)
            for(double value: row)
                value = 0;
        return tmp;
    }

    private int getClassWidth(){
        return getClassWidth(classes);
    }

    private int[] getClassBound(int classNumber){
        int[] bounds = new int[2];
        bounds[0] = getLowerBound()+(getClassWidth()*classNumber);
        bounds[1] = bounds[0]+getClassWidth();
        return bounds;
    }

    public void printListToSTDOUT(){
        ArrayList<double[]> array = list.getDoubleArray();
        Iterator<double[]> iterator = array.iterator();
        while (iterator.hasNext()) {
            for (double nodo : iterator.next())
                System.out.print(nodo + "\t");
            System.out.println("");
        }
    }

    public String getMean() {
        return new DecimalFormat(Const.Load().format).format(list.getMean());
    }

    public String getMode() {
        return String.valueOf(list.getSMode());
    }

    public String getMedian() {
        return new DecimalFormat(Const.Load().format).format(list.getMedian());
    }

    public enum MODE{
        DEMOGRAPHIC, SAMPLE
    }

    public ArrayList getValuesList(){
        return list.getValueList();
    }

    public ArrayList getValFreqList(){
        return list.getValFreqList();
    }

    public String getDataAmount() {
        return String.valueOf(list.getDataAmount());
    }

    public void setClasses(int classes){ this.classes = classes; }

    public GraphingData getGraphingData(){
        if(data == null)
            refreshData();
        return data;
    }

    public void refreshData(){
        data = new GraphingData(getClassWidth(classes), classes, getLowerBound(), this);
        try{
            data.setFreqData(getValuesFreqData());
        }
        catch (Exception e){}
    }

    public void addValue(double val, int value){
        Node node = list.insertNode(val);
        node.setFrequency(value);
    }
}
