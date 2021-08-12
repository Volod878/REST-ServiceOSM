package ru.volod878.rest_service_osm.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OSMObject {

    private List<?> coordinates = new ArrayList<>();

    private final Map<String, String> centrePoint = new HashMap<>();

    public List<?> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<?> coordinates) {
        this.coordinates = coordinates;
    }

    public Map<String, String> getCentrePoint() {
        return centrePoint;
    }

    public void setCentrePoint(String name, String coordinate) {
        this.centrePoint.put(name, coordinate);
    }

}
