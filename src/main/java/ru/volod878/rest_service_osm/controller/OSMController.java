package ru.volod878.rest_service_osm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.volod878.rest_service_osm.models.OSMObject;
import ru.volod878.rest_service_osm.service.GeoService;

@RestController
public class OSMController {

    private final GeoService geoService;

    @Autowired
    public OSMController(GeoService geoService) {
        this.geoService = geoService;
    }

    @GetMapping(value = "/osmObjects/name={name}&type={type}")
    public ResponseEntity<OSMObject> read(@PathVariable(name = "name") String name,
                                          @PathVariable(name = "type") String type) {

        final OSMObject osmObject = geoService.getOSMObject(name, type);

        return osmObject != null
                ? new ResponseEntity<>(osmObject, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}