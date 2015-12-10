package com.itc.mn.Structures.Lists;

import com.itc.mn.Structures.NodeXY;

import java.util.Iterator;

/**
 * This list holds values of the kind f(x) = y
 */
public class XYList implements Iterable<NodeXY> {

    private NodeXY root = null;

    /**
     * Creates a new node with the given values and inserts it into the list
     * @param x
     * @param y
     */
    public void insert(Double x, Double y){
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

    @Override
    public Iterator<NodeXY> iterator() {
        Iterator<NodeXY> iterator = new Iterator<NodeXY>() {

            NodeXY tmp = root;

            @Override
            public boolean hasNext() {
                return (tmp != null);
            }

            @Override
            public NodeXY next() {
                NodeXY node = tmp;
                tmp = node.getNext();
                return node;
            }
        };
        return iterator;
    }

    public boolean isEmpty(){
        return root == null;
    }

    public int length(){
        int i = 0;
        if(!isEmpty()) {
            NodeXY tmp = root;
            while (tmp != null) {
                i++;
                tmp = tmp.getNext();
            }
        }
        return i;
    }

    public double[][] toArray()throws Exception{
        if(isEmpty())
            throw new Exception("Emtpy list");
        int length = length();
        NodeXY tmp = root;
        double[][] array = new double[length][2];
        for(int i = 0; i < length; i++){
            array[i][0] = tmp.getX();
            array[i][1] = tmp.getY();
            tmp = tmp.getNext();
        }
        return array;
    }
}
