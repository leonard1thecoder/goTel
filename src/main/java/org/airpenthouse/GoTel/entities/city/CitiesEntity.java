package org.airpenthouse.GoTel.entities.city;

import lombok.Getter;
import org.airpenthouse.GoTel.entities.country.CountriesEntity;
import org.airpenthouse.GoTel.util.CommonEntityMethod;
import org.airpenthouse.GoTel.util.LOG;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.airpenthouse.GoTel.util.PropertiesUtilManager.getPropertiesValue;


@Component
public final class CitiesEntity implements Callable<Set<CitiesEntity>>, Comparable<CitiesEntity> {


    private final static CommonEntityMethod commonEntityMethod;

    @Getter
    private String cityName;
    @Getter
    private String district;
    private String countryCode;
    @Getter
    private AtomicInteger population;
    private final Set<CitiesEntity> cities = new ConcurrentSkipListSet<>();
    private String jdbcQueryAllGetCities, jdbcQueryFindCityByName, jdbcInsertIntoQuery, jdbcUpdateCityPopulation, jdbcUpdateCityName, jdbcQueryFindCitiesByDistrict, jdbcQueryFindCitiesByCountryCode;
    private final CountriesEntity entity = new CountriesEntity();

    static {
        commonEntityMethod = new CommonEntityMethod();
    }

    public CitiesEntity() {
        super();
        this.jdbcQueryAllGetCities = getPropertiesValue("jdbc.query.getAllCities");
        this.jdbcQueryFindCityByName = getPropertiesValue("jdbc.query.findCityByName");
        this.jdbcInsertIntoQuery = getPropertiesValue("jdbc.add.addCity");
        this.jdbcQueryFindCitiesByDistrict = getPropertiesValue("jdbc.query.findCityByDistrict");
        this.jdbcQueryFindCitiesByCountryCode = getPropertiesValue("jdbc.query.findCityByCountry");
        this.jdbcUpdateCityPopulation = getPropertiesValue("jdbc.update.cityPopulationNo");
        this.jdbcUpdateCityName = getPropertiesValue("jdbc.update.updateCityName");
    }


    @Override
    public int compareTo(CitiesEntity cc) {
        return cityName.compareTo(cc.cityName);
    }


    public CitiesEntity(String countryCode, String cityName, String district, int population) {
        this.cityName = cityName;
        this.countryCode = countryCode;
        this.district = district;
        this.population = new AtomicInteger(population);
    }

    public static String QUERY_HANDLE = null;

    @Override
    public Set<CitiesEntity> call() {
        try {
            PreparedStatement ps;
            ResultSet set;
            switch (QUERY_HANDLE) {
                case "GET_ALL_CITIES_DATA" -> {
                    System.out.println("Ove");
                    ps = commonEntityMethod.databaseConfig(jdbcQueryAllGetCities);
                    set = ps.executeQuery();
                    return addDataFromDBToList(set);
                }
                case "FIND_CITY_INFO_BY_NAME" -> {
                    System.out.println("3");
                    System.out.println(this.jdbcQueryFindCityByName);
                    System.out.println(getPropertiesValue("cityName"));
                    ps = commonEntityMethod.databaseConfig(jdbcQueryFindCityByName);
                    ps.setString(1, getPropertiesValue("cityName"));
                    return addDataFromDBToList(ps.executeQuery());
                }
                case "ADD_CITY" -> {
                    ps = commonEntityMethod.databaseConfig(jdbcInsertIntoQuery);
                    ps.setString(1, getPropertiesValue("countryCode"));
                    ps.setString(2, getPropertiesValue("cityName"));
                    ps.setString(3, getPropertiesValue("district"));
                    ps.setLong(4, Long.parseLong(getPropertiesValue("population")));
                    ps.executeUpdate();

                    cities.add(new CitiesEntity(getPropertiesValue("countryCode"), getPropertiesValue("cityName"), getPropertiesValue("district"), Integer.parseInt(getPropertiesValue("district"))));
                    return cities;
                }
                case "GET_CITIES_BY_DISTRICT" -> {
                    LOG.info("Executing cities entity for get cities by district");
                    ps = commonEntityMethod.databaseConfig(this.jdbcQueryFindCitiesByDistrict);
                    ps.setString(1, getPropertiesValue("districtName"));
                    return addDataFromDBToList(ps.executeQuery());
                }
                case "FIND_CITIES_BY_COUNTRY" -> {
                    try {
                        System.out.println("Three");
                        ps = commonEntityMethod.databaseConfig(this.jdbcQueryFindCitiesByCountryCode);
                        ps.setString(1, this.getCountryCode());
                        System.out.println(addDataFromDBToList(ps.executeQuery()));
                        return addDataFromDBToList(ps.executeQuery());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;

                }
                case "UPDATE_CITY_POPULATION" -> {
                    ps = commonEntityMethod.databaseConfig(this.jdbcUpdateCityPopulation);
                    ps.setLong(1, Long.parseLong(getPropertiesValue("population")));
                    ps.setString(2, "citName");
                    ps.executeUpdate();

                    cities.add(new CitiesEntity(getPropertiesValue("countryCode"), getPropertiesValue("cityName"), getPropertiesValue("district"), Integer.parseInt(getPropertiesValue("district"))));
                    return cities;
                }
                case "UPDATE_CITY_NAME" -> {
                    ps = commonEntityMethod.databaseConfig(this.jdbcUpdateCityName);
                    ps.setString(1, "newCityName");
                    ps.setString(2, getPropertiesValue("cityName"));
                    ps.executeUpdate();

                    cities.add(new CitiesEntity(getPropertiesValue("countryCode"), getPropertiesValue("cityName"), getPropertiesValue("district"), Integer.parseInt(getPropertiesValue("district"))));
                    return cities;
                }
            }
        } catch (SQLException | ExecutionException | InterruptedException | TimeoutException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getCountryCode() {
        Set<CountriesEntity> dataStructure = this.entity.getCountryCodeFromDB();

        if (dataStructure.size() == 1) {
            for (CountriesEntity entity : dataStructure) {
                return entity.getCountryCode();
            }

            return null;
        } else {
            throw new IllegalStateException("The data structure contains more than 2 values or zero check : " + dataStructure);
        }
    }

    private Set<CitiesEntity> addDataFromDBToList(ResultSet set) throws SQLException {
        while (set.next()) {
            cities.add(new CitiesEntity(set.getString(1), set.getString(2), set.getString(4), set.getInt(5)));
        }
        set.close();
        LOG.info("data from database to data structure cities data : " + cities);
        return cities;
    }

    @Override
    public String toString() {
        return "country code:" + this.countryCode + ", city name:" + cityName + ", city district: " + district + ", population:" + population.get();

    }
}