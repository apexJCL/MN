package com.itc.mn.Structures.Lists;

import com.itc.mn.Structures.NodeXY;

/**
 * This list holds values of the kind f(x) = y
 */
public class XYList {

    private NodeXY root = null;


    public void insert(double x, double y){
        if(root == null)
            root = new NodeXY(x, y);
    }

}
