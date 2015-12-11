package com.itc.mn.Methods;

import com.itc.mn.Structures.Lists.XYList;
import com.itc.mn.Structures.NodeXY;

public strictfp class Interpolation {

    private XYList list;

    public Interpolation(){
        list = new XYList();
    }

    public XYList l_interpolate_list(XYList list) throws Exception {
        // Now we l_interpolate for the missing values, using the closest value in the list
        for(NodeXY node: list){
            if(node.getY() == null && node.getNext() != null)
                node.setY(l_interpolate(node.getPrevious().getX(), node.getPrevious().getY(), node.getNext().getX(), node.getNext().getY(), node.getX()));
        }
        return list;
    }

    public XYList q_interpolate(XYList list)throws Exception{
        for (NodeXY nodeXY: list){
            // Three cases, 1. X value is the first in the list, X is in the middle, X is the last value
            if(list.getRoot() == nodeXY && nodeXY.getY() == null)
                nodeXY.setY(q_interpolate(nodeXY.getNext().getX(), nodeXY.getNext().getY(), nodeXY.getNext().getNext().getX(),  nodeXY.getNext().getNext().getY(),  nodeXY.getNext().getNext().getNext().getX(),  nodeXY.getNext().getNext().getNext().getY(), nodeXY.getX()));
            else {
                if (list.getLast() != nodeXY && nodeXY.getY() == null) {
                    if (nodeXY.getNext().getNext() != null)
                        nodeXY.setY(q_interpolate(nodeXY.getPrevious().getX(), nodeXY.getPrevious().getY(), nodeXY.getNext().getX(), nodeXY.getNext().getY(), nodeXY.getNext().getNext().getX(), nodeXY.getNext().getNext().getY(), nodeXY.getX()));
                    else
                        nodeXY.setY(q_interpolate(nodeXY.getPrevious().getPrevious().getX(), nodeXY.getPrevious().getPrevious().getY(), nodeXY.getPrevious().getX(), nodeXY.getPrevious().getY(), nodeXY.getNext().getX(), nodeXY.getNext().getY(), nodeXY.getX()));
                }
                else if (nodeXY.getY() == null)
                    nodeXY.setY(q_interpolate(nodeXY.getPrevious().getPrevious().getPrevious().getX(), nodeXY.getPrevious().getPrevious().getPrevious().getY(), nodeXY.getPrevious().getPrevious().getX(), nodeXY.getPrevious().getPrevious().getY(), nodeXY.getPrevious().getX(), nodeXY.getPrevious().getY(), nodeXY.getX()));
            }
        }
        return list;
    }

    /**
     * Linear Interpolation.
     *
     * This method interpolates between the given parameters to calculate the last one
     * @param x0 First X point
     * @param fx0 First Y point
     * @param x1 Second X point
     * @param fx1 Second Y point
     * @param x Desired X calculation
     */
    public Double l_interpolate(Double x0, Double fx0, Double x1, Double fx1, Double x){
        return fx0 + ((fx1 - fx0)/(x1 - x0)) * (x - x0);
    }

    /**
     * Returns a quadratic interpolation result
     * @param x0
     * @param fx0
     * @param x1
     * @param fx1
     * @param x2
     * @param fx2
     * @param x
     * @return
     */
    public Double q_interpolate(Double x0, Double fx0, Double x1, Double fx1, Double x2, Double fx2, Double x){
        return fx0 + b1(x0, fx0, x1, fx1)*(x - x0) + b2(x0, fx0, x1, fx1, x2, fx2)*(x - x0)*(x - x1);
    }

    /**
     * Returns b1, useful for Quadratic interpolation
     * @param x0 First X
     * @param fx0 Function evaluated in X0
     * @param x1 Second X
     * @param fx1 Function evaluated in X1
     * @return
     */
    private Double b1(Double x0, Double fx0, Double x1, Double fx1){
        return (fx1-fx0)/(x1-x0);
    }


    /**
     * Returns b2, useful for Quadratic interpolation
     * @param x0
     * @param fx0
     * @param x1
     * @param fx1
     * @param x2
     * @param fx2
     * @return
     */
    private Double b2(Double x0, Double fx0, Double x1, Double fx1, Double x2, Double fx2){
        return (b1(x1, fx1, x2, fx2) - b1(x0, fx0, x1, fx1))/(x2 - x0);
    }
}
