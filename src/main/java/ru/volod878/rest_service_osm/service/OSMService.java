package ru.volod878.rest_service_osm.service;

import org.json.JSONArray;
import org.springframework.stereotype.Service;
import ru.volod878.rest_service_osm.models.OSMObject;
import ru.volod878.rest_service_osm.models.OpenStreetMap;
import ru.volod878.rest_service_osm.models.WebParser;

import java.net.URL;
import java.util.HashSet;
import java.util.Set;

@Service
public class OSMService implements GeoService {

    private final Set<OSMService> listOSM = new HashSet<>();
    private String name;
    private String type;
    private OSMObject osmObject;

    public OSMObject getDataFromServiceOSM(String name, String type) {
        // создаем url исходя из параметров запроса
        URL url;
        if (type.equals("region")) url = OpenStreetMap.createURL("state=" + name);
        else if (type.equals("federal")) url = OpenStreetMap.createURL("q=" + name);
        else return null;

        // загружаем Json в виде Java строки
        String resultJson = WebParser.parseUrl(url);

        // парсим полученный JSON и добавляем его в geoObjects
        JSONArray geoObjects = new JSONArray();
        WebParser.parseJSONData(resultJson, geoObjects);
        System.out.println("Количество загруженных гео-объектов: " + geoObjects.length());

        // создаем гео-объект с массивом координат и положением географического центра
        OSMObject osmObject = new OSMObject();
        osmObject.generate(geoObjects);

        // сохроняем в set для быстрого доступа
        OSMService osm = new OSMService();
        osm.name = name;
        osm.type = type;
        osm.osmObject = osmObject;
        listOSM.add(osm);

        return osmObject;
    }

    @Override
    public OSMObject getOSMObject(String name, String type) {
        if (!listOSM.isEmpty())
            for (OSMService osm : listOSM)
                if (osm.name.equals(name) && osm.type.equals(type)) return osm.osmObject;

        System.out.println("Создаем новый объект");
        return getDataFromServiceOSM(name, type);
    }
}