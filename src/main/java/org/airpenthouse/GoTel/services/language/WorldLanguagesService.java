package org.airpenthouse.GoTel.services.language;

import org.airpenthouse.GoTel.dtos.languages.LanguageRequest;
import org.airpenthouse.GoTel.entities.languanges.WorldLanguagesEntity;
import org.airpenthouse.GoTel.util.mappers.LanguageMapper;
import org.springframework.stereotype.Service;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import static org.airpenthouse.GoTel.util.executors.WorldLanguagesExecutors.getInstances;

@Service
public final class WorldLanguagesService implements Callable<Set<LanguageRequest>> {

    private static WorldLanguagesService instance;
    private final LanguageMapper mapper;
    public static String SERVICE_HANDLER;

    private WorldLanguagesService() {
        mapper = getInstances().getLanguageMapper();
    }

    public static WorldLanguagesService getInstance() {
        if (instance == null) {
            instance = new WorldLanguagesService();
        }

        return instance;
    }

    private Set<LanguageRequest> getAllLanguages() {
        WorldLanguagesEntity.ENTITY_TRIGGER = "FIND_ALL_LANGUAGES";
        return getInstances().initializeWorldLanguagesEntity().parallelStream().map(mapper::mapper).collect(Collectors.toSet());
    }

    private Set<LanguageRequest> getLanguageByName() {
        WorldLanguagesEntity.ENTITY_TRIGGER = "FIND_LANGUAGE_BY_NAME";
        return getInstances().initializeWorldLanguagesEntity().parallelStream().map(mapper::mapper).collect(Collectors.toSet());
    }

    private Set<LanguageRequest> getLanguageByCountry() {
        WorldLanguagesEntity.ENTITY_TRIGGER = "FIND_LANGUAGES_BY_COUNTRY";
        return getInstances().initializeWorldLanguagesEntity().parallelStream().map(mapper::mapper).collect(Collectors.toSet());
    }

    @Override
    public Set<LanguageRequest> call() {
        return switch (SERVICE_HANDLER) {
            case "FIND_ALL_LANGUAGES" -> getAllLanguages();
            case "FIND_LANGUAGE_BY_NAME" -> getLanguageByName();
            case "FIND_LANGUAGES_BY_COUNTRY" -> getLanguageByCountry();
            case "UPDATE_LANGUAGE_STATUS" -> updateWorldLanguageStatus();
            case "UPDATE_LANGUAGE_NAME" -> updateWorldLanguage();
            case "ADD_LANGUAGE" -> insertWorldLanguage();
            default -> throw new RuntimeException("Error occurred while building your service");
        };
    }

    private Set<LanguageRequest> insertWorldLanguage() {
        WorldLanguagesEntity.ENTITY_TRIGGER = "ADD_LANGUAGE";
        return getInstances().initializeWorldLanguagesEntity().parallelStream().map(mapper::mapper).collect(Collectors.toSet());
    }

    private Set<LanguageRequest> updateWorldLanguage() {
        WorldLanguagesEntity.ENTITY_TRIGGER = "UPDATE_LANGUAGE_NAME";
        return getInstances().initializeWorldLanguagesEntity().parallelStream().map(mapper::mapper).collect(Collectors.toSet());
    }

    private Set<LanguageRequest> updateWorldLanguageStatus() {
        WorldLanguagesEntity.ENTITY_TRIGGER = "UPDATE_LANGUAGE_STATUS";
        return getInstances().initializeWorldLanguagesEntity().parallelStream().map(mapper::mapper).collect(Collectors.toSet());
    }
}