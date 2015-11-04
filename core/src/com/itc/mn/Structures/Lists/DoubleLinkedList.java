package com.itc.mn.Structures.Lists;

import com.itc.mn.Structures.DLLNode;

/**
 * This creates a double linked list with numerical values.
 * The nodes already have a frequency indicator, so we don't have
 * duplicated nodes with same values
 */
public class DoubleLinkedList {

    private DLLNode root;
    private String label;

    /**
     * Creates an empty Double Linked list
     */
    public DoubleLinkedList(){
        root = null;
    }

    /**
     * Creates a double linked list with a given name, in case
     * it happens to be many dll's.
     * @param label Name of the linked list
     */
    public DoubleLinkedList(String label){
        root = null;
        this.label = label;
    }

    private void insertNode(DLLNode node){
        if(isListEmpty())
            root = node;
        else{
            if(node.getValue() > root.getValue()){
                DLLNode tmp = root.getNext();
                while (tmp != root && node.getValue() > tmp.getValue())
                    tmp = tmp.getNext();
            }
            else if(node.getValue() < root.getValue()){}
            else if(node.getValue() == root.getValue());
        }
    }

    public boolean isListEmpty(){
        return root == null;
    }

}
