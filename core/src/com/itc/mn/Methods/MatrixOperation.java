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

    public double[][] gauss_jordan(double[][] mat){
        double[][] gauss = gauss(mat);
        for(int i = gauss.length-1; i >= 0; i--)
            zeroesBelowElement(i, gauss, Direction.UP);
        return gauss;
    }

    /**
     * Will return gauss of the given matrix
     * @return
     */
    public double[][] gauss(double[][] mat){
        double[][] ans = mat;
        for(int i = 0; i < mat.length; i++){
            if(ans[i][i] == 0 && i == 0){ // If the i element of the i column equals 0, we must switch it with other
                int x = 1;
                while(ans[x][i] == 0 )
                    x++;
                switchrows(mat, i, x);
            }
            divideRow(i, ans[i][i], ans); // Divides the row so the first element becomes 1
            zeroesBelowElement(i, mat, Direction.DOWN); // Turns the elements below the 1 to 0's
        }
        fixZeroes(ans);
        return ans;
    }

    // Gauss Thingies

    private void fixZeroes(double[][] mat){
        for(int i = 0; i < mat.length; i++)
            for(int j = 0; j < mat[0].length; j++){
                if(String.valueOf(mat[i][j]).matches("-0.0"))
                    mat[i][j] = 0;
            }
    }

    private void zeroesBelowElement(int pivot, double[][] mat, Direction direction){
        if(direction == Direction.DOWN) {
            if (pivot + 1 < mat.length) {
                for (int i = 1 + pivot; i < mat.length; i++) {
                    double multiplier = mat[i][pivot] * -1; // Multiply the value so if eliminates
                    for (int j = 0; j < mat[pivot].length; j++) {
                        mat[i][j] += (mat[pivot][j] * multiplier);
                    }
                }
            }
        }
        else{
            if(pivot -1 >= 0){
                for(int i = pivot - 1; i >= 0; i--){
                    double multiplier = mat[i][pivot] * -1; // Multiply the value so if eliminates
                    for(int j = mat[i].length; j >= 0; j--)
                        mat[i][j] += (mat[pivot][j] * multiplier);
                }
            }
        }
    }

    private enum Direction{
        UP, DOWN
    }

    /**
     * Divides the given row by the given number
     * @param row
     * @param value
     * @param mat
     */
    private void divideRow(int row, double value, double[][] mat){
        for(int i = 0; i < mat[row].length; i++)
            mat[row][i]/=value;
    }

    /**
     * Switch two given rows
     * @param mat
     * @param rowA
     * @param rowB
     */
    private void switchrows(double[][] mat, int rowA, int rowB){
        for(int i = 0; i < mat[rowA].length; i++){
            double tmp = mat[rowA][i];
            mat[rowA][i] = mat[rowB][i];
            mat[rowB][i] = tmp;
        }
    }

    // End of gauss thingies

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
