package org.airpenthouse.GoTel.services.language;


import org.airpenthouse.GoTel.entities.languanges.WorldLanguagesEntity;
import org.airpenthouse.GoTel.util.ExecutionHandler;


import org.airpenthouse.GoTel.util.mappers.LanguageMapper;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.Callable;

@Service
public final class WorldLanguagesService extends ExecutionHandler implements Callable<Set<WorldLanguagesEntity>> {


    private LanguageMapper mapper;
    public static String SERVICE_HANDLER;


    private Set<WorldLanguagesEntity> getAllLanguages() {
        WorldLanguagesEntity.ENTITY_TRIGGER = "FIND_ALL_LANGUAGES";
        return super.executeWorldLanguagesEntity();
    }

    private Set<WorldLanguagesEntity> getLanguageByName() {
        WorldLanguagesEntity.ENTITY_TRIGGER = "FIND_LANGUAGE_BY_NAME";
        return super.executeWorldLanguagesEntity();
    }

    private Set<WorldLanguagesEntity> getLanguageByCountry() {
        WorldLanguagesEntity.ENTITY_TRIGGER = "FIND_LANGUAGES_BY_COUNTRY";
        return super.executeWorldLanguagesEntity();
    }

    @Override
    public Set<WorldLanguagesEntity> call() throws Exception {
        switch (SERVICE_HANDLER) {
            case "FIND_ALL_LANGUAGES":
                return getAllLanguages();
            case "FIND_LANGUAGE_BY_NAME":
                return getLanguageByName();
            case "FIND_LANGUAGES_BY_COUNTRY":
                return getLanguageByCountry();
            //below cases needs DTO's to use, next push methods will be used
            case "UPDATE_LANGUAGE_STATUS":
            case "ADD_LANGUAGE":
            default:
                throw new RuntimeException("Error occurred while building your service");
        }

    }
}