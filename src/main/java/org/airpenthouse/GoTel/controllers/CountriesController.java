package org.airpenthouse.GoTel.controllers;

import org.airpenthouse.GoTel.entities.country.CountriesEntity;
import org.airpenthouse.GoTel.services.country.CountriesService;
import org.airpenthouse.GoTel.util.ExecutionHandler;
import org.airpenthouse.GoTel.util.LOG;
import org.airpenthouse.GoTel.util.PropertiesUtilManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Set;

@RestController
@RequestMapping("/api/countries")
public class CountriesController extends ExecutionHandler {

    @GetMapping("/getAllCountries")
    public ResponseEntity<Set<CountriesEntity>> getCountriesEntitySet() throws Exception {
        CountriesService.SERVICE_HANDLER = "FIND_ALL_COUNTRIES";
        LOG.info("EXECURINg EnTITY 2" + executeCountriesService());

        return ResponseEntity.ok(Collections.unmodifiableSet(executeCountriesService()));
    }

    @GetMapping("/getCountryByName/{countryName}")
    public Set<CountriesEntity> getCountryByName(@PathVariable String countryName) {
        CountriesService.SERVICE_HANDLER = "FIND_COUNTRY_BY_NAME";
        PropertiesUtilManager.setProperties("countryName", countryName);
        return executeCountriesService();
    }

    @GetMapping("/getCountryByContinent/{continentName}")
    public Set<CountriesEntity> getCountryByContinent(@PathVariable String continentName) {
        CountriesService.SERVICE_HANDLER = "FIND_COUNTRY_BY_CONTINENT";
        PropertiesUtilManager.setProperties("continentName", continentName);
        return executeCountriesService();
    }


}
