package org.airpenthouse.GoTel.services.country;

import org.airpenthouse.GoTel.dtos.countries.CountriesRequest;
import org.airpenthouse.GoTel.util.executors.CountriesExecutors;
import org.airpenthouse.GoTel.util.mappers.CountriesMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import static org.airpenthouse.GoTel.entities.country.CountriesEntity.ENTITY_TRIGGER;

@Service
public class CountriesService extends CountriesExecutors implements Callable<Set<CountriesRequest>> {

    public static String SERVICE_HANDLER;
    private CountriesMapper mapper;

    private Set<CountriesRequest> getAllCountries() {
        ENTITY_TRIGGER = "FIND_ALL_COUNTRIES";
        return initializeCountriesEntity().stream().map(mapper::mapper).collect(Collectors.toSet());
    }

    private Set<CountriesRequest> getCountryByName() {
        ENTITY_TRIGGER = "FIND_COUNTRY_BY_NAME";
        return initializeCountriesEntity().stream().map(mapper::mapper).collect(Collectors.toSet());
    }

    private Set<CountriesRequest> getCountryByContinent() {
        ENTITY_TRIGGER = "FIND_COUNTRY_BY_CONTINENT";
        return initializeCountriesEntity().stream().map(mapper::mapper).collect(Collectors.toSet());

    }

    private Set<CountriesRequest> getCountryByRegion() {
        ENTITY_TRIGGER = "FIND_COUNTRY_BY_REGION";
        return initializeCountriesEntity().stream().map(mapper::mapper).collect(Collectors.toSet());

    }

    @Override
    public Set<CountriesRequest> call() {

        return switch (SERVICE_HANDLER) {
            case "FIND_ALL_COUNTRIES" -> getAllCountries();
            case "FIND_COUNTRY_BY_NAME" -> getCountryByName();
            case "FIND_COUNTRY_BY_CONTINENT" -> getCountryByContinent();
            case "FIND_COUNTRY_BY_REGION" -> getCountryByRegion();
            default -> null;
        };

    }
}

