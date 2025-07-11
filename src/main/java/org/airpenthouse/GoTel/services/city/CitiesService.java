package org.airpenthouse.GoTel.services.city;


import org.airpenthouse.GoTel.util.ExecutionHandler;
import org.airpenthouse.GoTel.entities.city.CitiesEntity;
import org.airpenthouse.GoTel.util.LOG;
import org.airpenthouse.GoTel.util.mappers.CitiesMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.Callable;


@Service
public class CitiesService extends ExecutionHandler implements Callable<Set<CitiesEntity>> {

    //Managing data to displayed
    CitiesMapper citiesMapper;

    public static String SERVICE_TRIGGER = null;

    public Set<CitiesEntity> getAllCities() {
        CitiesEntity.QUERY_HANDLE = "GET_ALL_CITIES_DATA";
        return new HashSet<>(initializeCitiesEntity());
    }

    public Set<CitiesEntity> findCityByName() {
        CitiesEntity.QUERY_HANDLE = "FIND_CITY_INFO_BY_NAME";
        return new HashSet<>(initializeCitiesEntity());

    }

    public Set<CitiesEntity> insertCities() {
        CitiesEntity.QUERY_HANDLE = "ADD_CITY";
        return new HashSet<>(initializeCitiesEntity());
    }

    public Set<CitiesEntity> findCitiesByCountries() {
        CitiesEntity.QUERY_HANDLE = "FIND_CITIES_BY_COUNTRY";
        return new HashSet<>(initializeCitiesEntity());
    }

    public Set<CitiesEntity> findCitiesByDistrict() {
        LOG.info("Executing find cities by district service");
        CitiesEntity.QUERY_HANDLE = "GET_CITIES_BY_DISTRICT";
        LOG.info(" data from the data structure for service : ");
        return new HashSet<>(initializeCitiesEntity());
    }

    public Set<CitiesEntity> updateCiteName() {
        CitiesEntity.QUERY_HANDLE = "UPDATE_CITY_NAME";
        return new HashSet<>(initializeCitiesEntity());
    }

    public Set<CitiesEntity> updateCitePopulation() {
        CitiesEntity.QUERY_HANDLE = "UPDATE_CITY_NAME";
        return new HashSet<>(initializeCitiesEntity());
    }

    @Override
    public Set<CitiesEntity> call() {
        switch (SERVICE_TRIGGER) {
            case "GET_ALL_CITIES_DATA" -> {
                return this.getAllCities();
            }
            case "ADD_CITY" -> {
                return this.insertCities();
            }
            case "FIND_CITY_INFO_BY_NAME" -> {
                return this.findCityByName();
            }
            case "FIND_CITIES_BY_COUNTRY" -> {
                return this.findCitiesByCountries();
            }
            case "GET_CITIES_BY_DISTRICT" -> {
                return this.findCitiesByDistrict();
            }
            case "UPDATE_CITY_POPULATION" -> {
                return this.updateCitePopulation();
            }
            case "UPDATE_CITY_NAME" -> {
                return this.updateCiteName();
            }
            default -> {
                return null;
            }
        }
    }
}