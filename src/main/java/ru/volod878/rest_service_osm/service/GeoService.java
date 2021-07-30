package ru.volod878.rest_service_osm.service;

import org.json.JSONArray;
import org.springframework.cache.annotation.Cacheable;
import ru.volod878.rest_service_osm.models.OSMObject;


public interface GeoService {
    /**
     * Возвращает гео-объект по его наименованию и типу
     * @param name - наименование гео-объекта
     * @param type - тип гео-объекта
     * @return - гео-объект с заданными параметрами
     */
    OSMObject getDataFromServiceOSM(String name, String type);

    /**
     * Возвращает локальный гео-объект по его наименованию и типу
     * @param name - наименование гео-объекта
     * @param type - тип гео-объекта
     * @return - локальный гео-объект с заданными параметрами, если такого нет, создается новый
     */
    OSMObject getOSMObject(String name, String type);
}
