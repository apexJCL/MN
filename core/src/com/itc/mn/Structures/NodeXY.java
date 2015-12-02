package com.itc.mn.Structures;

/**
 * This class literally contains two numbers, X and it's matching Y
 */
public class NodeXY {

    private NodeXY previous = null;
    private NodeXY next = null;
    private double x;
    private double y;


    public NodeXY(double x, double y){
        this.x = x;
        this.y = y;
    }

    public NodeXY(NodeXY previous, double x, double y){
        this.x = x;
        this.y = y;
        previous.next = this;
        this.previous = previous;
    }

    public NodeXY(double x, double y, NodeXY next){
        this.x = x;
        this.y = y;
        this.next = next;
        next.previous = this;
    }

    public NodeXY(NodeXY previous, double x, double y, NodeXY next){
        this.x = x;
        this.y = y;
        previous.next = this;
        this.previous = previous;
        next.previous = this;
        this.next = next;
    }

    public double getX() { return x; }
    public void setX(double x) { this.x = x; }
    public double getY() { return y; }
    public void setY(double y) { this.y = y; }

}
