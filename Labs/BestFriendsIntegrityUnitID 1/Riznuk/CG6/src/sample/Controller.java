package sample;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class Controller {

    @FXML
    private Button addLine;

    @FXML
    private Button buttonCalculate;

    @FXML
    private TextField x01;

    @FXML
    private TextField y01;

    @FXML
    private TextField x02;

    @FXML
    private TextField y02;

    @FXML
    private Button removeLine;

    @FXML
    private LineChart<Number, Number> lineChart;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    private XYChart.Series<Number, Number> series = new XYChart.Series<>();
    private XYChart.Series<Number, Number> newSeries = new XYChart.Series<>();
    private ArrayList<XYChart.Series<Number, Number>> arrayOfSeries = new ArrayList<>();
    private ArrayList<XYChart.Series<Number, Number>> arrayOfNewSeries = new ArrayList<>();

    @FXML
    void addLine() {
        XYChart.Data dataOne = new XYChart.Data(Double.parseDouble(x01.getText()), Double.parseDouble(y01.getText()));
        XYChart.Data dataTwo = new XYChart.Data(Double.parseDouble(x02.getText()), Double.parseDouble(y02.getText()));
        series.getData().add(dataOne);
        series.getData().add(dataTwo);
        lineChart.getData().add(series);
        arrayOfSeries.add(series);
        series = new XYChart.Series<>();

    }

    @FXML
    void removeLine() {
        if (arrayOfSeries.size() - 1 >= 0) {
            lineChart.getData().remove(arrayOfSeries.get(arrayOfSeries.size() - 1));
            arrayOfSeries.remove(arrayOfSeries.size() - 1);
            lineChart.getData().remove(arrayOfNewSeries.get(arrayOfNewSeries.size() - 1));
            arrayOfNewSeries.remove(arrayOfNewSeries.size() - 1);
        }
    }

    @FXML
    void calculateResults() {
        for (int i = 0; i < arrayOfSeries.size(); i++) {
            int xBegin = arrayOfSeries.get(i).getData().get(0).getXValue().intValue();
            int yBegin = arrayOfSeries.get(i).getData().get(0).getYValue().intValue();
            int xEnd = arrayOfSeries.get(i).getData().get(1).getXValue().intValue();
            int yEnd = arrayOfSeries.get(i).getData().get(1).getYValue().intValue();
            int x, y, dx, dy, incx, incy, pdx, pdy, es, el, err;
            dx = xEnd - xBegin;
            dy = yEnd - yBegin;
            incx = (int) Math.signum(dx);
            incy = (int) Math.signum(dy);
            if (dx < 0) dx = -dx;
            if (dy < 0) dy = -dy;
            if (dx > dy) {
                pdx = incx;
                pdy = 0;
                es = dy;
                el = dx;
            } else {
                pdx = 0;
                pdy = incy;
                es = dx;
                el = dy;
            }
            x = xBegin;
            y = yBegin;
            err = el / 2;
            XYChart.Data data = new XYChart.Data(x, y);
            newSeries.getData().add(data);
            for (int t = 0; t < el; t++) {
                err -= es;
                if (err < 0) {
                    err += el;
                    x += incx;
                    y += incy;
                } else {
                    x += pdx;
                    y += pdy;
                }
                data = new XYChart.Data(x, y);
                newSeries.getData().add(data);
            }
        }
        lineChart.getData().add(newSeries);
        arrayOfNewSeries.add(newSeries);
        newSeries = new XYChart.Series<>();
    }

    @FXML
    void initialize() {
        series.setName("LineChart");
        lineChart.setLegendVisible(false);
        xAxis.setUpperBound(10);
        xAxis.setLowerBound(-10);
        yAxis.setUpperBound(10);
        yAxis.setLowerBound(-10);
        xAxis.setTickUnit(1);
        yAxis.setTickUnit(1);
        xAxis.setAutoRanging(false);
        yAxis.setAutoRanging(false);
    }

}
