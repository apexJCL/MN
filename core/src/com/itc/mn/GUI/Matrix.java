package com.itc.mn.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.itc.mn.Metodos.MatrixOperation;
import com.itc.mn.Metodos.MatrixOperation.Operation;
import com.kotcrab.vis.ui.widget.*;

import java.util.ArrayList;
import java.util.StringTokenizer;

import static com.itc.mn.Metodos.MatrixOperation.Operation.*;

/**
 * This window will hold a little program that will evaluate two matrix with the given options.
 */
public class Matrix extends VisWindow {

    private final VisLabel l_opciones = new VisLabel("Operacion a realizar");
    private final VisLabel lma = new VisLabel("Matriz A");
    private final VisLabel lmb = new VisLabel("Matriz B");
    private VisLabel mensaje = new VisLabel("");
    private final VisTextButton calcular = new VisTextButton("Calcular");
    private final VisTextButton cerrar = new VisTextButton("Cerrar");
    private VisSelectBox opciones;
    private VisTextArea matrix_a, matrix_b;
    private VisTable inputTable, showTable;
    private VisSplitPane splitPane;
    private MatrixOperation matrixOperation;
    private double[][] ans;

    public Matrix() {
        super("Matrices", true);
        // Create the table that will hold our input stuff
        inputTable = new VisTable(true);
        // Create the showTable where we will show the matrices
        showTable = new VisTable(true);
        // We create the matrix operations
        opciones = new VisSelectBox();
        opciones.setItems(new Operation[]{SUMA, RESTA, MULTIPLICACION});
        // We add the fields for the matrix
        matrix_a = new VisTextArea();
        matrix_b = new VisTextArea();
        matrix_a.setMessageText("[1,2,3],[4,5,6]");
        matrix_b.setMessageText("[1,2,3],[4,5,6]");
        // Default window operations
        addCloseButton();
        closeOnEscape();
        // Adding events to default buttons
        cerrar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                close();
            }
        });
        calcular.addListener(new corroboraEvalua());
        // We add the stuff
        inputTable.add(l_opciones).expandX().pad(2f);
        inputTable.add(opciones).expandX().pad(2f).row();
        inputTable.add(lma).expandX().center().pad(1f);
        inputTable.add(matrix_a).expandX().left().pad(2f).colspan(2).row();
        inputTable.add(lmb).expandX().center().pad(1f);
        inputTable.add(matrix_b).expandX().left().pad(2f).colspan(2).row();
        inputTable.add(mensaje).expandX().center().pad(2f).colspan(2).row();
        inputTable.add(cerrar).expandX().right().pad(2f);
        inputTable.add(calcular).expandX().left().pad(5f).row();
        // Creating the splitpanel
        splitPane = new VisSplitPane(inputTable, showTable, false);
        // Adding the splitpane to the window
        add(splitPane).expand().fill();
        splitPane.setSplitAmount(1);
        // Adjusting
        pack();
        //setSize(Gdx.graphics.getWidth()*0.5f, Gdx.graphics.getHeight()*0.5f);
        setPosition((Gdx.graphics.getWidth() - getWidth()) / 2f, (Gdx.graphics.getHeight() - getHeight()) / 2f);
    }

    private class corroboraEvalua extends ClickListener {

        @Override
        public void clicked(InputEvent event, float x, float y) {
            double[][] mat_a = null;
            double[][] mat_b = null;
            try {
                mat_a = generateMatrix(fixString(matrix_a.getText()));
                matrix_a.setColor(Color.GREEN);
            } catch (Exception e) {
                matrix_a.setColor(Color.RED);
            }
            try {
                mat_b = generateMatrix(fixString(matrix_b.getText()));
                matrix_b.setColor(Color.GREEN);
            } catch (Exception e) {
                matrix_b.setColor(Color.RED);
                e.printStackTrace();
            }
            try {
                matrixOperation = new MatrixOperation(mat_a, mat_b);
                switch ((Operation) opciones.getSelected()) {
                    case SUMA:
                    case RESTA:
                        if (!matrixOperation.areMatrixEqualSized())
                            throw new Exception("Estas matrices no se pueden sumar/restar");
                        // We clean the table
                        showTable.clear();
                        ans = matrixOperation.a_s_Matrix((Operation) opciones.getSelected());
                        // Now we display it on screen!
                        for (double[] row : ans) {
                            for (double element : row) {
                                showTable.add(new VisLabel(String.valueOf(element))).center().pad(1f);
                            }
                            showTable.row();
                        }
                        splitPane.setSplitAmount(0.2f);
                        break;
                    case MULTIPLICACION:
                        if (!matrixOperation.areMatrixMultiplicable())
                            throw new Exception("Estas matrices no se pueden multiplicar");
                        // We clean the table
                        showTable.clear();
                        ans = matrixOperation.multiplyMatrix();
                        // Now we display it on screen!
                        for (double[] row : ans) {
                            for (double element : row) {
                                showTable.add(new VisLabel(String.valueOf(element))).center().pad(1f);
                            }
                            showTable.row();
                        }
                        splitPane.setSplitAmount(0.2f);
                        break;
                }
            } catch (Exception e) {
                mensaje.setText(e.getMessage());
            }
        }

        private String fixString(String matrix) {
            return matrix.replace(" ", "");
        }

        private double[][] generateMatrix(String input) throws Exception {
            ArrayList<String> rowHolder = new ArrayList(0);
            ArrayList<String> numbers = new ArrayList(0);
            int rows = 0; // This one will be modified to get the rows of the desired matrix
            int cols = 0; // This one will hold... you got it! columns!
            int colsVerifier = 0;
            boolean colsCounted = false;
            StringTokenizer stk = new StringTokenizer(input, "[]", false);
            while (stk.hasMoreElements()) {
                String tmp = stk.nextToken();
                if (tmp.matches("(.)*(\\d)+(.)*")) {
                    rows++;
                    rowHolder.add(tmp);
                }
            }
            for (String s : rowHolder) { // We iterate over all the "rows" now
                stk = new StringTokenizer(s, ",", false);
                while (stk.hasMoreElements()) { // In the first place we will count how many "columns" do we have
                    if (!colsCounted)
                        cols++;
                    else
                        colsVerifier++;
                    numbers.add(stk.nextToken());
                }
                if (!colsCounted)
                    colsCounted = true;
                else if (cols != colsVerifier)
                    throw new Exception("Inconsistent Matrix");
                numbers.add("nr"); // This will generate errors, that's when we know our matrices are malformed
                colsVerifier = 0;
            }
            double[][] matrix = new double[rows][cols];
            int index = 0;
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    matrix[i][j] = Double.parseDouble(numbers.get(index) + "d");
                    index++;
                }
                index++;
            }
            return matrix;
        }

    }
}
