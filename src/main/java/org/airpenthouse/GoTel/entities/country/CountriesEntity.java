package org.airpenthouse.GoTel.entities.country;

import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import lombok.Getter;
import org.airpenthouse.GoTel.util.CommonEntityMethod;

import org.airpenthouse.GoTel.util.LOG;
import org.airpenthouse.GoTel.util.PropertiesUtilManager;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

@Component
public final class CountriesEntity implements Callable<Set<CountriesEntity>>, Comparable<CountriesEntity> {

    @Getter
    private String countryName;
    @Getter
    private String countryRegion;
    @Getter
    private int population;

    private Set<CountriesEntity> countries;
    public static String ENTITY_TRIGGER = "FIND_ALL_COUNTRIES";
    private String jdbcQueryFindAllCountries, jdbcQueryFindCountryCode, jdbcQueryFindCountryByname;

    public CountriesEntity() {
        super();

        this.countries = new ConcurrentSkipListSet<>();
        jdbcQueryFindAllCountries = PropertiesUtilManager.getPropertiesValue("jdbc.query.allCountries");
        jdbcQueryFindCountryByname = PropertiesUtilManager.getPropertiesValue("jdbc.query.findCountryByName");

        jdbcQueryFindCountryCode = PropertiesUtilManager.getPropertiesValue("jdbc.query.findCountryCodeByCountry");

        LOG.info(jdbcQueryFindAllCountries);
    }

    static {
        commonEntityMethod = new CommonEntityMethod();
    }

    private Set<CountriesEntity> getCountryByName() {
        PreparedStatement ps;
        try {

            ps = commonEntityMethod.databaseConfig(jdbcQueryFindCountryByname);
            LOG.info("OK#######");
            ps.setString(1, PropertiesUtilManager.getPropertiesValue("countryName"));
            ResultSet set = ps.executeQuery();
            return addDataFromDbToSet(set);
        } catch (SQLException | ExecutionException | TimeoutException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static final CommonEntityMethod commonEntityMethod;

    @Override
    public int compareTo(CountriesEntity obj) {
        return countryName.compareTo(obj.countryName);
    }

    public Set<CountriesEntity> getCountryCodeFromDB() {
        PreparedStatement ps;
        try {

            ps = commonEntityMethod.databaseConfig(jdbcQueryFindCountryCode);
            LOG.info("OK#######");
            ps.setString(1, PropertiesUtilManager.getPropertiesValue("countryName"));
            ResultSet set = ps.executeQuery();
            return null;
        } catch (SQLException | ExecutionException | TimeoutException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    private Set<CountriesEntity> getAllCountries() {

        PreparedStatement ps;
        try {
            ps = commonEntityMethod.databaseConfig(jdbcQueryFindAllCountries);
            ResultSet set = ps.executeQuery();
            LOG.info("OK#######**********");
            return addDataFromDbToSet(set);
        } catch (SQLException | ExecutionException | TimeoutException | InterruptedException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Set<CountriesEntity> call() throws Exception {
        switch (ENTITY_TRIGGER) {
            case "FIND_ALL_COUNTRIES":
                return getAllCountries();
            case "FIND_COUNTRY_BY_NAME":
                return getCountryByName();
            default:
                return null;
        }
    }


    //The find country by Country COde to be replaced by JOINED QUERY


    private Set<CountriesEntity> addDataFromDbToSet(ResultSet set) throws SQLException {
        while (set.next()) {
            countries.add(new CountriesEntity(set.getString(2), set.getString(4), set.getInt(7)));
        }
        return countries;
    }

    private CountriesEntity(String countryName, String countryRegion, int population) {
        //	this();
        this.countryName = countryName;
        this.countryRegion = countryRegion;
        this.population = population;
    }

    @Override
    public String toString() {
        return "country name : " + countryName + " country region : " + countryRegion + " country population :" + population;
    }


}


