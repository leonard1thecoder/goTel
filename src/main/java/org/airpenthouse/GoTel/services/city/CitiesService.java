package org.airpenthouse.GoTel.services.city;

import org.airpenthouse.GoTel.dtos.cities.CitiesRequest;
import org.airpenthouse.GoTel.entities.city.CitiesEntity;
import org.airpenthouse.GoTel.util.Log;
import org.airpenthouse.GoTel.util.executors.CitiesExecutors;
import org.airpenthouse.GoTel.util.mappers.CitiesMapper;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
@Service
public class CitiesService extends CitiesExecutors implements Callable<Set<CitiesRequest>> {
    private static CitiesService instance;

    public final CitiesMapper citiesMapper;

    public static String SERVICE_TRIGGER = null;

    protected CitiesService() {
        citiesMapper = getMapper();
    }

    public static CitiesService getInstance() {
        if (instance == null) {
            instance = new CitiesService();
        }
        return instance;
    }

    public Set<CitiesRequest> getAllCities() {
        CitiesEntity.QUERY_HANDLE = "GET_ALL_CITIES_DATA";

        return initializeCitiesEntity().parallelStream().map(citiesMapper::toCitiesDto).peek(System.out::println).collect(Collectors.toSet());
    }

    public Set<CitiesRequest> findCityByName() {
        CitiesEntity.QUERY_HANDLE = "FIND_CITY_INFO_BY_NAME";
        return initializeCitiesEntity().parallelStream().map(citiesMapper::toCitiesDto).collect(Collectors.toSet());

    }

    public Set<CitiesRequest> insertCities() {
        CitiesEntity.QUERY_HANDLE = "ADD_CITY";
        return initializeCitiesEntity().parallelStream().map(citiesMapper::toCitiesDto).collect(Collectors.toSet());
    }

    public Set<CitiesRequest> findCitiesByCountries() {
        CitiesEntity.QUERY_HANDLE = "FIND_CITIES_BY_COUNTRY";
        return initializeCitiesEntity().parallelStream().map(citiesMapper::toCitiesDto).collect(Collectors.toSet());
    }

    public Set<CitiesRequest> findCitiesByDistrict() {
        Log.info("Executing find cities by district service");
        CitiesEntity.QUERY_HANDLE = "GET_CITIES_BY_DISTRICT";
        Log.info(" data from the data structure for service : ");
        return initializeCitiesEntity().parallelStream().map(citiesMapper::toCitiesDto).collect(Collectors.toSet());
    }

    public Set<CitiesRequest> updateCiteName() {
        CitiesEntity.QUERY_HANDLE = "UPDATE_CITY_NAME";
        return initializeCitiesEntity().parallelStream().map(citiesMapper::toCitiesDto).collect(Collectors.toSet());
    }

    public Set<CitiesRequest> updateCitePopulation() {
        CitiesEntity.QUERY_HANDLE = "UPDATE_CITY_NAME";
        return initializeCitiesEntity().parallelStream().map(citiesMapper::toCitiesDto).collect(Collectors.toSet());
    }

    @Override
    public Set<CitiesRequest> call() {
        return switch (SERVICE_TRIGGER) {
            case "GET_ALL_CITIES_DATA" -> this.getAllCities();
            case "ADD_CITY" -> this.insertCities();
            case "FIND_CITY_INFO_BY_NAME" -> this.findCityByName();
            case "FIND_CITIES_BY_COUNTRY" -> this.findCitiesByCountries();
            case "GET_CITIES_BY_DISTRICT" -> this.findCitiesByDistrict();
            case "UPDATE_CITY_POPULATION" -> this.updateCitePopulation();
            case "UPDATE_CITY_NAME" -> this.updateCiteName();
            default -> null;
        };
    }
}