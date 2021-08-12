package ru.volod878.rest_service_osm.models;

import java.net.MalformedURLException;
import java.net.URL;


public class OpenStreetMap {

    public static URL createURL(String value) {
        String link = "https://nominatim.openstreetmap.org/search?" +
            value +
            "&country=russia" +
            "&format=json" +
            "&polygon_geojson=1";

        try {
            return new URL(link);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;  //todo заменить null на исключение
        }
    }
}
