package sample;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

public class Controller {

    @FXML
    private Button addPoint;

    @FXML
    private Button buttonCalculate;

    @FXML
    private TextField x0;

    @FXML
    private TextField y0;

    @FXML
    private Button removePoint;

    @FXML
    private TextField xM1;


    @FXML
    private TextField yM1;

    @FXML
    private TextField xM2;

    @FXML
    private TextField yM2;

    @FXML
    private TextField xM3;

    @FXML
    private TextField yM3;

    @FXML
    private LineChart<String, Number> lineChart;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    private XYChart.Series<String, Number> series = new XYChart.Series<>();
    private XYChart.Series<String, Number> newSeries = new XYChart.Series<>();
    private ArrayList<Number> xPoints = new ArrayList<>();
    private ArrayList<Number> yPoints = new ArrayList<>();

    @FXML
    void addPoint() {

        series.getData().add(new XYChart.Data(Double.parseDouble(x0.getText()), Double.parseDouble(y0.getText())));
        xPoints.add(Double.parseDouble(x0.getText()));
        yPoints.add(Double.parseDouble(y0.getText()));

        if (xPoints.size() > 2 && yPoints.size() > 2) {
            newSeries.getData().clear();
            newSeries.getData().add(new XYChart.Data(xPoints.get(0), yPoints.get(0)));
            newSeries.getData().add(new XYChart.Data(xPoints.get(xPoints.size() - 1), yPoints.get(yPoints.size() - 1)));
        }
    }

    @FXML
    void removePoint() {
        if (series.getData().size() > 0) {
            series.getData().remove(series.getData().size() - 1, series.getData().size());
            xPoints.remove(xPoints.size() - 1);
            yPoints.remove(yPoints.size() - 1);
        }
        newSeries.getData().clear();
        if (xPoints.size() > 2 && yPoints.size() > 2) {
            newSeries.getData().add(new XYChart.Data(xPoints.get(0), yPoints.get(0)));
            newSeries.getData().add(new XYChart.Data(xPoints.get(xPoints.size() - 1), yPoints.get(yPoints.size() - 1)));
        }
    }

    @FXML
    void calculateResults() {
        series.getData().clear();
        newSeries.getData().clear();
        double xOld, yOld;
        for (int i = 0; i < xPoints.size(); i++) {
            xOld = xPoints.get(i).doubleValue();
            yOld = yPoints.get(i).doubleValue();
            if (!xM3.getText().isEmpty() && !yM3.getText().isEmpty()) {
                xPoints.set(i, (Double.parseDouble(xM1.getText()) * xOld + Double.parseDouble(xM2.getText()) * yOld + Double.parseDouble(xM3.getText())));
                yPoints.set(i, (Double.parseDouble(yM1.getText()) * xOld + Double.parseDouble(yM2.getText()) * yOld + Double.parseDouble(yM3.getText())));
            } else {
                xPoints.set(i, (Double.parseDouble(xM1.getText()) * xOld + Double.parseDouble(xM2.getText()) * yOld));
                yPoints.set(i, (Double.parseDouble(yM1.getText()) * xOld + Double.parseDouble(yM2.getText()) * yOld));
            }
            series.getData().add(new XYChart.Data(xPoints.get(i), yPoints.get(i)));
        }
        if (xPoints.size() > 2 && yPoints.size() > 2) {
            newSeries.getData().add(new XYChart.Data(xPoints.get(0), yPoints.get(0)));
            newSeries.getData().add(new XYChart.Data(xPoints.get(xPoints.size() - 1), yPoints.get(yPoints.size() - 1)));
        }
    }

    @FXML
    void initialize() {
        series.setName("LineChart");
        lineChart.setLegendVisible(false);
        lineChart.getData().add(series);
        lineChart.getData().add(newSeries);
        Node line = newSeries.getNode().lookup(".chart-series-line");
        Node newLine = series.getNode().lookup(".chart-series-line");
        String rgb = String.format("%d, %d, %d",
                0,
                0,
                0);

        line.setStyle("-fx-stroke: rgba(" + rgb + ", 1.0);");
        newLine.setStyle("-fx-stroke: rgba(" + rgb + ", 1.0);");
        /*lineChart.setCreateSymbols(false);*/
    }

}
