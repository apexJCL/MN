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
        else{
            NodeXY tmp = root;
            while (tmp.getNext() != null && tmp.getX() < x)
                tmp = tmp.getNext();
            if(tmp.getNext() == null){
                if(tmp.getX() < x)
                    tmp.setNext(new NodeXY(tmp, x, y));
                else {
                    if (tmp.equals(root))
                        root = new NodeXY(x, y, root);
                    else
                        tmp.getPrevious().setNext(new NodeXY(tmp.getPrevious(), x, y, tmp));
                }
            }
            else {
                if (tmp.equals(root))
                    root = new NodeXY(x, y, root);
                else
                    tmp.getPrevious().setNext(new NodeXY(tmp.getPrevious(), x, y, tmp));
            }
        }
    }
}
