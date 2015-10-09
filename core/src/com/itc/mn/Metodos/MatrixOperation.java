package com.itc.mn.Metodos;

/**
 * Created by zero_ on 08/10/2015.
 */
public class MatrixOperation {

    private double[][] matrix_a, matrix_b;
    private double[][] multipliedMatrix, addedMatrices, substractedMatrices;
    private boolean matrixChanged;

    public MatrixOperation(double[][] matrix_a, double[][] matrix_b) {
        this.matrix_a = matrix_a;
        this.matrix_b = matrix_b;
        matrixChanged = false;
    }

    public enum Operation {
        SUMA, RESTA, MULTIPLICACION
    }

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
