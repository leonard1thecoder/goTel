package org.airpenthouse.GoTel.services.country;

import org.airpenthouse.GoTel.dtos.countries.CountriesRequest;
import org.airpenthouse.GoTel.util.mappers.CountriesMapper;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import static org.airpenthouse.GoTel.entities.country.CountriesEntity.ENTITY_TRIGGER;
import static org.airpenthouse.GoTel.util.executors.CountriesExecutors.getInstances;


public final class CountriesService implements Callable<Set<CountriesRequest>> {

    private static CountriesService instance;
    public static String SERVICE_HANDLER;
    private final CountriesMapper mapper;

    private CountriesService() {
        mapper = getInstances().getMapper();
    }

    public static CountriesService getInstance() {
        if (instance == null) {
            instance = new CountriesService();
        }
        return instance;
    }

    private Set<CountriesRequest> getAllCountries() {
        ENTITY_TRIGGER = "FIND_ALL_COUNTRIES";
        return getInstances().initializeCountriesEntity().stream().map(mapper::mapper).collect(Collectors.toSet());
    }

    private Set<CountriesRequest> getCountryByName() {
        ENTITY_TRIGGER = "FIND_COUNTRY_BY_NAME";
        return getInstances().initializeCountriesEntity().stream().map(mapper::mapper).collect(Collectors.toSet());
    }

    private Set<CountriesRequest> getCountryByContinent() {
        ENTITY_TRIGGER = "FIND_COUNTRY_BY_CONTINENT";
        return getInstances().initializeCountriesEntity().stream().map(mapper::mapper).collect(Collectors.toSet());

    }

    private Set<CountriesRequest> getCountryByRegion() {
        ENTITY_TRIGGER = "FIND_COUNTRY_BY_REGION";
        return getInstances().initializeCountriesEntity().stream().map(mapper::mapper).collect(Collectors.toSet());

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

