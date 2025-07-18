package org.airpenthouse.GoTel.services.country;

import org.airpenthouse.GoTel.util.ExecutionHandler;
import org.airpenthouse.GoTel.entities.country.CountriesEntity;
import org.airpenthouse.GoTel.util.LOG;
import org.airpenthouse.GoTel.util.mappers.CountriesMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.Callable;

import static org.airpenthouse.GoTel.entities.country.CountriesEntity.ENTITY_TRIGGER;

@Service
public final class CountriesService extends ExecutionHandler implements Callable<Set<CountriesEntity>> {
    private CountriesMapper mapper;
    public static String SERVICE_HANDLER;


    private Set<CountriesEntity> getAllCountries() {
        ENTITY_TRIGGER = "FIND_ALL_COUNTRIES";
        return Collections.unmodifiableSet(super.executeCountriesEntity());
    }

    private Set<CountriesEntity> getCountryByName() {
        ENTITY_TRIGGER = "FIND_COUNTRY_BY_NAME";
        return Collections.unmodifiableSet(super.executeCountriesEntity());
    }

    private Set<CountriesEntity> getCountryByContinent() {
        ENTITY_TRIGGER = "FIND_COUNTRY_BY_CONTINENT";
        return Collections.unmodifiableSet(super.executeCountriesEntity());
    }

    private Set<CountriesEntity> getCountryByRegion() {
        ENTITY_TRIGGER = "FIND_COUNTRY_BY_REGION";
        return Collections.unmodifiableSet(super.executeCountriesEntity());
    }

    @Override
    public Set<CountriesEntity> call() throws Exception {

        return switch (SERVICE_HANDLER) {
            case "FIND_ALL_COUNTRIES" -> getAllCountries();
            case "FIND_COUNTRY_BY_NAME" -> getCountryByName();
            case "FIND_COUNTRY_BY_CONTINENT" -> getCountryByContinent();
            case "FIND_COUNTRY_BY_REGION" -> getCountryByRegion();
            default -> null;
        };
    }
}

