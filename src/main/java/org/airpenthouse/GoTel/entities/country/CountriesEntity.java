package org.airpenthouse.GoTel.entities.country;

import java.util.concurrent.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.airpenthouse.GoTel.util.Log;
import org.airpenthouse.GoTel.util.PropertiesUtilManager;
import org.airpenthouse.GoTel.util.executors.CountriesExecutors;

import java.util.Set;


@AllArgsConstructor
public final class CountriesEntity extends CountriesExecutors implements Callable<Set<CountriesEntity>>, Comparable<CountriesEntity> {

    @Getter
    private String countryName;
    @Getter
    private String countryRegion;
    @Getter
    private int population;
    @Getter
    private String continent;
    @Getter
    private double surfaceArea;
    @Getter
    private int indepYear;
    @Getter
    private double lifeExpectancy;
    @Getter
    private double gnp;
    @Getter
    private double oldGnp;
    @Getter
    private String localName;
    @Getter
    private String governmentForm;
    @Getter
    private String headOfState;
    @Getter
    private String capital;
    @Getter
    private String code;


    private Set<CountriesEntity> countries;
    public static String ENTITY_TRIGGER;
    private String jdbcQueryFindAllCountries, jdbcQueryFindCountryByName, jdbcQueryFindCountryByRegion, jdbcQueryFindCountryByContinent;

    public CountriesEntity() {
        jdbcQueryFindAllCountries = PropertiesUtilManager.getPropertiesValue("jdbc.query.allCountries");
        jdbcQueryFindCountryByName = PropertiesUtilManager.getPropertiesValue("jdbc.query.findCountryByName");
        jdbcQueryFindCountryByContinent = PropertiesUtilManager.getPropertiesValue("jdbc.query.findCountriesByContinent");
        jdbcQueryFindCountryByRegion = PropertiesUtilManager.getPropertiesValue("jdbc.query.findCountriesByRegion");
    }

    private CountriesEntity(String countryName, String countryRegion, int population) {
        this.countryName = countryName;
        this.countryRegion = countryRegion;
        this.population = population;
    }

    public CountriesEntity(String countryName, String countryRegion, int population, String continent, double surfaceArea, int indepYear, double lifeExpectancy, double gnp, double oldGnp, String localName, String governmentForm, String headOfState, String capital, String code) {
        this.countryName = countryName;
        this.countryRegion = countryRegion;
        this.population = population;
        this.continent = continent;
        this.surfaceArea = surfaceArea;
        this.indepYear = indepYear;
        this.lifeExpectancy = lifeExpectancy;
        this.gnp = gnp;
        this.oldGnp = oldGnp;
        this.localName = localName;
        this.governmentForm = governmentForm;
        this.headOfState = headOfState;
        this.capital = capital;
        this.code = code;
    }

    private Set<CountriesEntity> getCountryByName() {
        PreparedStatement ps;
        try {

            ps = databaseConfig(jdbcQueryFindCountryByName);
            Log.info(jdbcQueryFindCountryByName);
            Log.info("OK#######1");
            ps.setString(1, PropertiesUtilManager.getPropertiesValue("countryName"));
            ResultSet set = ps.executeQuery();
            return addDataFromDbToSet(set);
        } catch (SQLException | ExecutionException | TimeoutException | InterruptedException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Set<CountriesEntity> getCountryByContinent() {
        PreparedStatement ps;
        try {

            ps = databaseConfig(jdbcQueryFindCountryByContinent);
            ps.setString(1, PropertiesUtilManager.getPropertiesValue("continentName"));
            ResultSet set = ps.executeQuery();
            return addDataFromDbToSet(set);
        } catch (SQLException | ExecutionException | TimeoutException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private Set<CountriesEntity> getCountryByRegion() {
        PreparedStatement ps;
        try {

            ps = databaseConfig(jdbcQueryFindCountryByRegion);
            ps.setString(1, PropertiesUtilManager.getPropertiesValue("regionName"));
            ResultSet set = ps.executeQuery();
            return addDataFromDbToSet(set);
        } catch (SQLException | ExecutionException | TimeoutException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public int compareTo(CountriesEntity obj) {
        return countryName.compareTo(obj.countryName);
    }


    private Set<CountriesEntity> getAllCountries() {

        PreparedStatement ps;
        try {
            ps = databaseConfig(jdbcQueryFindAllCountries);
            ResultSet set = ps.executeQuery();
            Log.info("OK#######**********");
            return addDataFromDbToSet(set);
        } catch (SQLException | ExecutionException | TimeoutException | InterruptedException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Set<CountriesEntity> call() {
        return switch (ENTITY_TRIGGER) {
            case "FIND_ALL_COUNTRIES" -> getAllCountries();
            case "FIND_COUNTRY_BY_NAME" -> getCountryByName();
            case "FIND_COUNTRY_BY_CONTINENT" -> getCountryByContinent();
            case "FIND_COUNTRY_BY_REGION" -> getCountryByRegion();
            default -> null;
        };
    }

    private Set<CountriesEntity> addDataFromDbToSet(ResultSet set) throws SQLException {
        countries = new CopyOnWriteArraySet<>();
        while (set.next()) {
            if (checkMemberShipStatusAndTokenMatch()) {
                countries.add(new CountriesEntity(set.getString(2), set.getString(4), set.getInt(7)));
            } else {
                countries.add(new CountriesEntity(set.getString(2), set.getString(4), set.getInt(7), set.getString(3), set.getDouble(5), set.getInt(6), set.getDouble(8), set.getDouble(9), set.getDouble(10), set.getString(11), set.getString(12), set.getString(13), set.getString(14), set.getString(15)));
            }
        }
        System.out.println(countries);
        return countries;
    }


    @Override
    public String toString() {
        return "country name : " + countryName + " country region : " + countryRegion + " country population :" + population;
    }


}


