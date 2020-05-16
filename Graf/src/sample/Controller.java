package sample;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private AnchorPane scena;

    @FXML
    private BarChart<?, ?> graf;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;
    public static void main(String[] args) {}



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        XYChart.Series set1 = new XYChart.Series<>();

        set1.getData().add(new XYChart.Data("James", 20));
        set1.getData().add(new XYChart.Data("Alice", 40));
        set1.getData().add(new XYChart.Data("Alex", 60));

        graf.getData().addAll(set1);
    }

}
