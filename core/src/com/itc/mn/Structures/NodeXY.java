package com.itc.mn.Structures;

/**
 * This class literally contains two numbers, X and it's matching Y
 * Y will be treated as an object just for calculation matters
 */
public class NodeXY {

    private NodeXY previous = null;
    private NodeXY next = null;
    private Double x;
    private Double y;


    public NodeXY(Double x, Double y){
        this.x = x;
        this.y = y;
    }

    public NodeXY(NodeXY previous, Double x, Double y){
        this.x = x;
        this.y = y;
        previous.next = this;
        this.previous = previous;
    }

    public NodeXY(Double x, Double y, NodeXY next){
        this.x = x;
        this.y = y;
        this.next = next;
        next.previous = this;
    }

    public NodeXY(NodeXY previous, Double x, Double y, NodeXY next){
        this.x = x;
        this.y = y;
        previous.next = this;
        this.previous = previous;
        next.previous = this;
        this.next = next;
    }

    public Double getX() { return x; }
    public void setX(Double x) { this.x = x; }
    public Double getY() { return y; }
    public void setY(Double y) { this.y = y; }
    public NodeXY getPrevious() { return previous; }
    public void setPrevious(NodeXY previous) { this.previous = previous; }
    public NodeXY getNext() { return next; }
    public void setNext(NodeXY next) { this.next = next; }
}
