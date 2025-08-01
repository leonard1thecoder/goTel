package org.airpenthouse.GoTel.entities.languanges;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.airpenthouse.GoTel.util.CommonEntityMethod;
import org.airpenthouse.GoTel.util.LOG;
import org.airpenthouse.GoTel.util.PropertiesUtilManager;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.concurrent.*;
import java.util.Set;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@AllArgsConstructor
public class WorldLanguagesEntity implements Callable<Set<WorldLanguagesEntity>>, Comparable<WorldLanguagesEntity> {
    @Getter
    private String countryName;
    @Getter
    private String languageName;
    @Getter
    private Boolean languageStatus;
    @Getter
    private String newLanguageName;
    private static WorldLanguagesEntity instance;
    private static final CommonEntityMethod commonEntityMethod;
    private String queryFindAllLanguages, queryFindCityWithLanguageAndCountryName, queryFindLanguagesByCountryName, queryFindLanguageByName, updateLanguageName, updateLanguageStatus, insertLanguage;
    private Set<WorldLanguagesEntity> languages;

    static {
        commonEntityMethod = new CommonEntityMethod();
    }

    public static WorldLanguagesEntity getInstance() {
        if (instance == null) {
            instance = new WorldLanguagesEntity();
        }
        return instance;
    }

    @Override
    public int compareTo(WorldLanguagesEntity obj) {
        return languageName.compareTo(obj.languageName);
    }

    private WorldLanguagesEntity() {
        super();
        queryFindCityWithLanguageAndCountryName = PropertiesUtilManager.getPropertiesValue("jdbc.query.findCityByCountryNameAndLanguage");
        queryFindAllLanguages = PropertiesUtilManager.getPropertiesValue("jdbc.query.allLanguage");
        queryFindLanguageByName = PropertiesUtilManager.getPropertiesValue("jdbc.query.findLanguageByName");
        queryFindLanguagesByCountryName = PropertiesUtilManager.getPropertiesValue("jdbc.query.findLanguageByCountryName");
        updateLanguageName = PropertiesUtilManager.getPropertiesValue("jdbc.update.updateLanguageName");
        updateLanguageStatus = PropertiesUtilManager.getPropertiesValue("jdbc.update.updateLanguageStatus");
        insertLanguage = PropertiesUtilManager.getPropertiesValue("jdbc.add.addLanguage");
    }

    private WorldLanguagesEntity(String country, String language, Boolean languageStatus) {
        this.countryName = country;
        this.languageName = language;
        this.languageStatus = languageStatus;
    }

    public static String ENTITY_TRIGGER;

