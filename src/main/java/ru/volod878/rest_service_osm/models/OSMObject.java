package ru.volod878.rest_service_osm.models;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

public class OSMObject {

    List<Object> coordinates = new ArrayList<>();

    Map<String, Object> centrePoint = new HashMap<>();

    public List<Object> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Object> coordinates) {
        this.coordinates = coordinates;
    }

    public Map<String, Object> getCentrePoint() {
        return centrePoint;
    }

    public void setCentrePoint(Map<String, Object> centrePoint) {
        this.centrePoint = centrePoint;
    }


    public void generate(JSONArray jsonArray) {

        Map<Object, Integer> map = new HashMap<>();
        JSONObject jsonObject = new JSONObject();

        // Если гео-объектов больше одно, находим с наибольшим количеством координат
        if (jsonArray.length() > 1) {

            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                int count = coordinateCounter(Converter.convertJSONtoList(jsonObject));
                map.put(jsonObject, count);
            }

            int max = Collections.max(map.values());

            for (Map.Entry<Object, Integer> m : map.entrySet()) {
                if (m.getValue() == (max)) {
                    jsonObject = (JSONObject) m.getKey();
                    break;
                }
            }

        } else jsonObject = jsonArray.getJSONObject(0);

        this.setCoordinates(Converter.convertJSONtoList(jsonObject));


        // Определяем географический центр
        String lat = jsonObject.getString("lat");
        String lon = jsonObject.getString("lon");
        JSONObject centrePoint = new JSONObject();
        centrePoint.put("lat", Double.parseDouble(lat));
        centrePoint.put("lon", Double.parseDouble(lon));
        this.setCentrePoint(centrePoint.toMap());

    }

    private static int coordinateCounter(List<Object> list) {
        int count = 0;
        for (Object object: list) {
            if (object instanceof Double) {
                count++;
            } else count += coordinateCounter((List<Object>) object);
        }

        return count;
    }
}
