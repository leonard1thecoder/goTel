package org.airpenthouse.GoTel.controllers;

import org.airpenthouse.GoTel.dtos.countries.CountriesRequest;
import org.airpenthouse.GoTel.services.country.CountriesService;
import org.airpenthouse.GoTel.util.PropertiesUtilManager;
import org.airpenthouse.GoTel.util.executors.CountriesExecutors;
import org.airpenthouse.GoTel.util.mappers.CountriesMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequestMapping("/api/countries")
@RestController
public class CountriesController {
    @Autowired
    public CountriesMapper mapper;
    @Autowired
    public CountriesService executor;
    
    @GetMapping("/getAllCountries")
    public ResponseEntity<Set<CountriesRequest>> getCountriesEntitySet() {
        CountriesService.SERVICE_HANDLER = "FIND_ALL_COUNTRIES";
        CountriesExecutors.setMapper(mapper);
        Set<CountriesRequest> entities = executor.initializeCountriesService();
        if (entities.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(entities);
        }
    }

    @GetMapping("/getCountryByName/{countryName}")
    public ResponseEntity<Set<CountriesRequest>> getCountryByName(@PathVariable String countryName) {
        CountriesService.SERVICE_HANDLER = "FIND_COUNTRY_BY_NAME";
        CountriesExecutors.setMapper(mapper);
        PropertiesUtilManager.setProperties("countryName", countryName);
        Set<CountriesRequest> entities = executor.initializeCountriesService();
        if (entities.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(entities);
        }
    }

    @GetMapping("/getCountryByContinent/{continentName}")
    public ResponseEntity<Set<CountriesRequest>> getCountryByContinent(@PathVariable String continentName) {
        CountriesService.SERVICE_HANDLER = "FIND_COUNTRY_BY_CONTINENT";
        CountriesExecutors.setMapper(mapper);
        PropertiesUtilManager.setProperties("continentName", continentName);
        Set<CountriesRequest> entities = executor.initializeCountriesService();
        if (entities.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(entities);
        }
    }

    @GetMapping("/getCountryByRegion/{regionName}")
    public ResponseEntity<Set<CountriesRequest>> getCountryByRegion(@PathVariable String regionName) {
        CountriesService.SERVICE_HANDLER = "FIND_COUNTRY_BY_REGION";
        CountriesExecutors.setMapper(mapper);
        PropertiesUtilManager.setProperties("regionName", regionName);
        Set<CountriesRequest> entities = executor.initializeCountriesService();
        if (entities.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(entities);
        }
    }


}
