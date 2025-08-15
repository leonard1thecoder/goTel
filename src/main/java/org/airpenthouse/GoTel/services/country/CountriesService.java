package org.airpenthouse.GoTel.services.country;

import org.airpenthouse.GoTel.dtos.countries.CountriesRequest;
import org.airpenthouse.GoTel.dtos.countries.MembershipCountriesRequest;
import org.airpenthouse.GoTel.util.dto.binder.CountriesRequestCombiner;
import org.airpenthouse.GoTel.util.executors.CountriesExecutors;
import org.airpenthouse.GoTel.util.mappers.CountriesMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import static org.airpenthouse.GoTel.entities.country.CountriesEntity.ENTITY_TRIGGER;

@Service
public class CountriesService extends CountriesExecutors implements Callable<Set<? extends CountriesRequestCombiner>> {

    public static String SERVICE_HANDLER;
    private CountriesMapper mapper;
    private MembershipCountriesRequest membershipCountriesRequest;

    private Set<? extends CountriesRequestCombiner> getAllCountries() {
        ENTITY_TRIGGER = "FIND_ALL_COUNTRIES";
        return getRequest();
    }

    private Set<? extends CountriesRequestCombiner> getRequest() {
        if (checkMemberShipStatusAndTokenMatch())
            return initializeCountriesEntity().stream().map(mapper::mapper).collect(Collectors.toSet());
        else
            return initializeCountriesEntity().stream().map(mapper::mapToMembershipCountryRequest).collect(Collectors.toSet());
    }

    private Set<? extends CountriesRequestCombiner> getCountryByName() {
        ENTITY_TRIGGER = "FIND_COUNTRY_BY_NAME";
        return getRequest();
    }

    private Set<? extends CountriesRequestCombiner> getCountryByContinent() {
        ENTITY_TRIGGER = "FIND_COUNTRY_BY_CONTINENT";
        return getRequest();
    }

    private Set<? extends CountriesRequestCombiner> getCountryByRegion() {
        ENTITY_TRIGGER = "FIND_COUNTRY_BY_REGION";
        return getRequest();
    }

    @Override
    public Set<? extends CountriesRequestCombiner> call() {

        return switch (SERVICE_HANDLER) {
            case "FIND_ALL_COUNTRIES" -> getAllCountries();
            case "FIND_COUNTRY_BY_NAME" -> getCountryByName();
            case "FIND_COUNTRY_BY_CONTINENT" -> getCountryByContinent();
            case "FIND_COUNTRY_BY_REGION" -> getCountryByRegion();
            default -> null;
        };

    }
}

