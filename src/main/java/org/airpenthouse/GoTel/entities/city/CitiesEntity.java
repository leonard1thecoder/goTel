package org.airpenthouse.GoTel.entities.city;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.airpenthouse.GoTel.util.Log;
import org.airpenthouse.GoTel.util.PropertiesUtilManager;
import org.airpenthouse.GoTel.util.executors.CitiesExecutors;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import java.util.concurrent.*;
import static org.airpenthouse.GoTel.util.PropertiesUtilManager.getPropertiesValue;

@AllArgsConstructor
public class CitiesEntity extends CitiesExecutors implements Callable<Set<CitiesEntity>>, Comparable<CitiesEntity> {

    @Getter
    private int cityId;
    @Getter
    private String cityName, newCityName;
    @Getter
    private String district;
    @Getter
    private String countryName;
    @Getter
    private int population;
    @Getter
    private String newLanguageName;
    private Set<CitiesEntity> cities;
    private String jdbcQueryAllGetCities, jdbcQueryFindCityWithNameAndCountry, jdbcQueryFindCityByName, jdbcInsertIntoQuery, jdbcUpdateCityPopulation, jdbcUpdateCityName, jdbcQueryFindCitiesByDistrict, jdbcQueryFindCitiesByCountryName;


    public CitiesEntity() {
        super();
        cities = new CopyOnWriteArraySet<>();
        this.jdbcQueryFindCityWithNameAndCountry = getPropertiesValue("jdbc.query.findCityByNameAndCountry");
        this.jdbcQueryAllGetCities = getPropertiesValue("jdbc.query.getAllCities");
        this.jdbcQueryFindCityByName = getPropertiesValue("jdbc.query.findCityByName");
        this.jdbcInsertIntoQuery = getPropertiesValue("jdbc.add.addCity");
        this.jdbcQueryFindCitiesByDistrict = getPropertiesValue("jdbc.query.findCityByDistrict");
        this.jdbcQueryFindCitiesByCountryName = getPropertiesValue("jdbc.query.findCityByCountry");
        this.jdbcUpdateCityPopulation = getPropertiesValue("jdbc.update.cityPopulationNo");
        this.jdbcUpdateCityName = getPropertiesValue("jdbc.update.updateCityName");
    }



    @Override
    public int compareTo(CitiesEntity cc) {
        return cityName.compareTo(cc.cityName);
    }


    private CitiesEntity(int cityId, String countryName, String cityName, String district, int population) {
        this.cityId = cityId;
        this.cityName = cityName;
        this.countryName = countryName;
        this.district = district;
        this.population = population;
    }

    public static String QUERY_HANDLE = null;

