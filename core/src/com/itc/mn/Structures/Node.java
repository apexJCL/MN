package com.itc.mn.Structures;

/**
 * Nodes, yikes!
 * This is a node for a double-linked list.
 */
public strictfp class Node {

    private Node previous, next;
    private double frequency = 0;
    private double value;
    private String label;

    /**
     * Default constructor, allowing the instance of a node
     * with a given value and a default frequency of 1.
     * You can increase the frequency calling frequency method.
     *
     * @param value
     */
    public Node(double value){
        this.value = value;
        this.next = this.previous = null;
        this.frequency = 1;
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

    public void setFrequency(int value){ this.frequency = value; }
    public Node getNext() { return next; }
    public Node getPrevious() { return previous; }
    public double getValue(){ return value; }
    public void setNext(Node node){ this.next = node; }
    public void setPrevious(Node node) { this.previous = node; }
    public double getFrequency() {
        return frequency;
    }
}
