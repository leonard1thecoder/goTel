package org.airpenthouse.GoTel.controllers;

import org.airpenthouse.GoTel.entities.city.CitiesEntity;
import org.airpenthouse.GoTel.services.city.CitiesService;
import org.airpenthouse.GoTel.util.LOG;
import org.airpenthouse.GoTel.util.PropertiesUtilManager;
import org.airpenthouse.GoTel.util.ExecutionHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/*
For Updating and inserting Api's, I'm using @GetMapping to test entity data processing
 */
@RestController
@RequestMapping("/api/cities")
public class CitiesController extends ExecutionHandler {

    @GetMapping("/findAllCites")
    public ResponseEntity<Set<CitiesEntity>> getAllCities() {
        CitiesService.SERVICE_TRIGGER = "GET_ALL_CITIES_DATA";
        Set<CitiesEntity> setOfCitiesEntity = new HashSet<>(initializeCitiesService());
        if (setOfCitiesEntity.isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(setOfCitiesEntity);
        }
    }

    @GetMapping("/findCityByName/{cityName}")
    public ResponseEntity<Set<CitiesEntity>> findCityByName(@PathVariable String cityName) {
        CitiesService.SERVICE_TRIGGER = "FIND_CITY_INFO_BY_NAME";


        PropertiesUtilManager.setProperties("cityName", cityName);
        System.out.println(PropertiesUtilManager.getPropertiesValue("cityName"));
        Set<CitiesEntity> setOfCitiesEntity = new HashSet<>(initializeCitiesService());
        if (setOfCitiesEntity.isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(setOfCitiesEntity);
        }    }

    @GetMapping("/insertCity")
    public Map<String, CitiesEntity> insertCities(@RequestParam String countryName,@RequestParam String cityName,@RequestParam String districtName,@RequestParam long population) {
        CitiesService.SERVICE_TRIGGER = "ADD_CITY";
        PropertiesUtilManager.setProperties("countryName",countryName);
        PropertiesUtilManager.setProperties("cityName",cityName);
        PropertiesUtilManager.setProperties("districtName",districtName);
        PropertiesUtilManager.setProperties("population",String.valueOf(population));
        return new HashSet<>(initializeCitiesService()).stream().collect(Collectors.toMap(CitiesEntity::getCityName, s -> s));
    }

    @GetMapping("/findCitiesByCountryName")
    public Map<String, CitiesEntity> findCitiesByCountries(@RequestParam String countryName) {
        CitiesService.SERVICE_TRIGGER = "FIND_CITIES_BY_COUNTRY";
        System.out.println("One");
        PropertiesUtilManager.setProperties("countryName", countryName);
        return new HashSet<>(initializeCitiesService()).stream().collect(Collectors.toMap(CitiesEntity::getCityName, s -> s));
    }

    @GetMapping("/findCitiesByDistrict")
    public Map<String, CitiesEntity> findCitiesByDistrict(@RequestParam String district) {
        LOG.info("Executing the find cities by district API ");
        CitiesService.SERVICE_TRIGGER = "GET_CITIES_BY_DISTRICT";
        PropertiesUtilManager.setProperties("districtName", district);
        LOG.info("District searched is " + district);
        return new HashSet<>(initializeCitiesService()).stream().collect(Collectors.toMap(CitiesEntity::getCityName, s -> s));
    }

    @GetMapping("/updateCityName/{cityName}")
    public Map<String, CitiesEntity> updateCiteName(@RequestParam String newCityName, @PathVariable String cityName) {
        CitiesService.SERVICE_TRIGGER = "UPDATE_CITY_NAME";
        PropertiesUtilManager.setProperties("cityName", cityName);
        PropertiesUtilManager.setProperties("newCityName", newCityName);
        return new HashSet<>(initializeCitiesService()).stream().collect(Collectors.toMap(CitiesEntity::getCityName, s -> s));
    }

    @GetMapping("/updateCityPopulation/{CityName}")
    public Map<String, CitiesEntity> updateCitePopulation(@PathVariable String cityName, @RequestParam long newUpdatePopulationNo) {
        CitiesService.SERVICE_TRIGGER = "UPDATE_CITY_POPULATION";
        PropertiesUtilManager.setProperties("cityName", cityName);
        PropertiesUtilManager.setProperties("newUpdatePopulationNo", String.valueOf(newUpdatePopulationNo));
        return new HashSet<>(initializeCitiesService()).stream().collect(Collectors.toMap(CitiesEntity::getCityName, s -> s));
    }
}