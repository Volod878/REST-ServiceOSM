package ru.volod878.rest_service_osm.service;

import org.json.JSONArray;
import org.springframework.stereotype.Service;
import ru.volod878.rest_service_osm.models.OSMObject;
import ru.volod878.rest_service_osm.models.OpenStreetMap;
import ru.volod878.rest_service_osm.models.WebParser;

import java.math.BigDecimal;
import java.net.URL;
import java.util.*;

@Service
public class OSMService implements GeoService {

    private final Set<OSMService> listOSM = new HashSet<>();
    private String name;
    private String type;
    private OSMObject osmObject;


    @Override
    public OSMObject getOSMObject(String name, String type) {
        if (!listOSM.isEmpty())
            for (OSMService osm : listOSM)
                if (osm.name.equals(name) && osm.type.equals(type)) return osm.osmObject;

        System.out.println("Создаем новый объект");
        return getDataFromServiceOSM(name, type);
    }


    private OSMObject getDataFromServiceOSM(String name, String type) {
        // создаем url исходя из параметров запроса
        URL url;
        if (type.equals("region")) url = OpenStreetMap.createURL("state=" + name);
        else if (type.equals("federal")) url = OpenStreetMap.createURL("q=" + name);
        else return null; //todo заменить null на исключение

        // загружаем Json в виде Java строки
        String resultJson = WebParser.parseUrl(url);
        if (resultJson.equals("[]")) return null; //todo заменить null на исключение

        // парсим полученный JSON и добавляем его в geoObjects
        JSONArray geoObjects = new JSONArray();
        WebParser.parseJSONData(resultJson, geoObjects);
        System.out.println("Количество загруженных гео-объектов: " + geoObjects.length());

        // создаем гео-объект с массивом координат и положением географического центра
        osmObject = new OSMObject();
        generate(geoObjects);

        // сохраняем в set для быстрого доступа
        this.name = name;
        this.type = type;
        listOSM.add(this);

        return osmObject;
    }


    private void generate(JSONArray jsonArray) {


        List<?> objectList = jsonArray.toList();

        // находим наибольший массив координат
        objectList.stream()
                .map(o -> ((HashMap<?, ?>) o).get("geojson"))
                .map(o -> ((HashMap<?, ?>) o).get("coordinates"))
                .max(Comparator.comparing(o -> coordinateCounter((List<?>) o)))
                .ifPresent(o -> osmObject.setCoordinates((List<?>) o));

        objectList.removeIf(o -> {
            HashMap<?, ?> hashMap = (HashMap<?, ?>) ((HashMap<?, ?>) o).get("geojson");
            return !hashMap.get("coordinates").equals(osmObject.getCoordinates());
        });

        // определяем географический центр
        if (objectList.size() == 1) {
            HashMap<?, ?> geoMap = (HashMap<?, ?>) objectList.get(0);
            osmObject.setCentrePoint("lat", (String) geoMap.get("lat"));
            osmObject.setCentrePoint("lon", (String) geoMap.get("lon"));
        } else throw new RuntimeException(); //todo заменить RuntimeException на свое
    }


    private static int coordinateCounter(List<?> list) {
        int count = 0;
        for (Object object: list) {
            if (object instanceof BigDecimal) {
                count++;
            } else count += coordinateCounter((List<?>) object);
        }

        return count;
    }
}