package org.airpenthouse.GoTel.controllers;

import org.airpenthouse.GoTel.services.country.CountriesService;
import org.airpenthouse.GoTel.util.PropertiesUtilManager;
import org.airpenthouse.GoTel.util.dto.binder.CountriesRequestCombiner;
import org.airpenthouse.GoTel.util.executors.CountriesExecutors;
import org.airpenthouse.GoTel.util.mappers.CountriesMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequestMapping("/api/countries")
@RestController
@CrossOrigin(origins="http://localhost:4200")
public class CountriesController {
    @Autowired
    public CountriesMapper mapper;
    @Autowired
    public CountriesService executor;
    
    @GetMapping("/getAllCountries")
    public ResponseEntity<Set<? extends CountriesRequestCombiner>> getAllCountries() {
        CountriesService.SERVICE_HANDLER = "FIND_ALL_COUNTRIES";
        CountriesExecutors.setMapper(mapper);
        Set<? extends CountriesRequestCombiner> entities = executor.initializeCountriesService();
        if (entities.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(entities);
        }
    }

    @GetMapping("/{memberUsername}/getAllCountries")
    public ResponseEntity<Set<? extends CountriesRequestCombiner>> membersGetAllCountries(@PathVariable String memberUsername, @RequestHeader(name = "x-auth-membership-token") String memberToken) {
        CountriesService.SERVICE_HANDLER = "FIND_ALL_COUNTRIES";
        CountriesExecutors.setMapper(mapper);
        PropertiesUtilManager.setProperties("memberUsername", memberUsername);
        PropertiesUtilManager.setProperties("memberToken", memberToken);
        if (executor.checkMemberShipStatusAndTokenMatch()) {
            Set<? extends CountriesRequestCombiner> entities = executor.initializeCountriesService();
            if (entities.isEmpty()) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(entities);
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{memberUsername}/getCountryByName/{countryName}")
    public ResponseEntity<Set<? extends CountriesRequestCombiner>> membersGetCountryByName(@PathVariable String countryName, @PathVariable String memberUsername, @RequestHeader(name = "x-auth-membership-token") String memberToken) {
        CountriesService.SERVICE_HANDLER = "FIND_COUNTRY_BY_NAME";
        CountriesExecutors.setMapper(mapper);
        PropertiesUtilManager.setProperties("memberUsername", memberUsername);
        PropertiesUtilManager.setProperties("countryName", countryName);
        PropertiesUtilManager.setProperties("memberToken", memberToken);
        if (executor.checkMemberShipStatusAndTokenMatch()) {
            Set<? extends CountriesRequestCombiner> entities = executor.initializeCountriesService();
            if (entities.isEmpty()) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(entities);
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/getCountryByName/{countryName}")
    public ResponseEntity<Set<? extends CountriesRequestCombiner>> getCountryByName(@PathVariable String countryName) {
        CountriesService.SERVICE_HANDLER = "FIND_COUNTRY_BY_NAME";
        CountriesExecutors.setMapper(mapper);
        PropertiesUtilManager.setProperties("countryName", countryName);
        Set<? extends CountriesRequestCombiner> entities = executor.initializeCountriesService();
        if (entities.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(entities);
        }
    }

    @GetMapping("/getCountryByContinent/{continentName}")
    public ResponseEntity<Set<? extends CountriesRequestCombiner>> getCountryByContinent(@PathVariable String continentName) {
        CountriesService.SERVICE_HANDLER = "FIND_COUNTRY_BY_CONTINENT";
        CountriesExecutors.setMapper(mapper);
        PropertiesUtilManager.setProperties("continentName", continentName);
        Set<? extends CountriesRequestCombiner> entities = executor.initializeCountriesService();
        if (entities.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(entities);
        }
    }

    @GetMapping("/{memberUsername}/getCountryByRegion/{regionName}")
    public ResponseEntity<Set<? extends CountriesRequestCombiner>> memberGetCountryByRegion(@PathVariable String regionName, @PathVariable String memberUsername, @RequestHeader(name = "x-auth-membership-token") String memberToken) {
        CountriesService.SERVICE_HANDLER = "FIND_COUNTRY_BY_REGION";
        CountriesExecutors.setMapper(mapper);
        PropertiesUtilManager.setProperties("regionName", regionName);
        PropertiesUtilManager.setProperties("memberToken", memberToken);
        PropertiesUtilManager.setProperties("memberUsername", memberUsername);
        if (executor.checkMemberShipStatusAndTokenMatch()) {
            Set<? extends CountriesRequestCombiner> entities = executor.initializeCountriesService();
            if (entities.isEmpty()) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(entities);
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{memberUsername}/getCountryByContinent/{continentName}")
    public ResponseEntity<Set<? extends CountriesRequestCombiner>> memberGetCountryByContinent(@PathVariable String continentName, @PathVariable String memberUsername, @RequestHeader String memberToken) {
        CountriesService.SERVICE_HANDLER = "FIND_COUNTRY_BY_CONTINENT";
        CountriesExecutors.setMapper(mapper);
        PropertiesUtilManager.setProperties("memberUsername", memberUsername);
        PropertiesUtilManager.setProperties("memberToken", memberToken);
        PropertiesUtilManager.setProperties("continentName", continentName);

        if (executor.checkMemberShipStatusAndTokenMatch()) {
            Set<? extends CountriesRequestCombiner> entities = executor.initializeCountriesService();
            if (entities.isEmpty()) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(entities);
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/getCountryByRegion/{regionName}")
    public ResponseEntity<Set<? extends CountriesRequestCombiner>> getCountryByRegion(@PathVariable String regionName) {
        CountriesService.SERVICE_HANDLER = "FIND_COUNTRY_BY_REGION";
        CountriesExecutors.setMapper(mapper);
        PropertiesUtilManager.setProperties("regionName", regionName);
        Set<? extends CountriesRequestCombiner> entities = executor.initializeCountriesService();
        if (entities.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(entities);
        }
    }


}
