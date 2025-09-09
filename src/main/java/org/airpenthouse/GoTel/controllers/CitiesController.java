package org.airpenthouse.GoTel.controllers;

import org.airpenthouse.GoTel.dtos.cities.CitiesRequest;
import org.airpenthouse.GoTel.dtos.cities.CreateNewCityRequest;
import org.airpenthouse.GoTel.dtos.cities.UpdateCityNameRequest;
import org.airpenthouse.GoTel.dtos.cities.UpdateCityPopulation;
import org.airpenthouse.GoTel.services.city.CitiesService;
import org.airpenthouse.GoTel.util.Log;
import org.airpenthouse.GoTel.util.PropertiesUtilManager;
import org.airpenthouse.GoTel.util.executors.CitiesExecutors;
import org.airpenthouse.GoTel.util.mappers.CitiesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Set;


@RestController
@RequestMapping("/api/cities")
@CrossOrigin(origins="http://localhost:4200")
public class CitiesController {
    @Autowired
    public CitiesMapper citiesMapper;

    private Set<CitiesRequest> entities;
    @Autowired
    public CitiesService executor;

    @GetMapping("/findAllCites")
    public ResponseEntity<Set<CitiesRequest>> getAllCitiesController() {
        CitiesExecutors.setMapper(citiesMapper);
        ResponseEntity<Set<CitiesRequest>> result;
        CitiesService.SERVICE_TRIGGER = "GET_ALL_CITIES_DATA";
        entities = executor.initializeCitiesService(true, null);

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
        CitiesExecutors.setMapper(citiesMapper);

        PropertiesUtilManager.setProperties("cityName", cityName);
        System.out.println(PropertiesUtilManager.getPropertiesValue("cityName"));
        entities = executor.initializeCitiesService(true, null);
        if (entities.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(entities);
        }
    }

    @PostMapping("{memberUsername}/insertCity")
    public ResponseEntity<Set<CitiesRequest>> insertCities(@PathVariable String memberUsername, @RequestHeader(name = "x-auth-membership-token") String memberToken, @RequestBody CreateNewCityRequest request, UriComponentsBuilder uriBuilder) {
        CitiesService.SERVICE_TRIGGER = "ADD_CITY";
        CitiesExecutors.setMapper(citiesMapper);
        var cityRequest = citiesMapper.toCitiesEntity(request);
        PropertiesUtilManager.setProperties("memberToken", memberToken);
        PropertiesUtilManager.setProperties("memberUsername", memberUsername);
        entities = executor.initializeCitiesService(false, cityRequest);
        if (executor.checkMemberShipStatusAndTokenMatch()) {
            if (entities.isEmpty()) {
                return ResponseEntity.badRequest().build();
            } else {
                // creating status 201
                var uri = uriBuilder.path("/api/cities/findCityByName/{cityName}").buildAndExpand(cityRequest.getCityName()).toUri();
                return ResponseEntity.created(uri).body(entities);
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/findByCountryName/{countryName}")
    public ResponseEntity<Set<CitiesRequest>> findCitiesByCountries(@PathVariable String countryName) {
        CitiesExecutors.setMapper(citiesMapper);
        CitiesService.SERVICE_TRIGGER = "FIND_CITIES_BY_COUNTRY";
        Log.info(countryName);
        PropertiesUtilManager.setProperties("countryName", countryName);
        entities = executor.initializeCitiesService(true, null);

        if (entities.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(entities);
        }
    }

    @GetMapping("/findCitiesByDistrict/{district}")
    public ResponseEntity<Set<CitiesRequest>> findCitiesByDistrict(@PathVariable String district) {
        CitiesExecutors.setMapper(citiesMapper);
        Log.info("Executing the find cities by district API ");
        CitiesService.SERVICE_TRIGGER = "GET_CITIES_BY_DISTRICT";
        PropertiesUtilManager.setProperties("districtName", district);
        Log.info("District searched is " + district);
        entities = executor.initializeCitiesService(true, null);

        if (entities.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(entities);
        }
    }

    @PostMapping("/{memberUsername}/updateCityName")
    public ResponseEntity<Void> updateCityName(@PathVariable String memberUsername, @RequestHeader(name = "x-auth-membership-token") String memberToken, @RequestBody UpdateCityNameRequest request) {
        CitiesService.SERVICE_TRIGGER = "UPDATE_CITY_NAME";
        CitiesExecutors.setMapper(citiesMapper);
        var dto = citiesMapper.toCitiesEntity(request);
        PropertiesUtilManager.setProperties("memberToken", memberToken);
        PropertiesUtilManager.setProperties("memberUsername", memberUsername);
        if (executor.checkMemberShipStatusAndTokenMatch()) {
            entities = executor.initializeCitiesService(false, dto);
            if (entities.isEmpty())
                return ResponseEntity.badRequest().build();
            else
                return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


    @PostMapping("{memberUsername}/updateCityPopulation")
    public ResponseEntity<Void> updateCitiesPopulation(@RequestHeader(name = "x-auth-membership-token") String memberToken, @PathVariable String memberUsername, @RequestBody UpdateCityPopulation request) {
        CitiesService.SERVICE_TRIGGER = "UPDATE_CITY_NAME";
        CitiesExecutors.setMapper(citiesMapper);
        var dto = citiesMapper.toCitiesEntity(request);
        entities = executor.initializeCitiesService(false, dto);
        PropertiesUtilManager.setProperties("memberToken", memberToken);
        PropertiesUtilManager.setProperties("memberUsername", memberUsername);

        if (executor.checkMemberShipStatusAndTokenMatch()) {
            entities = executor.initializeCitiesService(false, dto);
            if (entities.isEmpty())
                return ResponseEntity.badRequest().build();
            else
                return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

}