package com.coronafx;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class Border {
    ArrayList<Integer> activeCases = new ArrayList<Integer>();
    String [] countries = {"slovakia", "czechia", "poland", "ukraine", "hungary", "austria"};
    public void getBorder(){
        for (int i = 0; i < countries.length; i++) {
            URL url;
            String dataUrl;
            try {
                dataUrl = "https://corona.lmao.ninja/v2/countries/" + countries[i];

                url = new URL(dataUrl);
                URLConnection conn = url.openConnection();
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                String fileName = countries[i]+".json";
                File file = new File("src/main/resources/data/"+fileName);
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
            } catch (IOException e){

            }
        }
    }

    public void splitBorder() throws IOException {
        String [] active;

        for (int i = 0; i < countries.length; i++) {
            File file = new File("src/main/resources/data/"+countries[i]+".json");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String data = reader.readLine();
            String [] activeDirty = data.split("\"active\":");
            active = activeDirty[1].split(",");
            activeCases.add(Integer.parseInt(active[0]));
        }
    }
}
