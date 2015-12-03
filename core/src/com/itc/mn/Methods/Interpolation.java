package com.itc.mn.Methods;

import com.itc.mn.Structures.Lists.XYList;
import com.itc.mn.Structures.NodeXY;
import java.util.ArrayList;

public strictfp class Interpolation {

    private ArrayList<NodeXY> values;
    private XYList list;
    private double[] unknown;

    public Interpolation(){
        list = new XYList();
    }

    /**
     * This will l_interpolate the previously given data
     */
    public double[][] l_interpolate(double[][] values, double[] unknown) throws Exception {
        this.unknown = unknown;
        for(double[] node: values) // First we add the nodes to the list
            list.insert(node[0], node[1]);
        // Now we add the missing values to the list
        for(double x: unknown)
            list.insert(x, null);
        // Now we l_interpolate for the missing values, using the closest value in the list
        for(NodeXY node: list){
            if(node.getY() == null && node.getNext() != null)
                node.setY(l_interpolate(node.getPrevious().getX(), node.getPrevious().getY(), node.getNext().getX(), node.getNext().getY(), node.getX()));
        }
        for (NodeXY node : list)
            System.out.println("X: "+node.getX()+" Y: "+node.getY());
        return list.toArray();
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
