package sample;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import org.ejml.simple.SimpleMatrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Controller {

    @FXML
    private TextField point_x;

    @FXML
    private TextField point_y;

    @FXML
    private TextField trans_matrix_a;

    @FXML
    private TextField trans_matrix_b;

    @FXML
    private TextField trans_matrix_c;

    @FXML
    private TextField trans_matrix_d;

    @FXML
    private Button point_button;

    @FXML
    private LineChart<NumberAxis, NumberAxis> lineChart;

    @FXML
    private TextField line_x1;

    @FXML
    private TextField line_y1;

    @FXML
    private TextField line_x2;

    @FXML
    private TextField line_y2;

    @FXML
    private Button line_button;

    @FXML
    private TextField triangle_x1;

    @FXML
    private TextField triangle_y1;

    @FXML
    private TextField triangle_x2;

    @FXML
    private TextField triangle_y2;

    @FXML
    private Button triangle_button;

    @FXML
    private TextField triangle_x3;

    @FXML
    private TextField triangle_y3;

    @FXML
    private TextField trans_matrix_a1;

    @FXML
    private TextField trans_matrix_b1;

    @FXML
    private TextField trans_matrix_a2;

    @FXML
    private TextField trans_matrix_b2;

    @FXML
    private TextField trans_matrix_a3;

    @FXML
    private TextField trans_matrix_b3;

    @FXML
    private CheckBox check;


    @FXML
    public void initialize() {
        point_button.setOnAction(event -> {
            float[][] pointMatrix = getPointMatrix();
            float[][] transformationMatrix = getTransformationMatrix();
            displayPoint(transformationMatrix, pointMatrix);
        });

        line_button.setOnAction(event -> {
            float[][] transformationMatrix = getTransformationMatrix();
            float[][] lineMatrix = getLineMatrix();
            displayLine(transformationMatrix, lineMatrix);
        });

        triangle_button.setOnAction(event -> {

            float[][] triangleMatrix = getTriangleMatrix();
            if (check.isSelected()) {
                float[][] transformationMatrix = getExtendedMatrix();
                displayTriangle(transformationMatrix, triangleMatrix, true);
            } else {
                float[][] transformationMatrix = getTransformationMatrix();
                displayTriangle(transformationMatrix, triangleMatrix, false);
            }
        });
    }

    private void displayPoint(float[][] transformationMatrix, float[][] pointMatrix) {
        lineChart.getData().clear();
        XYChart.Series mainPointSeries = new XYChart.Series();
        mainPointSeries.setName("First point");
        mainPointSeries.getData().add(new XYChart.Data<>(pointMatrix[0][0], pointMatrix[0][1]));
        SimpleMatrix matrix1 = new SimpleMatrix(transformationMatrix);
        SimpleMatrix matrix2 = new SimpleMatrix(pointMatrix);
        SimpleMatrix result = matrix2.mult(matrix1);

        XYChart.Series newPointSeries = new XYChart.Series();
        newPointSeries.setName("New Point");
        newPointSeries.getData().add(new XYChart.Data<>(result.get(0), result.get(1)));

        lineChart.getData().addAll(mainPointSeries, newPointSeries);
    }

    private void displayLine(float[][] transformationMatrix, float[][] lineMatrix) {
        lineChart.getData().clear();
        XYChart.Series mainLineSeries = new XYChart.Series();
        mainLineSeries.setName("First line");
        mainLineSeries.getData().add(new XYChart.Data<>(lineMatrix[0][0], lineMatrix[0][1]));
        mainLineSeries.getData().add(new XYChart.Data<>(lineMatrix[1][0], lineMatrix[1][1]));

        float[][] pointOne = new float[1][2];
        float[][] pointTwo = new float[1][2];
        pointOne[0][0] = lineMatrix[0][0];
        pointOne[0][1] = lineMatrix[0][1];
        pointTwo[0][0] = lineMatrix[1][0];
        pointTwo[0][1] = lineMatrix[1][1];

        SimpleMatrix matrix1 = new SimpleMatrix(transformationMatrix);
        SimpleMatrix matrix2 = new SimpleMatrix(pointOne);
        SimpleMatrix matrix3 = new SimpleMatrix(pointTwo);
        SimpleMatrix result1 = matrix2.mult(matrix1);
        SimpleMatrix result2 = matrix3.mult(matrix1);

        XYChart.Series newLineSeries = new XYChart.Series();
        newLineSeries.setName("New Line");
        newLineSeries.getData().add(new XYChart.Data<>(result1.get(0), result1.get(1)));
        newLineSeries.getData().add(new XYChart.Data<>(result2.get(0), result2.get(1)));

        lineChart.getData().addAll(mainLineSeries, newLineSeries);
    }

    private void displayTriangle(float[][] transformationMatrix, float[][] triangleMatrix, boolean expandedTransMatr) {
        lineChart.getData().clear();
        XYChart.Series mainLineSeries = new XYChart.Series();
        XYChart.Series thirdLineSeries = new XYChart.Series();
        XYChart.Series newLineSeries = new XYChart.Series();
        XYChart.Series newThirdLineSeries = new XYChart.Series();
        mainLineSeries.setName("First triangle");
        filterMatrix(triangleMatrix);
        mainLineSeries.getData().add(new XYChart.Data<>(triangleMatrix[0][0], triangleMatrix[0][1]));
        mainLineSeries.getData().add(new XYChart.Data<>(triangleMatrix[1][0], triangleMatrix[1][1]));
        mainLineSeries.getData().add(new XYChart.Data<>(triangleMatrix[2][0], triangleMatrix[2][1]));
        if (triangleMatrix[1][0] > triangleMatrix[2][0] || triangleMatrix[1][0] != triangleMatrix[2][0]) {
            thirdLineSeries.getData().add(new XYChart.Data<>(triangleMatrix[0][0], triangleMatrix[0][1]));
            thirdLineSeries.getData().add(new XYChart.Data<>(triangleMatrix[1][0], triangleMatrix[1][1]));
        } else {
            thirdLineSeries.getData().add(new XYChart.Data<>(triangleMatrix[0][0], triangleMatrix[0][1]));
            thirdLineSeries.getData().add(new XYChart.Data<>(triangleMatrix[2][0], triangleMatrix[2][1]));
        }

        float[][] pointOne;
        float[][] pointTwo;
        float[][] pointThree;
        if (expandedTransMatr) {
            pointOne = new float[1][3];
            pointTwo = new float[1][3];
            pointThree = new float[1][3];
            pointOne[0][0] = triangleMatrix[0][0];
            pointOne[0][1] = triangleMatrix[0][1];
            pointOne[0][2] = 1;
            pointTwo[0][0] = triangleMatrix[1][0];
            pointTwo[0][1] = triangleMatrix[1][1];
            pointTwo[0][2] = 1;
            pointThree[0][0] = triangleMatrix[2][0];
            pointThree[0][1] = triangleMatrix[2][1];
            pointThree[0][2] = 1;

            SimpleMatrix matrix1 = new SimpleMatrix(transformationMatrix);
            SimpleMatrix matrix2 = new SimpleMatrix(pointOne);
            SimpleMatrix matrix3 = new SimpleMatrix(pointTwo);
            SimpleMatrix matrix4 = new SimpleMatrix(pointThree);
            SimpleMatrix result1 = matrix2.mult(matrix1);
            SimpleMatrix result2 = matrix3.mult(matrix1);
            SimpleMatrix result3 = matrix4.mult(matrix1);
            ArrayList<SimpleMatrix> matrices = new ArrayList<>(Arrays.asList(result1, result2, result3));
            matrices.sort((v1, v2) -> (int) (v1.get(0) - v2.get(0)));

            newLineSeries = new XYChart.Series();
            newLineSeries.setName("New Triangle");
            newLineSeries.getData().add(new XYChart.Data<>(matrices.get(0).get(0), matrices.get(0).get(1)));
            newLineSeries.getData().add(new XYChart.Data<>(matrices.get(1).get(0), matrices.get(1).get(1)));
            newLineSeries.getData().add(new XYChart.Data<>(matrices.get(2).get(0), matrices.get(2).get(1)));
            newThirdLineSeries = new XYChart.Series();

            newThirdLineSeries.getData().add(new XYChart.Data<>(matrices.get(0).get(0), matrices.get(0).get(1)));
            newThirdLineSeries.getData().add(new XYChart.Data<>(matrices.get(2).get(0), matrices.get(2).get(1)));
        } else {
            SimpleMatrix matrix1 = new SimpleMatrix(transformationMatrix);
            SimpleMatrix matrix2 = new SimpleMatrix(triangleMatrix);
            System.out.println(Arrays.deepToString(triangleMatrix));
            SimpleMatrix result1 = matrix2.mult(matrix1);
            System.out.println(result1);
            filterSimpleMatrix(result1);
            System.out.println(result1);

            newLineSeries = new XYChart.Series();
            newLineSeries.setName("New Triangle");
            newLineSeries.getData().add(new XYChart.Data<>(result1.get(0), result1.get(1)));
            newLineSeries.getData().add(new XYChart.Data<>(result1.get(2), result1.get(3)));
            newLineSeries.getData().add(new XYChart.Data<>(result1.get(4), result1.get(5)));
            newThirdLineSeries = new XYChart.Series();

            newThirdLineSeries.getData().add(new XYChart.Data<>(result1.get(0), result1.get(1)));
            newThirdLineSeries.getData().add(new XYChart.Data<>(result1.get(4), result1.get(5)));

        }
        lineChart.getData().addAll(mainLineSeries, newLineSeries, thirdLineSeries, newThirdLineSeries);
    }

    private float[][] getTransformationMatrix() {
        float[][] transformationMatrix = new float[2][2];
        transformationMatrix[0][0] = Float.parseFloat(trans_matrix_a.getText());
        transformationMatrix[0][1] = Float.parseFloat(trans_matrix_b.getText());
        transformationMatrix[1][0] = Float.parseFloat(trans_matrix_c.getText());
        transformationMatrix[1][1] = Float.parseFloat(trans_matrix_d.getText());
        return transformationMatrix;
    }

    private float[][] getPointMatrix() {
        float x = Float.parseFloat(point_x.getText());
        float y = Float.parseFloat(point_y.getText());
        float[][] pointMatrix = new float[1][2];
        pointMatrix[0][0] = x;
        pointMatrix[0][1] = y;
        return pointMatrix;
    }

    private float[][] getLineMatrix() {
        float x1 = Float.parseFloat(line_x1.getText());
        float y1 = Float.parseFloat(line_y1.getText());
        float x2 = Float.parseFloat(line_x2.getText());
        float y2 = Float.parseFloat(line_y2.getText());
        float[][] lineMatrix = new float[2][2];
        lineMatrix[0][0] = x1;
        lineMatrix[0][1] = y1;
        lineMatrix[1][0] = x2;
        lineMatrix[1][1] = y2;

        return lineMatrix;
    }

    private float[][] getTriangleMatrix() {
        float x1 = Float.parseFloat(triangle_x1.getText());
        float y1 = Float.parseFloat(triangle_y1.getText());
        float x2 = Float.parseFloat(triangle_x2.getText());
        float y2 = Float.parseFloat(triangle_y2.getText());
        float x3 = Float.parseFloat(triangle_x3.getText());
        float y3 = Float.parseFloat(triangle_y3.getText());

        float[][] triangleMatrix = new float[3][2];

        triangleMatrix[0][0] = x1;
        triangleMatrix[0][1] = y1;
        triangleMatrix[1][0] = x2;
        triangleMatrix[1][1] = y2;
        triangleMatrix[2][0] = x3;
        triangleMatrix[2][1] = y3;

        return triangleMatrix;
    }

    private float[][] getExtendedMatrix() {
        float x1 = Float.parseFloat(trans_matrix_a1.getText());
        float y1 = Float.parseFloat(trans_matrix_b1.getText());
        float x2 = Float.parseFloat(trans_matrix_a2.getText());
        float y2 = Float.parseFloat(trans_matrix_b2.getText());
        float x3 = Float.parseFloat(trans_matrix_a3.getText());
        float y3 = Float.parseFloat(trans_matrix_b3.getText());

        float[][] extendedMatrix = new float[3][2];

        extendedMatrix[0][0] = x1;
        extendedMatrix[0][1] = y1;
        extendedMatrix[1][0] = x2;
        extendedMatrix[1][1] = y2;
        extendedMatrix[2][0] = x3;
        extendedMatrix[2][1] = y3;

        return extendedMatrix;
    }

    private float[][] filterMatrix(float[][] matrix) {
        for (int i = 0, j = 0; i < matrix.length - 1; i++) {
            for (int k = 0; k < matrix.length; k++) {
                if (matrix[i][j] < matrix[k][j]) {
                    float temp = matrix[i][j];
                    float tempY = matrix[i][j + 1];
                    matrix[i][j + 1] = matrix[k][j + 1];
                    matrix[i][j] = matrix[k][j];
                    matrix[k][j] = temp;
                    matrix[k][j + 1] = tempY;
                }
            }
        }
        return matrix;
    }

    private void filterSimpleMatrix(SimpleMatrix simpleMatrix){
        for (int i = 0; i < simpleMatrix.getNumElements(); i += 2){
            for (int j = 0; j < simpleMatrix.getNumElements(); j += 2){
                if (simpleMatrix.get(i) < simpleMatrix.get(j)){
                    double temp = simpleMatrix.get(i);
                    double tempY = simpleMatrix.get(i + 1);
                    simpleMatrix.set(i, simpleMatrix.get(j));
                    simpleMatrix.set(i + 1, simpleMatrix.get(j + 1));
                    simpleMatrix.set(j, temp);
                    simpleMatrix.set(j + 1, tempY);
                }
            }
        }
    }
}
