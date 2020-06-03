package com.coronafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;

import java.text.DecimalFormat;

import java.awt.event.ActionEvent;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    Button getData;
    @FXML
    TextField country;
    @FXML
    Label totalLabel;
    @FXML
    Label deathsLabel;
    @FXML
    Label recoveredLabel;
    @FXML
    Label activeLabel;
    @FXML
    Label title;
    @FXML
    AnchorPane background;

    @FXML
    BarChart<?, ?> graf;
    @FXML
    CategoryAxis x;
    @FXML
    NumberAxis y;

    @FXML
    AnchorPane graphPane;
    @FXML
    Button graphButton;
    @FXML
    ComboBox<String> comboBox;


    public void run() {

        background.setBackground(Background.EMPTY);
        URL url;
        String dataUrl;

        try {
            String inputText = country.getText();
            if (country.getText().isEmpty()) {
                dataUrl = "https://corona.lmao.ninja/v2/all";
            } else {
                dataUrl = "https://corona.lmao.ninja/v2/countries/" + inputText;
            }

            url = new URL(dataUrl);
            URLConnection conn = url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            String fileName = "countries.json";
            File file = new File("src/main/resources/data/" + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            while ((inputLine = br.readLine()) != null) {
                bw.write(inputLine);
            }

            bw.close();
            br.close();
            splitData();

        } catch (FileNotFoundException e) {
            totalLabel.setText("Spolu: -");
            deathsLabel.setText("Úmrtia: -");
            recoveredLabel.setText("Zotavení: -");
            activeLabel.setText("Nakazení: -");
            country.setText("Krajina neexistuje");

        } catch (IOException ignored) {
        }

    }

    private void splitData() throws IOException {
        File file = new File("src/main/resources/data/countries.json");
        BufferedReader reader = new BufferedReader(new FileReader(file));

        String data = reader.readLine();
        String[] totalDirty = data.split("\"cases\":");
        String[] total = totalDirty[1].split(",");
        totalLabel.setText("Spolu: " + total[0]);

        String[] deathsDirty = totalDirty[1].split("\"deaths\":");
        String[] deaths = deathsDirty[1].split(",");
        deathsLabel.setText("Úmrtia: " + deaths[0]);

        String[] recoveredDirty = deathsDirty[1].split("\"recovered\":");
        String[] recovered = recoveredDirty[1].split(",");
        recoveredLabel.setText("Zotavení: " + recovered[0]);

        String[] activeDirty = recoveredDirty[1].split("\"active\":");
        String[] active = activeDirty[1].split(",");
        activeLabel.setText("Nakazení: " + active[0]);
    }

    public void exit() {
        System.exit(0);
    }

    private XYChart.Series set1 = new XYChart.Series<>();

    public void graph() throws IOException {
        graphPane.setVisible(true);

        x.setAnimated(false);
        graphButton.setVisible(false);
        graf.setAnimated(false);


    }

    public void hideGraph() {
        graphPane.setVisible(false);
        graf.setAnimated(true);
        graphButton.setVisible(true);
    }

    String CBValue = "";

    public void parseCBValue() {
        y.setLabel(comboBox.getValue());
        switch (comboBox.getValue()) {
            case "Spolu":
                CBValue = "cases";
                break;
            case "Úmrtia":
                CBValue = "deaths";
                break;
            case "Zotavení":
                CBValue = "recovered";
                break;
            case "Nakazení":
                CBValue = "active";
                break;
            case "Testovaní":
                CBValue = "tests";
                break;
            case "Nakazení dnes":
                CBValue = "todayCases";
                break;
            case "Úmrtia dnes":
                CBValue = "todayDeaths";
                break;
            case "Prípadov na milión":
                CBValue = "casesPerOneMillion";
                break;
            case "Úmrtia na milión":
                CBValue = "deathsPerOneMillion";
                break;
            case "Testov na milión":
                CBValue = "testsPerOneMillion";
                break;
        }
        Border border = new Border();
        border.getBorder();
        try {
            border.splitBorder(CBValue);
        } catch (IOException e) {
            e.printStackTrace();
        }
        set1.getData().clear();
        set1.getData().add(new XYChart.Data("Slovensko", border.activeCases.get(0)));
        set1.getData().add(new XYChart.Data("Česko", border.activeCases.get(1)));
        set1.getData().add(new XYChart.Data("Poľsko", border.activeCases.get(2)));
        set1.getData().add(new XYChart.Data("Ukrajina", border.activeCases.get(3)));
        set1.getData().add(new XYChart.Data("Maďarsko", border.activeCases.get(4)));
        set1.getData().add(new XYChart.Data("Rakúsko", border.activeCases.get(5)));
        try {
            graf.getData().addAll(set1);
        } catch (IllegalArgumentException ignored){}



    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> options = FXCollections.observableArrayList(
                "Spolu",
                "Úmrtia",
                "Zotavení",
                "Nakazení",
                "Testovaní",
                "Nakazení dnes",
                "Úmrtia dnes",
                "Prípadov na milión",
                "Úmrtia na milión",
                "Testov na milión"
        );
        comboBox.getItems().addAll(options);
    }
}
