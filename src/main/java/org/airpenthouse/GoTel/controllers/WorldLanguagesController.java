package org.airpenthouse.GoTel.controllers;


import org.airpenthouse.GoTel.dtos.languages.InsertWorldLanguageRequest;
import org.airpenthouse.GoTel.dtos.languages.LanguageRequest;
import org.airpenthouse.GoTel.dtos.languages.UpdateLanguageRequest;
import org.airpenthouse.GoTel.dtos.languages.UpdateLanguageStatus;
import org.airpenthouse.GoTel.services.language.WorldLanguagesService;
import org.airpenthouse.GoTel.util.LOG;
import org.airpenthouse.GoTel.util.PropertiesUtilManager;
import org.airpenthouse.GoTel.util.mappers.LanguageMapper;
import org.airpenthouse.GoTel.util.mappers.LanguageMapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Set;

import static org.airpenthouse.GoTel.util.executors.WorldLanguagesExecutors.getInstances;

@RestController
@RequestMapping("/api/worldLanguages")
public class WorldLanguagesController {
    @Autowired
    public LanguageMapper mapper = new LanguageMapperImpl();
    private Set<LanguageRequest> entities;

    @Autowired
    public WorldLanguagesController() {
        getInstances().build(mapper);
    }

    @GetMapping("/findAllLanguages")
    public ResponseEntity<Set<LanguageRequest>> getAllLanguages() {
        WorldLanguagesService.SERVICE_HANDLER = "FIND_ALL_LANGUAGES";
        entities = getInstances().initializeWorldLanguageService();

        if (entities.isEmpty())
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(entities);
    }

    @GetMapping("/findLanguageByName/{languageName}")
    public ResponseEntity<Set<LanguageRequest>> getLanguageByName(@PathVariable String languageName) {
        WorldLanguagesService.SERVICE_HANDLER = "FIND_LANGUAGE_BY_NAME";
        PropertiesUtilManager.setProperties("languageName", languageName);
        entities = getInstances().initializeWorldLanguageService();

        if (entities.isEmpty())
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(entities);
    }

    @GetMapping("/findLanguagesByCountryName/{countryName}")
    public ResponseEntity<Set<LanguageRequest>> getLanguageByCountry(@PathVariable String countryName) {
        WorldLanguagesService.SERVICE_HANDLER = "FIND_LANGUAGES_BY_COUNTRY";
        PropertiesUtilManager.setProperties("countryName1", countryName);
        entities = getInstances().initializeWorldLanguageService();

        if (entities.isEmpty())
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(entities);
    }


    @PutMapping("/updateLanguageName")
    public ResponseEntity<Void> updateLanguageName(@RequestBody UpdateLanguageRequest request) {
        WorldLanguagesService.SERVICE_HANDLER = "UPDATE_LANGUAGE_NAME";
        var dto = mapper.toWorldEntity(request);
        LOG.info(dto.getLanguageName() + " " + dto.getNewLanguageName());
        PropertiesUtilManager.setProperties("newLanguageName", dto.getNewLanguageName());
        PropertiesUtilManager.setProperties("languageName", dto.getLanguageName());
        entities = getInstances().initializeWorldLanguageService();

        if (entities == null)
            return ResponseEntity.badRequest().build();
        if (entities.isEmpty())
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.noContent().build();
    }

    @PutMapping("/updateLanguageStatus")
    public ResponseEntity<Void> updateLanguageStatus(@RequestBody UpdateLanguageStatus request) {
        WorldLanguagesService.SERVICE_HANDLER = "UPDATE_LANGUAGE_STATUS";
        var dto = mapper.toWorldEntity(request);
        PropertiesUtilManager.setProperties("newWorldLanguageStatus", String.valueOf(dto.getLanguageStatus()));
        PropertiesUtilManager.setProperties("languageName", dto.getLanguageName());
        entities = getInstances().initializeWorldLanguageService();

        if (entities.isEmpty())
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.noContent().build();
    }

    @PostMapping("/insertLanguage")
    public ResponseEntity<Set<LanguageRequest>> insertLanguage(@RequestBody InsertWorldLanguageRequest request, UriComponentsBuilder uriBuilder) {
        WorldLanguagesService.SERVICE_HANDLER = "ADD_LANGUAGE";

        var entity = mapper.toWorldEntity(request);
        PropertiesUtilManager.setProperties("countryName1", entity.getCountryName());
        PropertiesUtilManager.setProperties("languageName", entity.getLanguageName());
        LOG.info(entity.getCountryName());
        LOG.info(entity.getLanguageName());
        // creating status 201
        entities = getInstances().initializeWorldLanguageService();
        if (entities.isEmpty())
            return ResponseEntity.badRequest().build();
        else {
            var uri = uriBuilder.path("/api/worldLanguages/findLanguageByName/{languageName}").buildAndExpand(entity.getLanguageName()).toUri();
            return ResponseEntity.created(uri).body(getInstances().initializeWorldLanguageService());
        }
    }
}

