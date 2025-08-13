package org.airpenthouse.GoTel.controllers;

import org.airpenthouse.GoTel.dtos.cities.CitiesRequest;
import org.airpenthouse.GoTel.dtos.cities.CreateNewCityRequest;
import org.airpenthouse.GoTel.dtos.cities.UpdateCityNameRequest;
import org.airpenthouse.GoTel.dtos.cities.UpdateCityPopulation;
import org.airpenthouse.GoTel.services.city.CitiesService;
import org.airpenthouse.GoTel.util.Log;
import org.airpenthouse.GoTel.util.PropertiesUtilManager;
import org.airpenthouse.GoTel.util.mappers.CitiesMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.Set;


@RestController
@RequestMapping("/api/cities")
public class CitiesController extends CitiesService {

    private final CitiesMapper citiesMapper;

    public CitiesController() {
        citiesMapper = getMapper();
    }

    private Set<CitiesRequest> entities;

    @GetMapping("/findAllCites")
    public ResponseEntity<Set<CitiesRequest>> getAllCitiesController() {

        ResponseEntity<Set<CitiesRequest>> result;
        CitiesService.SERVICE_TRIGGER = "GET_ALL_CITIES_DATA";
        entities = initializeCitiesService();

        if (entities.isEmpty()) {
            result = ResponseEntity.notFound().build();
        } else {
            result = ResponseEntity.ok(entities);
        }
        return result;
    }

    @GetMapping("/findCityByName/{cityName}")
    public ResponseEntity<Set<CitiesRequest>> findCityByName(@PathVariable String cityName) {
        CitiesService.SERVICE_TRIGGER = "FIND_CITY_INFO_BY_NAME";


        PropertiesUtilManager.setProperties("cityName", cityName);
        System.out.println(PropertiesUtilManager.getPropertiesValue("cityName"));
        entities = initializeCitiesService();
        if (entities.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(entities);
        }
    }

    @PostMapping("/insertCity")
    public ResponseEntity<Set<CitiesRequest>> insertCities(@RequestBody CreateNewCityRequest request, UriComponentsBuilder uriBuilder) {
        CitiesService.SERVICE_TRIGGER = "ADD_CITY";

        var cityRequest = citiesMapper.toCitiesEntity(request);
        PropertiesUtilManager.setProperties("countryName1", cityRequest.getCountryName());
        PropertiesUtilManager.setProperties("cityName", cityRequest.getCityName());
        PropertiesUtilManager.setProperties("districtName", cityRequest.getDistrict());
        entities = initializeCitiesService();

        if (entities.isEmpty()) {
            return ResponseEntity.badRequest().build();
        } else {
            // creating status 201
            var uri = uriBuilder.path("/api/cities/findCityByName/{cityName}").buildAndExpand(cityRequest.getCityName()).toUri();
            return ResponseEntity.created(uri).body(entities);
        }
    }

    @GetMapping("/findByCountryName/{countryName}")
    public ResponseEntity<Set<CitiesRequest>> findCitiesByCountries(@PathVariable String countryName) {
        CitiesService.SERVICE_TRIGGER = "FIND_CITIES_BY_COUNTRY";
        Log.info(countryName);
        PropertiesUtilManager.setProperties("countryName", countryName);
        entities = initializeCitiesService();

        if (entities.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(entities);
        }
    }

    @GetMapping("/findCitiesByDistrict/{district}")
    public ResponseEntity<Set<CitiesRequest>> findCitiesByDistrict(@PathVariable String district) {

        Log.info("Executing the find cities by district API ");
        CitiesService.SERVICE_TRIGGER = "GET_CITIES_BY_DISTRICT";
        PropertiesUtilManager.setProperties("districtName", district);
        Log.info("District searched is " + district);
        entities = initializeCitiesService();

        if (entities.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(entities);
        }
    }

    @PutMapping("/updateCityName")
    public ResponseEntity<Void> updateCityName(@RequestBody UpdateCityNameRequest request) {
        CitiesService.SERVICE_TRIGGER = "UPDATE_CITY_NAME";

        var dto = citiesMapper.toCitiesEntity(request);
        PropertiesUtilManager.setProperties("cityName", dto.getCityName());
        PropertiesUtilManager.setProperties("newCityName", dto.getNewLanguageName());
        entities = initializeCitiesService();

        if (entities.isEmpty())
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.noContent().build();
    }


    @PutMapping("/updateCityPopulation")
    public ResponseEntity<Void> updateCitiesPopulation(@RequestBody UpdateCityPopulation request) {
        CitiesService.SERVICE_TRIGGER = "UPDATE_CITY_NAME";
        var dto = citiesMapper.toCitiesEntity(request);
        PropertiesUtilManager.setProperties("cityName", dto.getCityName());
        PropertiesUtilManager.setProperties("newCityName", String.valueOf(dto.getPopulation()));
        entities = initializeCitiesService();

        if (entities.isEmpty())
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.noContent().build();
    }

}