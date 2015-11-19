package com.itc.mn.Methods;

import com.badlogic.gdx.files.FileHandle;
import com.itc.mn.Structures.Lists.DoubleLinkedList;
import com.itc.mn.Structures.Node;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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
    private DoubleLinkedList list;
    public static int MINIMUM_CLASSES = 5;
    public static int MAXIMUM_CLASSES = 20;
    public int classes = MINIMUM_CLASSES;

    public StatisticParser(FileHandle file){
        this.fileHandle = file;
        list = new DoubleLinkedList();
        load();
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
     * @param range range between minimum and maximum value
     * @param desiredClasses Amount of desired classes
     * @return An integer, that defines the separation between bounds of classes
     */
    public int getClassWidth(double range, double desiredClasses){
        try{
            return (int)Math.ceil((range/desiredClasses));
        }
        catch (Exception e) {
            return 0;
        }
    }

    /**
     * Sets the amount of classes to process, given two boundaries (min and max classes amount).
     * @param desiredClasses
     */
    public void setClassesAmount(int desiredClasses){
        if(desiredClasses >= MINIMUM_CLASSES && desiredClasses <= MAXIMUM_CLASSES)
            this.classes = desiredClasses;
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

    public ArrayList getValuesList(){
        return list.getValueList();
    }

    public ArrayList getValFreqList(){
        return list.getValFreqList();
    }

}
