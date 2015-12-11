package com.itc.mn.Methods;

import com.itc.mn.Structures.Lists.XYList;
import com.itc.mn.Structures.NodeXY;

public class Regression {

    private XYList list;
    private double x_average, y_average, r, a0, a1, x2_sum, xy_sum, x_sum, y_sum, Sr, St;
    private Double[] xy;
    private Double[] x2;
    private Double[] yaax; // This array will store (yi-a0-a1xi)^2 = Sr
    private Double[] yiy; // This array will store (yi-y)^2 = St

    // ao = y_average - a1x

    public Regression(){
        list = new XYList();
        initialize();
    }

    private void initialize(){
        x_average = 0;
        y_average = 0;
        r = 0;
        a0 = 0;
        a1 = 0;
        x2_sum = 0;
        xy_sum = 0;
        x_sum = 0;
        y_sum = 0;
        Sr = 0;
        St = 0;
    }

    public void addValues(Double x, Double y){
        list.insert(x, y);
    }

    /**
     * Calculates the regression and returns the coefficient r
     * @return
     * @throws Exception
     */
    public Double calculateRegression() throws Exception{
        initialize();
        Double regression = null;
        // First we initialize the arrays with their respective size
        int list_length = list.length(); // This returns the size of the list
        xy = new Double[list_length];
        x2 = new Double[list_length];
        yaax = new Double[list_length];
        yiy = new Double[list_length];
        // Now we manipulate the list
        int i = 0; // So we can control the fixed-sized arrays
        for(NodeXY node: list){
            x_sum+=node.getX(); // This will sum the values
            y_sum+=node.getY(); // later we will divide to convert to proper averages
            xy[i] = node.getX()*node.getY();
            xy_sum+=xy[i];
            x2[i] = node.getX()*node.getX();
            x2_sum+=x2[i];
            i++;
        }
        // Now we fix the averages
        x_average = x_sum/list_length;
        y_average = y_sum/list_length;
        // Now we calculate a0, list_lenght would be the data size (n)
        a1=((list_length*xy_sum)-(x_sum*y_sum))/(list_length*x2_sum-(x_sum*x_sum));
        // now we calculate a0
        a0=y_average-(a1*x_average);
        // To control fixed-size array, we restablished the i
        i = 0;
        for(NodeXY node: list){
            yaax[i] = Math.pow(node.getY()-a0-(a1*node.getX()),2);
            Sr += yaax[i];
            yiy[i] = Math.pow(node.getY() - y_average,2);
            St += yiy[i];
            i++;        }
        regression = Math.sqrt((St-Sr)/St);
        r = regression;
        return regression;
    }

    public double getR() { return r; }
    public double getSt() { return St; }
    public double getSr() { return Sr; }
    public double getA0() { return a0; }
    public double getA1() { return a1; }
    public Double[] getXy() { return xy; }
    public Double[] getX2() { return x2; }
    public XYList getList(){ return list; }
    public Double[] getYiy() { return yiy; }
    public double getX_sum() { return x_sum; }
    public double getY_sum() { return y_sum; }
    public double getXy_sum() { return xy_sum; }
    public double getX2_sum() { return x2_sum; }
    public Double[] getYaax() { return yaax; }
    public double getY_average() { return y_average; }
    public double getX_average() { return x_average; }
}
