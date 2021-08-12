package ru.volod878.rest_service_osm.models;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class WebParser {

    public static String parseUrl(URL url) {
        if (url == null) {
            return ""; //todo вернуть исключение
        }

        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()))) {

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                stringBuilder.append(inputLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }


    public static void parseJSONData(String resultJson, JSONArray products) {
        JSONArray jsonArray = new JSONArray(resultJson);

        for (int i = 0; i < jsonArray.length(); i++) {
            products.put(jsonArray.get(i));
        }
    }
}
