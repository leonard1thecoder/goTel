package org.airpenthouse.GoTel.controllers;

import org.airpenthouse.GoTel.dtos.countries.CountriesRequest;
import org.airpenthouse.GoTel.services.country.CountriesService;
import org.airpenthouse.GoTel.util.PropertiesUtilManager;
import org.airpenthouse.GoTel.util.mappers.CountriesMapper;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Set;

@RequestMapping("/api/countries")
@RestController
public class CountriesController extends CountriesService {
    private final CountriesMapper mapper;

    public CountriesController() {
        mapper = getMapper();
    }

    @GetMapping("/getAllCountries")
    public ResponseEntity<Set<CountriesRequest>> getCountriesEntitySet() {
        CountriesService.SERVICE_HANDLER = "FIND_ALL_COUNTRIES";

        Set<CountriesRequest> entities = initializeCountriesService();
        if (entities.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(entities);
        }
    }

    @GetMapping("/getCountryByName/{countryName}")
    public ResponseEntity<Set<CountriesRequest>> getCountryByName(@PathVariable String countryName) {
        CountriesService.SERVICE_HANDLER = "FIND_COUNTRY_BY_NAME";
        PropertiesUtilManager.setProperties("countryName", countryName);
        Set<CountriesRequest> entities = initializeCountriesService();
        if (entities.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(entities);
        }
    }

    @GetMapping("/getCountryByContinent/{continentName}")
    public ResponseEntity<Set<CountriesRequest>> getCountryByContinent(@PathVariable String continentName) {
        CountriesService.SERVICE_HANDLER = "FIND_COUNTRY_BY_CONTINENT";
        PropertiesUtilManager.setProperties("continentName", continentName);
        Set<CountriesRequest> entities = initializeCountriesService();
        if (entities.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(entities);
        }
    }

    @GetMapping("/getCountryByRegion/{regionName}")
    public ResponseEntity<Set<CountriesRequest>> getCountryByRegion(@PathVariable String regionName) {
        CountriesService.SERVICE_HANDLER = "FIND_COUNTRY_BY_REGION";
        PropertiesUtilManager.setProperties("regionName", regionName);
        Set<CountriesRequest> entities = initializeCountriesService();
        if (entities.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(entities);
        }
    }



}
