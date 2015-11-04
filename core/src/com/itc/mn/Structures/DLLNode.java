package com.itc.mn.Structures;

/**
 * Nodes, yikes!
 * This is a node for a double-linked list.
 */
public strictfp class DLLNode {

    private DLLNode previous, next;
    private double frequency = 0;
    private double value;
    private String label;

    /**
     * Default constructor, allowing the instance of a node
     * with a given value and a default frequency of 1.
     * You can increase the frequency calling frequency method.
     *
     * This node references to itself
     * @param value
     */
    public DLLNode(double value, boolean root){
        this.value = value;
        this.next = this.previous = this;
        this.frequency = 1;
    }

    /**
     * Allows creating a new node, then adding a node with an automatic reference to the previous node.
     * This means this method is for adding a node before a node
     * @param value
     * @param next
     */
    public DLLNode(double value, DLLNode next){
        this.value = value;
        this.next = next;
        this.next.previous = this;
        frequency = 1;
    }

    /**
     * Allows creating a new node, then adding it after the given node with an automatic reference
     * between nodes.
     * @param previous
     * @param value
     */
    public DLLNode(DLLNode previous, double value){
        this.value = value;
        this.previous = previous;
        this.previous.next = this;
        frequency = 1;
    }

    /**
     * Creates a new node with auto-refering to itself to their neighbors.
     * Usually you can use it to instantiate the root of a list, or to put
     * a node between two others.
     * @param previous
     * @param value
     * @param next
     */
    public DLLNode(DLLNode previous, double value, DLLNode next){
        this.value = value;
        this.previous = previous;
        this.next = next;
        this.previous.next = this.next.previous = this;
        frequency = 1;
    }

    /**
     * This will allow to increase the frequency of the given node
     * IT MUST NOT BE USED WHEN THE VALUE IS NULL
     * @param increase
     */
    public boolean frequency(boolean increase){
        frequency+=(increase) ? 1: -1;
        return increase;
    }

    public DLLNode getNext() { return next; }
    public DLLNode getPrevious() { return previous; }
    public double getValue(){ return value; }
}
