package ru.volod878.rest_service_osm.service;

import ru.volod878.rest_service_osm.models.OSMObject;

public interface GeoService {
    /**
     * Возвращает локальный гео-объект по его наименованию и типу
     * @param name наименование гео-объекта
     * @param type тип гео-объекта
     * @return - локальный гео-объект с заданными параметрами
     */
    OSMObject getOSMObject(String name, String type);
}