    private Set<WorldLanguagesEntity> getCityByLanguageAndCountry(String languageName, String countryName) {
        try {
            preparedStatementFoRResultSet = commonEntityMethod.databaseConfig(queryFindCityWithLanguageAndCountryName);
            preparedStatementFoRResultSet.setString(1, languageName);
            preparedStatementFoRResultSet.setString(2, countryName);
            return addDatFromDBToList(true);
        } catch (SQLException | TimeoutException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Set<WorldLanguagesEntity> getAllLanguages() {
        try {
            preparedStatementFoRResultSet = commonEntityMethod.databaseConfig(queryFindAllLanguages);

            return addDatFromDBToList(true);
        } catch (SQLException | TimeoutException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean update;
    private Set<WorldLanguagesEntity> getLanguageByName() {
        try {
            preparedStatementFoRResultSet = commonEntityMethod.databaseConfig(queryFindLanguageByName);

            if (update) {
                preparedStatementFoRResultSet.setString(1, PropertiesUtilManager.getPropertiesValue("newLanguageName"));
                update = false;
            } else {
                preparedStatementFoRResultSet.setString(1, PropertiesUtilManager.getPropertiesValue("languageName"));
            }
            return addDatFromDBToList(true);
        } catch (SQLException | TimeoutException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Set<WorldLanguagesEntity> getLanguageByCountry() {
        try {
            preparedStatementFoRResultSet = commonEntityMethod.databaseConfig(queryFindLanguagesByCountryName);
            preparedStatementFoRResultSet.setString(1, PropertiesUtilManager.getPropertiesValue("countryName1"));

            return addDatFromDBToList(true);
        } catch (SQLException | TimeoutException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Set<WorldLanguagesEntity> updateWorldLanguage() {
        try {
            update = true;
            preparedStatementFoRExecuteUpdate = commonEntityMethod.databaseConfig(updateLanguageName);
            preparedStatementFoRExecuteUpdate.setString(1, PropertiesUtilManager.getPropertiesValue("newLanguageName"));
            preparedStatementFoRExecuteUpdate.setString(2, PropertiesUtilManager.getPropertiesValue("languageName"));
            try {
                int updateRow = preparedStatementFoRExecuteUpdate.executeUpdate();
                LOG.info("" + updateRow);
                if (updateRow > 0) {
                    update = true;
                    Set<WorldLanguagesEntity> set = getLanguageByName();

                    LOG.info(set.toString());
                    return set;
                } else {
                    LOG.info("Can't update, LANGUAGE to update not there");
                    languages = new CopyOnWriteArraySet<>();
                    return languages;
                }
            } catch (SQLIntegrityConstraintViolationException e) {
                LOG.info("Can't update , DUPLICATE VALUE");
                languages = new CopyOnWriteArraySet<>();
                return languages;
            }

        } catch (SQLException | TimeoutException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Set<WorldLanguagesEntity> updateWorldLanguageStatus() {
        try {
            preparedStatementFoRExecuteUpdate = commonEntityMethod.databaseConfig(updateLanguageStatus);
            if (Boolean.parseBoolean(PropertiesUtilManager.getPropertiesValue("newWorldLanguageStatus"))) {
                preparedStatementFoRExecuteUpdate.setString(1, "T");
            } else {
                preparedStatementFoRExecuteUpdate.setString(1, "F");
            }
            preparedStatementFoRExecuteUpdate.setString(2, PropertiesUtilManager.getPropertiesValue("languageName"));
            int updatedRow = preparedStatementFoRExecuteUpdate.executeUpdate();
            if (updatedRow > 0) {
                Set<WorldLanguagesEntity> set = getLanguageByName();
                LOG.info(set.toString());
                return set;
            } else {
                languages = new CopyOnWriteArraySet<>();
                return languages;
            }

        } catch (SQLException | TimeoutException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    private PreparedStatement preparedStatementFoRResultSet;
    private PreparedStatement preparedStatementFoRExecuteUpdate;
    private boolean added = false;

    private Set<WorldLanguagesEntity> insertWorldLanguage() {
        try {
            preparedStatementFoRExecuteUpdate = commonEntityMethod.databaseConfig(insertLanguage);
            LOG.info(PropertiesUtilManager.getPropertiesValue("countryName1"));
            preparedStatementFoRExecuteUpdate.setString(1, commonEntityMethod.getCountryCodeByCountryName(PropertiesUtilManager.getPropertiesValue("countryName1")));
            preparedStatementFoRExecuteUpdate.setString(2, PropertiesUtilManager.getPropertiesValue("languageName"));
            preparedStatementFoRExecuteUpdate.setString(3, "F");
            preparedStatementFoRExecuteUpdate.setDouble(4, 0.0);

            Set<WorldLanguagesEntity> set = getCityByLanguageAndCountry(PropertiesUtilManager.getPropertiesValue("languageName"), PropertiesUtilManager.getPropertiesValue("countryName1"));
            LOG.info("Testing : " + set);

            if (set.size() == 0) {
                preparedStatementFoRExecuteUpdate.executeUpdate();
                added = true;
                languages = getCityByLanguageAndCountry(PropertiesUtilManager.getPropertiesValue("languageName"), PropertiesUtilManager.getPropertiesValue("countryName1"));
                LOG.info("Data inserted");
            } else {
                if (added) {
                    added = false;
                } else {
                    LOG.info("LOGGING INSERTING FAILED, DUPLICATE ELEMENT : " + set);
                    languages.clear();
                }
            }
            return languages;
        } catch (SQLException | TimeoutException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Set<WorldLanguagesEntity> call() {
        return switch (ENTITY_TRIGGER) {
            case "FIND_ALL_LANGUAGES" -> getAllLanguages();
            case "FIND_LANGUAGE_BY_NAME" -> getLanguageByName();
            case "FIND_LANGUAGES_BY_COUNTRY" -> getLanguageByCountry();
            case "UPDATE_LANGUAGE_STATUS" -> updateWorldLanguageStatus();
            case "UPDATE_LANGUAGE_NAME" -> updateWorldLanguage();
            case "ADD_LANGUAGE" -> insertWorldLanguage();
            default -> null;
        };
    }

    public Set<WorldLanguagesEntity> addDatFromDBToList(boolean isResultSet) throws SQLException {
        languages = new ConcurrentSkipListSet<>();
        if (isResultSet) {
            ResultSet set = preparedStatementFoRResultSet.executeQuery();
            while (set.next()) {
                if (set.getObject(3).toString().charAt(0) == 'T')
                    languages.add(new WorldLanguagesEntity(set.getString(1), set.getString(2), true));
                else
                    languages.add(new WorldLanguagesEntity(set.getString(1), set.getString(2), false));
            }
        }
        LOG.info(languages.toString());
        return languages;
    }

    @Override
    public String toString() {
        return "country : " + countryName + " language : " + languageName + " is   official : " + languageStatus;
    }

}



