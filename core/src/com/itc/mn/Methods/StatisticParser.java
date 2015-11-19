package com.itc.mn.Methods;

import com.badlogic.gdx.files.FileHandle;
import com.itc.mn.Structures.Lists.DoubleLinkedList;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * This class loads a text file with data (one per line), loads them to a list and gives the basic statistic values.
 * Or it can receive data manually and process it.
 */
public class StatisticParser {

    private FileHandle fileHandle;
    private DoubleLinkedList list;

    public StatisticParser(FileHandle file){
        this.fileHandle = file;
        list = new DoubleLinkedList();
        load();
    }

    private void load(){
        try{
            InputStream read = fileHandle.read();
            BufferedReader reader = new BufferedReader(new InputStreamReader(read));
            String line = null;
            while((line = reader.readLine()) != null) {
                if(true)
                    list.insertNode(Double.parseDouble(line));
                else
                    System.out.println("Invalid value: "+line);
            }
        }
        catch (Exception e){ e.printStackTrace(); }
    }

    public void printList(){
        ArrayList<Double> array = list.getDoubleArray();
        for(double value: array)
            System.out.println(value);
    }

}
