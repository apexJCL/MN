package com.itc.mn.UI.Modules;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.I18NBundle;
import com.itc.mn.Methods.MatrixOperation;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.*;
import com.kotcrab.vis.ui.widget.tabbedpane.Tab;

import java.util.ArrayList;
import java.util.StringTokenizer;
import static com.itc.mn.Methods.MatrixOperation.Operation.*;

/**
 * Created by zero_ on 12/11/2015.
 */
public class MatrixModule extends Tab {

    private Table container;
    private final VisLabel l_opciones; // Change with i18n file
    private final VisLabel lma;
    private final VisLabel lmb;
    private final I18NBundle bundle;
    private final VisTextButton calcular;
    private VisLabel mensaje = new VisLabel("");
    private VisSelectBox opciones;
    private VisTextArea matrix_a, matrix_b;
    private VisTable inputTable, showTable;
    private VisSplitPane splitPane;
    private MatrixOperation matrixOperation;
    private double[][] ans;

    public MatrixModule(I18NBundle bundle) {
        super(true, true); // Change with i18n file
        this.bundle = bundle;
        // Create the container
        container = new Table();
        container.setBackground(VisUI.getSkin().getDrawable("window-bg"));
        // Create the table that will hold our input stuff
        inputTable = new VisTable(true);
        // Create the showTable where we will show the matrices
        showTable = new VisTable(true);
        // We create the matrix operations
        opciones = new VisSelectBox();
        // Create the array of strings that will show the options
        String[] options = {bundle.get("add"), bundle.get("substract"), bundle.get("multiply"), bundle.get("transpose"), bundle.get("gauss")};
        opciones.setItems(options);
        // We add the fields for the matrix
        matrix_a = new VisTextArea();
        matrix_b = new VisTextArea();
        matrix_a.setMessageText("[1,2,3],[4,5,6]");
        matrix_b.setMessageText("[1,2,3],[4,5,6]");
        calcular = new VisTextButton(bundle.get("l_calculate"));
        calcular.addListener(new corroboraEvalua());
        // We add the stuff
        l_opciones = new VisLabel(bundle.get("l_matrixoptions"));
        inputTable.add(l_opciones).expandX().pad(2f);
        inputTable.add(opciones).expandX().pad(2f).row();
        lma = new VisLabel(bundle.get("l_amatrix"));
        inputTable.add(lma).expandX().center().pad(1f);
        inputTable.add(matrix_a).expandX().left().pad(2f).colspan(2).row();
        lmb = new VisLabel(bundle.get("l_bmatrix"));
        inputTable.add(lmb).expandX().center().pad(1f);
        inputTable.add(matrix_b).expandX().left().pad(2f).colspan(2).row();
        inputTable.add(mensaje).expandX().center().pad(2f).colspan(2).row();
        inputTable.add(calcular).expandX().left().pad(5f).row();
        // Creating the splitpanel
        splitPane = new VisSplitPane(inputTable, showTable, false);
        // Adding the splitpane to the window
        container.add(splitPane).expand().fill();
        splitPane.setSplitAmount(1);
        // Adjusting
        container.pack();
    }

    @Override
    public String getTabTitle() {
        return bundle.get("m_matrix");
    }

    @Override
    public Table getContentTable() {
        return container;
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
                MatrixOperation.Operation op = null;
                if (opciones.getSelected().equals(bundle.get("add")))
                    op = SUMA;
                else if (opciones.getSelected().equals(bundle.get("substract")))
                    op = RESTA;
                else if(opciones.getSelected().equals(bundle.get("multiply")))
                    op = MULTIPLICACION;
                else if(opciones.getSelected().equals(bundle.get("transpose")))
                    op = TRANSPUESTA;
                else if(opciones.getSelected().equals(bundle.get("gauss")))
                    op = GAUSS;
                switch (op) {
                    case SUMA:
                    case RESTA:
                        if (!matrixOperation.areMatrixEqualSized())
                            throw new Exception(bundle.get("l_cantaddmatrix"));
                        // We clean the table
                        showTable.clear();
                        ans = matrixOperation.a_s_Matrix(op);
                        display();
                        break;
                    case MULTIPLICACION:
                        if (!matrixOperation.areMatrixMultiplicable())
                            throw new Exception(bundle.get("l_cantmultiplymatrix")); // Change with i18n file
                        // We clean the table
                        showTable.clear();
                        ans = matrixOperation.multiplyMatrix();
                        display();
                        break;
                    case TRANSPUESTA:
                        showTable.clear();
                        ans = matrixOperation.traspose();
                        display();
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
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
                    throw new Exception(bundle.get("l_inconsistentmatrix"));
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

    private void display(){
        // Now we display it on screen!
        for (double[] row : ans) {
            for (double element : row) {
                showTable.add(new VisLabel(String.valueOf(element))).center().pad(1f);
            }
            showTable.row();
        }
        splitPane.setSplitAmount(0.2f);
    }

}
