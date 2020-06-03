package com.coronafx;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class Border {
    ArrayList<Integer> activeCases = new ArrayList<Integer>();
    private String [] countries = {"slovakia", "czechia", "poland", "ukraine", "hungary", "austria"};
    void getBorder(){
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
               /* if (!file.exists()) {
                    file.createNewFile();
                }*/

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
        String [] active;
        String [] activeDirty;
        for (String country : countries) {
            File file = new File("src/main/resources/data/" + country + ".json");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String data = reader.readLine();
            switch (CBValue){
                default:
                    activeDirty = data.split("\"active\":");
                    break;
                case "cases":
                    activeDirty = data.split("\"cases\":");
                    break;
                case "active":
                    activeDirty = data.split("\"active\":");
                    break;
                case "deaths":
                    activeDirty = data.split("\"deaths\":");
                    break;
                case "recovered":
                    activeDirty = data.split("\"recovered\":");
                    break;
                case "tests":
                    activeDirty = data.split("\"tests\":");
                    break;
                case "todayCases":
                    activeDirty = data.split("\"todayCases\":");
                    break;
                case "todayDeaths":
                    activeDirty = data.split("\"todayDeaths\":");
                    break;
                case "casesPerOneMillion":
                    activeDirty = data.split("\"casesPerOneMillion\":");
                    break;
                case "testsPerOneMillion":
                    activeDirty = data.split("\"testsPerOneMillion\":");
                    break;
                case "deathsPerOneMillion":
                    activeDirty = data.split("\"deathsPerOneMillion\":");
                    break;

            }
            active = activeDirty[1].split(",");
            activeCases.add(Integer.parseInt(active[0]));
        }
    }
}
