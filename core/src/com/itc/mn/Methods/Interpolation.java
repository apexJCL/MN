package com.itc.mn.Methods;

import com.itc.mn.Structures.NodeXY;
import java.util.ArrayList;

public strictfp class Interpolation {

    private ArrayList<NodeXY> values;
    private double[] unknown;

    public Interpolation(){

    }

    /**
     * This will interpolate the previously given data
     */
    public void interpolate(ArrayList<NodeXY> values, double[] unknown){
        this.values = values;
        this.unknown = unknown;
        for(NodeXY node: this.values){

        }
    }

    /**
     * This method interpolates between the given parameters to calculate the last one
     * @param x0 First X point
     * @param fx0 First Y point
     * @param x1 Second X point
     * @param fx1 Second Y point
     * @param x Desired X calculation
     */
    public double interpolate(float x0, float fx0, float x1, float fx1, float x){
        return fx0 + ((fx1 - fx0)/(x1 - x0)) * (x - x0);
    }

}
