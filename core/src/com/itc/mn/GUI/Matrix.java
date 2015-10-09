package com.itc.mn.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
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
    private final VisTextButton calcular = new VisTextButton("Calcular");
    private final VisTextButton cerrar = new VisTextButton("Cerrar");
    private VisSelectBox opciones;
    private VisTextArea matrix_a, matrix_b;
    private VisTable inputTable, showTable;
    private VisSplitPane splitPane;

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
            try {
                double[][] test = generateMatrix(fixString(matrix_a.getText()));
//                for(double[] t: test)
//                    for(double e: t)
//                        System.out.println(e);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private String fixString(String matrix) {
            return matrix.replace(" ", "");
        }

        private double[][] generateMatrix(String input) {
            ArrayList<String> list = new ArrayList(0);
            int rows = 0; // This one will be modified to get the rows of the desired matrix
            int cols = 0; // This one will hold... you got it! columns!
            StringTokenizer stk = new StringTokenizer(input, "[]", false);
            while (stk.hasMoreElements()) {
                String tmp = stk.nextToken();
                if (tmp.matches("(.)*(\\d)+(.)*"))
                    rows++;
            }
            // TODO fix this mess
//            while(stk.hasMoreElements()){
//                String tmp = stk.nextToken();
//                if(tmp.equals("["))
//                    rows++;
//                else{
//                    if(tmp.equals("]")) {
//                        cols++;
//                        list.add(tmp);
//                    }
//                    else if(!tmp.equals(","))
//                        list.add(tmp+"d");
//                }
//            }
//            double[][] matrix = new double[rows][cols];
            double[][] matrix = new double[1][1];
//            int index = 0;
//            for(int i = 0; i < rows; i++) {
//                for (int j = 0; j < cols; j++) {
//                    if (!list.get(index).equals("]"))
//                        matrix[i][j] = Double.parseDouble(list.get(index));
//                    index++;
//                }
//                index++;
//            }
            return matrix;
        }

    }
}
