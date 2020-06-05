package com.coronafx;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class Border {

    ArrayList<Integer> dataList = new ArrayList<Integer>();
    private String[] countries = {"slovakia", "czechia", "poland", "ukraine", "hungary", "austria"};
    void getBorder() {
        for (String country : countries) {
            URL url;
            String dataUrl;
            try {
                dataUrl = "https://corona.lmao.ninja/v2/countries/" + country;

                url = new URL(dataUrl);
                URLConnection conn = url.openConnection();
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                String fileName = country + ".json";
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
            } catch (IOException ignored) {

            }
        }
    }

    void splitBorder(String CBValue) throws IOException {
        String[] info;
        String[] infoDirty = {};
        //int infoRec = 0;
        for (String country : countries) {
            File file = new File("src/main/resources/data/" + country + ".json");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String data = reader.readLine();
            switch (CBValue) {
                default:
                    infoDirty = data.split("\"active\":");
                    break;
                case "cases":
                    infoDirty = data.split("\"cases\":");
                    break;
                case "active":
                    infoDirty = data.split("\"active\":");
                    break;
                case "deaths":
                    infoDirty = data.split("\"deaths\":");
                    break;
                case "recovered":
                    infoDirty = data.split("\"recovered\":");
                    break;
                case "tests":
                    infoDirty = data.split("\"tests\":");
                    break;
                case "todayCases":
                    infoDirty = data.split("\"todayCases\":");
                    break;
                case "todayDeaths":
                    infoDirty = data.split("\"todayDeaths\":");
                    break;
                case "casesPerOneMillion":
                    infoDirty = data.split("\"casesPerOneMillion\":");
                    break;
                case "testsPerOneMillion":
                    infoDirty = data.split("\"testsPerOneMillion\":");
                    break;
                case "deathsPerOneMillion":
                    infoDirty = data.split("\"deathsPerOneMillion\":");
                    break;
                case "population":
                    infoDirty = data.split("\"population\":");
                    break;
               /* case "recoveredPer":
                    String[] tempDirty = data.split("\"cases\":");
                    String[] temp = tempDirty[1].split(",");
                    double tempD = Double.parseDouble(temp[0]);

                    String[] tempDirty1 = temp[1].split("\"recovered\":");
                    String[] temp1 = tempDirty1[1].split(",");
                    double tempD1 = Double.parseDouble(temp1[0]);

                    infoRec = (int) ((tempD1 / tempD) * 100);
                    break;*/
            }


            /*if (CBValue == "recoveredPer"){
                dataList.add(infoRec);
            } else{
                info = infoDirty[1].split(",");
                dataList.add(Integer.parseInt(info[0]));
            }*/
            info = infoDirty[1].split(",");
            dataList.add(Integer.parseInt(info[0]));
        }


    }
}

