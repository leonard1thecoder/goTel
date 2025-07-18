package org.airpenthouse.GoTel.entities.languanges;

import lombok.Getter;
import org.airpenthouse.GoTel.util.CommonEntityMethod;
import org.airpenthouse.GoTel.util.PropertiesUtilManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.*;
import java.util.Set;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class WorldLanguagesEntity implements Callable<Set<WorldLanguagesEntity>>, Comparable<WorldLanguagesEntity> {
    @Getter
    private String country;
    @Getter
    private String language;
    @Getter
    private Boolean languageStatus;

    private static final CommonEntityMethod commonEntityMethod;
    private String queryFindAllLanguages, queryFindLanguagesByCountryName, queryFindLanguageByName;


    private Set<WorldLanguagesEntity> languages;

    static {
        commonEntityMethod = new CommonEntityMethod();
    }

    @Override
    public int compareTo(WorldLanguagesEntity obj) {
        return language.compareTo(obj.language);
    }

    public WorldLanguagesEntity() {
        super();
        languages = new CopyOnWriteArraySet<>();
        queryFindAllLanguages = PropertiesUtilManager.getPropertiesValue("jdbc.query.allLanguage");
        queryFindLanguageByName = PropertiesUtilManager.getPropertiesValue("jdbc.query.findLanguageByName");
        queryFindLanguagesByCountryName = PropertiesUtilManager.getPropertiesValue("jdbc.query.findLanguageByCountryName");

    }

    private WorldLanguagesEntity(String country, String language, Boolean languageStatus) {
        this.country = country;
        this.language = language;
        this.languageStatus = languageStatus;
    }

    public static String ENTITY_TRIGGER;

    private Set<WorldLanguagesEntity> getAllLanguages() {
        try {
            PreparedStatement ps = commonEntityMethod.databaseConfig(queryFindAllLanguages);
            ResultSet set = ps.executeQuery();
            return addDatFromDBToList(set);
        } catch (SQLException | TimeoutException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Set<WorldLanguagesEntity> getLanguageByName() {
        try {
            PreparedStatement ps = commonEntityMethod.databaseConfig(queryFindLanguageByName);
            ps.setString(1, PropertiesUtilManager.getPropertiesValue("languageName"));
            ResultSet set = ps.executeQuery();
            return addDatFromDBToList(set);
        } catch (SQLException | TimeoutException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Set<WorldLanguagesEntity> getLanguageByCountry() {
        try {
            PreparedStatement ps = commonEntityMethod.databaseConfig(queryFindLanguagesByCountryName);
            ps.setString(1, PropertiesUtilManager.getPropertiesValue("countryName"));
            ResultSet set = ps.executeQuery();
            return addDatFromDBToList(set);
        } catch (SQLException | TimeoutException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Set<WorldLanguagesEntity> call() throws Exception {
        switch (ENTITY_TRIGGER) {
            case "FIND_ALL_LANGUAGES":
                return getAllLanguages();
            case "FIND_LANGUAGE_BY_NAME":
                return getLanguageByName();
            case "FIND_LANGUAGES_BY_COUNTRY":
                return getLanguageByCountry();
            //below cases needs DTO's to use, next push methods will be used
            case "UPDATE_LANGUAGE_STATUS":
            case "ADD_LANGUAGE":
        }
        return null;
    }

    public Set<WorldLanguagesEntity> addDatFromDBToList(ResultSet set) throws SQLException {
        languages = new ConcurrentSkipListSet<>();
        while (set.next()) {
            if (set.getObject(3).toString().charAt(0) == 'T')
                languages.add(new WorldLanguagesEntity(set.getString(1), set.getString(2), true));
            else
                languages.add(new WorldLanguagesEntity(set.getString(1), set.getString(2), false));
        }

        return languages;
    }

    @Override
    public String toString() {
        return "country : " + country + " language : " + language + " is language official : " + languageStatus;
    }
}



