package com.itc.mn.Methods;

/**
 * Created by zero_ on 08/10/2015.
 */
public class MatrixOperation {

    private double[][] matrix_a, matrix_b;
    private double[][] multipliedMatrix, addedMatrices, substractedMatrices;
    private boolean matrixChanged;
    private boolean singleMatrix = false;
    /**
     * This is used to realize operations between two matrix.
     *
     * Give this two matrix, you can add, substract and multiply them (as long as
     * is possible)
     * @param matrix_a First Matrix
     * @param matrix_b Second Matrix
     */
    public MatrixOperation(double[][] matrix_a, double[][] matrix_b) {
        this.matrix_a = matrix_a;
        this.matrix_b = matrix_b;
        matrixChanged = false;
    }

    /**
     * Handles single-matrix operations, such as Gauss, Gauss-Jordan, Transpose.
     * @param matrix
     */
    public MatrixOperation(double[][] matrix){
        this.matrix_a = matrix;
        singleMatrix = true;
    }

    public enum Operation {
        SUMA, RESTA, MULTIPLICACION, TRANSPUESTA, GAUSS, GAUSS_JORDAN
    }

    public double[][] traspose(){
        double[][] tmp = new double[matrix_a[0].length][matrix_a.length];
        for(int i = 0; i < matrix_a.length; i++)
            for(int j = 0; j < matrix_a[0].length; j++)
                tmp[j][i] = matrix_a[i][j];
        return tmp;
    }

//    public double[][] gauss(){
//        double[][] tmp = matrix_a;
//        int i = 0;
//        while(i < tmp.length) {
//            if (tmp[i][i] != 1)
//                for (int j = i; j < tmp[0].length; j++)
//                    tmp[i][j] = tmp[i][j] / tmp[i][i];
//            if (i + 1 < tmp.length) { // To avoid array out of bounds
//                for (int j = i + 1; j < tmp.length; j++) // To scroll all the column
//                    if (tmp[j][i] != 0) // If our value isn't 0 already
//                        if (tmp[j][i] * tmp[i][i] < 0) // To know if we must add or substract
//                            tmp[j][i] += (tmp[j][i]);
//            }
//        }
//        return tmp;
//    }

    /**
     * Returns the previous given matrix
     *
     * @return
     */
    public double[][] multiplyMatrix() throws Exception {
        if (multipliedMatrix == null || matrixChanged) {
            if (areMatrixMultiplicable()) {
                multipliedMatrix = new double[matrix_a.length][matrix_b[0].length];
                for (int i = 0; i < matrix_a.length; i++) {
                    for (int j = 0; j < matrix_b[0].length; j++) {
                        for (int k = 0; k < matrix_a[0].length; k++)
                            multipliedMatrix[i][j] += matrix_a[i][k] * matrix_b[k][j];
                    }
                }
                return multipliedMatrix;
            } else throw new Exception("Current matrices aren't multiplicable. Try switching them.");
        } else
            return multipliedMatrix;
    }

    public double[][] a_s_Matrix(Operation operation) throws Exception {
        if (areMatrixEqualSized()) {
            if (addedMatrices == null || substractedMatrices == null || !matrixChanged) {
                double[][] tmp = new double[matrix_a.length][matrix_a[0].length];
                for (int i = 0; i < matrix_a.length; i++) {
                    for (int j = 0; j < matrix_a[0].length; j++) {
                        switch (operation) {
                            case SUMA:
                                tmp[i][j] = matrix_a[i][j] + matrix_b[i][j];
                                break;
                            case RESTA:
                                tmp[i][j] = matrix_a[i][j] - matrix_b[i][j];
                                break;
                        }
                    }
                }
                switch (operation) {
                    case SUMA:
                        addedMatrices = tmp;
                        return tmp;
                    case RESTA:
                        substractedMatrices = tmp;
                        return tmp;
                }
            } else {
                switch (operation) {
                    case SUMA:
                        return addedMatrices;
                    case RESTA:
                        return substractedMatrices;
                }
            }
        }
        throw new Exception("Matrix aren't equal sized");
    }

    /**
     * This switches the matrices for the operations
     */
    public void switchMatrices() {
        double[][] tmp = matrix_a;
        matrix_a = matrix_b;
        matrix_b = tmp;
    }

    /**
     * It says if the matrices are equal-sized so you can add, substract them
     *
     * @return boolean
     */
    public boolean areMatrixEqualSized() {
        return (matrix_a.length == matrix_b.length && matrix_a[0].length == matrix_b[0].length);
    }

    /**
     * It says if the matrices are multiplicable
     *
     * @return boolean
     */
    public boolean areMatrixMultiplicable() {
        return (matrix_a[0].length == matrix_b.length);
    }
}
