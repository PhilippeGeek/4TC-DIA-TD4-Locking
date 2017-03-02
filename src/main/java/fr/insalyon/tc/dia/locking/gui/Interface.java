package fr.insalyon.tc.dia.locking.gui;

import fr.insalyon.tc.dia.locking.Runner;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;

/**
 * Created by pvienne on 02/03/2017.
 */
public class Interface {

    @FXML
    private BarChart<String, Number> plot;
    @FXML
    private Slider threadsCount;
    @FXML
    private Slider storeSize;
    @FXML
    private Slider jobCount;
    @FXML
    private Button runButton;

    @FXML
    private void launchBenchmark(ActionEvent event) {
        final Runner runner = new Runner((int) threadsCount.getValue(), (int) jobCount.getValue() * 10000, (int) storeSize.getValue());
        runButton.setDisable(true);
        runner.run();
        runButton.setDisable(false);

        // Setup plot
        plot.getXAxis().setLabel("Type");
        plot.getYAxis().setLabel("Temps d'execution");

        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("Test");
        runner.getExecutionTimes().forEach((label, value)->{
            final ObservableList<XYChart.Data<String, Number>> data = series1.getData();
            data.add(new XYChart.Data<>(label, value));
        });
        plot.getData().clear();
        plot.getData().add(series1);
    }
}