    private Set<CitiesEntity> getAllCities() {
        try {
            preparedStatementFoRResultSet = databaseConfig(jdbcQueryAllGetCities);
            return addDataFromDBToList();
        } catch (SQLException | ExecutionException | TimeoutException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private Set<CitiesEntity> getCitiesByDistrict() {
        try {
            preparedStatementFoRResultSet = databaseConfig(this.jdbcQueryFindCitiesByDistrict);
            preparedStatementFoRResultSet.setString(1, getPropertiesValue("districtName"));
            return addDataFromDBToList();
        } catch (SQLException | ExecutionException | TimeoutException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private Set<CitiesEntity> getCitiesByNameAndCountry() {
        try {
            preparedStatementFoRResultSet = databaseConfig(this.jdbcQueryFindCityWithNameAndCountry);
            preparedStatementFoRResultSet.setString(1, getPropertiesValue("cityName"));
            Log.info(getPropertiesValue("cityName"));
            preparedStatementFoRResultSet.setString(2, getPropertiesValue("countryName1"));
            Log.info(getPropertiesValue("countryName1"));
            return addDataFromDBToList();
        } catch (SQLException | ExecutionException | TimeoutException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private Set<CitiesEntity> getCityByName() {
        try {
            preparedStatementFoRResultSet = databaseConfig(jdbcQueryFindCityByName);
            if (this.changeToNewCityName) {
                preparedStatementFoRResultSet.setString(1, getPropertiesValue("newCityName"));
                Log.info("TRUE");
                changeToNewCityName = false;
            } else {
                Log.info("FALSE");
                preparedStatementFoRResultSet.setString(1, getPropertiesValue("cityName"));
            }
            return addDataFromDBToList();
        } catch (SQLException | ExecutionException | TimeoutException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private Set<CitiesEntity> getCityByCountry() {
        try {

            preparedStatementFoRResultSet = databaseConfig(this.jdbcQueryFindCitiesByCountryName);
            preparedStatementFoRResultSet.setString(1, getCountryCodeByCountryName(PropertiesUtilManager.getPropertiesValue("countryName")));
            return addDataFromDBToList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Set<CitiesEntity> insertCity() {
        try {

            preparedStatementFoRExecuteUpdate = databaseConfig(jdbcInsertIntoQuery);

            preparedStatementFoRExecuteUpdate.setString(2, getCountryCodeByCountryName(getEntity().getCountryName()));
            preparedStatementFoRExecuteUpdate.setString(1, getEntity().getCityName());
            preparedStatementFoRExecuteUpdate.setString(3, getEntity().getDistrict());
            preparedStatementFoRExecuteUpdate.setInt(4, getEntity().getPopulation());
            Set<CitiesEntity> set = getCitiesByNameAndCountry();
            Log.info("testing" + set);

            if (set.isEmpty()) {
                preparedStatementFoRExecuteUpdate.executeUpdate();
                cities = getCitiesByNameAndCountry();
                Log.info("Data inserted");
            } else {
                cities = new CopyOnWriteArraySet<>();
            }
            return cities;
        } catch (SQLException | ExecutionException | TimeoutException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private Set<CitiesEntity> updateCitiesPopulation() {
        try {

            preparedStatementFoRExecuteUpdate = databaseConfig(this.jdbcUpdateCityPopulation);
            preparedStatementFoRExecuteUpdate.setInt(1, getEntity().getPopulation());
            preparedStatementFoRExecuteUpdate.setString(2, getEntity().getCityName());
            Set<CitiesEntity> set = getCityByName();
            if (!set.isEmpty()) {
                preparedStatementFoRExecuteUpdate.executeUpdate();
                changeToNewCityName = true;
                var local = getCityByName();
                Log.info("" + local);
                return local;
            } else {
                cities = new CopyOnWriteArraySet<>();
                return cities;
            }

        } catch (SQLException | ExecutionException | TimeoutException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private PreparedStatement preparedStatementFoRResultSet;
    private PreparedStatement preparedStatementFoRExecuteUpdate;
    private boolean changeToNewCityName = false;

    private Set<CitiesEntity> updateCityName() {
        try {

            preparedStatementFoRExecuteUpdate = databaseConfig(this.jdbcUpdateCityName);
            preparedStatementFoRExecuteUpdate.setString(1, getEntity().getNewCityName());
            preparedStatementFoRExecuteUpdate.setString(2, getEntity().getCityName());
            Set<CitiesEntity> set = getCityByName();
            Log.info("" + set.size());
            if (!set.isEmpty()) {
                preparedStatementFoRExecuteUpdate.executeUpdate();
                changeToNewCityName = true;
                var local = getCityByName();
                Log.info("" + local);
                return local;
            } else {
                cities = new CopyOnWriteArraySet<>();
                return cities;
            }
        } catch (SQLException | ExecutionException | TimeoutException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Set<CitiesEntity> call() {

        return switch (QUERY_HANDLE) {
            case "GET_ALL_CITIES_DATA" -> getAllCities();
            case "FIND_CITY_INFO_BY_NAME" -> getCityByName();
            case "ADD_CITY" -> insertCity();
            case "GET_CITIES_BY_DISTRICT" -> getCitiesByDistrict();
            case "FIND_CITIES_BY_COUNTRY" -> getCityByCountry();
            case "UPDATE_CITY_POPULATION" -> updateCitiesPopulation();
            case "UPDATE_CITY_NAME" -> updateCityName();
            default -> null;
        };

    }

    private Set<CitiesEntity> addDataFromDBToList() throws SQLException {
        cities = new ConcurrentSkipListSet<>();

        ResultSet set = preparedStatementFoRResultSet.executeQuery();

            while (set.next()) {
                cities.add(new CitiesEntity(set.getInt(1), set.getString(2), set.getString(3), set.getString(4), set.getInt(5)));
            }
            set.close();

        Log.info("data from database to data structure cities data : " + cities);
        return cities;
    }

    @Override
    public String toString() {
        return "ID :" + cityId + "country code:" + this.countryName + ", city name:" + cityName + ", city district: " + district + ", population:" + population;
    }
}