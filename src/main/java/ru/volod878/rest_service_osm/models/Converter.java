package ru.volod878.rest_service_osm.models;

import org.json.JSONObject;

import java.util.List;

public class Converter {

    public static List<Object> convertJSONtoList(JSONObject jsonObject) {
        return jsonObject.getJSONObject("geojson")
                .getJSONArray("coordinates")
                .toList();
    }
}
