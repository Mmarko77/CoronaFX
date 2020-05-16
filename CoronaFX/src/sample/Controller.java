package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLOutput;
import java.util.ResourceBundle;
import java.util.Stack;


public class Controller {
    @FXML Button getData;
    @FXML TextField country;
    @FXML Label totalLabel;
    @FXML Label deathsLabel;
    @FXML Label recoveredLabel;
    @FXML Label activeLabel;
    @FXML Label title;
    @FXML AnchorPane background;
    @FXML BarChart barChart;

    @FXML
    private BarChart<?, ?> graf;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;


 public void run() throws IOException {
     background.setBackground(Background.EMPTY);
     URL url;
     String dataUrl;
     try {
         String inputText = country.getText();
         if (country.getText().isEmpty()){
             dataUrl = "https://corona.lmao.ninja/v2/all";
         } else{
             dataUrl = "https://corona.lmao.ninja/v2/countries/"+inputText;
         }

         url = new URL(dataUrl);
         URLConnection conn = url.openConnection();
         BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
         String inputLine;
         String fileName = "D:\\Users\\Adrian\\Desktop\\CoronaData\\countries.json";
         File file = new File(fileName);
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
         e.printStackTrace();
         totalLabel.setText("Spolu: "+ 0);
         deathsLabel.setText("Úmrtia: "+ 0);
         recoveredLabel.setText("Zotavení: "+0);
         activeLabel.setText("Aktívny: "+ 0);
         country.setText("Krajina neexistuje");
     } catch (IOException e){
         e.printStackTrace();
     }

 }

 private void splitData() throws IOException {
     File file = new File("D:\\Users\\Adrian\\Desktop\\CoronaData\\countries.json");
     BufferedReader reader = new BufferedReader(new FileReader(file));

     String data = reader.readLine();
     String [] totalDirty = data.split("\"cases\":");
     String [] total = totalDirty[1].split(",");
     System.out.println("Total: "+total[0]);
     totalLabel.setText("Spolu: "+total[0]);

     String [] deathsDirty = totalDirty[1].split("\"deaths\":");
     String [] deaths = deathsDirty[1].split(",");
     System.out.println("Deaths: "+deaths[0]);
     deathsLabel.setText("Úmrtia: "+deaths[0]);

     String [] recoveredDirty = deathsDirty[1].split("\"recovered\":");
     String [] recovered = recoveredDirty[1].split(",");
     System.out.println("Recovered: "+recovered[0]);
     recoveredLabel.setText("Zotavení: "+recovered[0]);

     String [] activeDirty = recoveredDirty[1].split("\"active\":");
     String [] active = activeDirty[1].split(",");
     System.out.println("Active: "+active[0]);
     activeLabel.setText("Aktívny: "+active[0]);
 }

 public void exit(){
     System.exit(0);
 }

    public void graph() {
        XYChart.Series set1 = new XYChart.Series<>();

        set1.getData().add(new XYChart.Data("James", 20));
        set1.getData().add(new XYChart.Data("Alice", 40));
        set1.getData().add(new XYChart.Data("Alex", 200));

        graf.getData().addAll(set1);
    }

}
