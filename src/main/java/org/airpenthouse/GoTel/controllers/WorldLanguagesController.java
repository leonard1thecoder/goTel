package org.airpenthouse.GoTel.controllers;


import org.airpenthouse.GoTel.dtos.languages.InsertWorldLanguageRequest;
import org.airpenthouse.GoTel.dtos.languages.LanguageRequest;
import org.airpenthouse.GoTel.dtos.languages.UpdateLanguageRequest;
import org.airpenthouse.GoTel.dtos.languages.UpdateLanguageStatus;
import org.airpenthouse.GoTel.services.language.WorldLanguagesService;
import org.airpenthouse.GoTel.util.Log;
import org.airpenthouse.GoTel.util.PropertiesUtilManager;
import org.airpenthouse.GoTel.util.executors.WorldLanguagesExecutors;
import org.airpenthouse.GoTel.util.mappers.LanguageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.Set;


@RestController
@RequestMapping("/api/worldLanguages")
public class WorldLanguagesController {

    @Autowired
    public LanguageMapper mapper;
    private Set<LanguageRequest> entities;
    @Autowired
    public WorldLanguagesService executor;



    @GetMapping("/findAllLanguages")
    public ResponseEntity<Set<LanguageRequest>> getAllLanguages() {
        WorldLanguagesExecutors.setLanguageMapper(mapper);
        WorldLanguagesService.SERVICE_HANDLER = "FIND_ALL_LANGUAGES";
        entities = executor.initializeWorldLanguageService(true, null);

        if (entities.isEmpty())
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(entities);
    }

    @GetMapping("/findLanguageByName/{languageName}")
    public ResponseEntity<Set<LanguageRequest>> getLanguageByName(@PathVariable String languageName) {
        WorldLanguagesExecutors.setLanguageMapper(mapper);
        WorldLanguagesService.SERVICE_HANDLER = "FIND_LANGUAGE_BY_NAME";
        PropertiesUtilManager.setProperties("languageName", languageName);
        Log.info("buhbuhbuhhb" + mapper + "\n" + executor);
        executor.setService(executor);
        entities = executor.initializeWorldLanguageService(true, null);

        if (entities.isEmpty())
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(entities);
    }

    @GetMapping("/findLanguagesByCountryName/{countryName}")
    public ResponseEntity<Set<LanguageRequest>> getLanguageByCountry(@PathVariable String countryName) {
        WorldLanguagesExecutors.setLanguageMapper(mapper);
        WorldLanguagesService.SERVICE_HANDLER = "FIND_LANGUAGES_BY_COUNTRY";
        PropertiesUtilManager.setProperties("countryName1", countryName);
        executor.setService(executor);
        entities = executor.initializeWorldLanguageService(true, null);

        if (entities.isEmpty())
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(entities);
    }


    @PostMapping("/{memberUsername}/updateLanguageName")
    public ResponseEntity<Void> updateLanguageName(@PathVariable String memberUsername, @RequestHeader String memberToken, @RequestBody UpdateLanguageRequest request) {
        WorldLanguagesExecutors.setLanguageMapper(mapper);
        WorldLanguagesService.SERVICE_HANDLER = "UPDATE_LANGUAGE_NAME";
        var dto = mapper.toWorldEntity(request);
        Log.info(dto.getLanguageName() + " " + dto.getNewLanguageName());
        PropertiesUtilManager.setProperties("newLanguageName", dto.getNewLanguageName());
        PropertiesUtilManager.setProperties("languageName", dto.getLanguageName());
        PropertiesUtilManager.setProperties("memberToken", memberToken);
        PropertiesUtilManager.setProperties("memberUsername", memberUsername);
        executor.setService(executor);
        entities = executor.initializeWorldLanguageService(false, dto);
        if (executor.checkMemberShipStatusAndTokenMatch()) {
            if (entities == null)
                return ResponseEntity.badRequest().build();
            if (entities.isEmpty())
                return ResponseEntity.badRequest().build();
            else
                return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{memberToken}/updateLanguageStatus")
    public ResponseEntity<Void> updateLanguageStatus(@PathVariable String memberUsername, @RequestHeader String memberToken, @RequestBody UpdateLanguageStatus request) {
        WorldLanguagesExecutors.setLanguageMapper(mapper);
        WorldLanguagesService.SERVICE_HANDLER = "UPDATE_LANGUAGE_STATUS";
        var dto = mapper.toWorldEntity(request);
        PropertiesUtilManager.setProperties("newWorldLanguageStatus", String.valueOf(dto.getLanguageStatus()));
        PropertiesUtilManager.setProperties("languageName", dto.getLanguageName());
        executor.setService(executor);
        entities = executor.initializeWorldLanguageService(true, dto);
        PropertiesUtilManager.setProperties("memberToken", memberToken);
        PropertiesUtilManager.setProperties("memberUsername", memberUsername);
        if (executor.checkMemberShipStatusAndTokenMatch()) {
            if (entities == null)
                return ResponseEntity.badRequest().build();
            if (entities.isEmpty())
                return ResponseEntity.badRequest().build();
            else
                return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("{memberToken}/insertLanguage")
    public ResponseEntity<Set<LanguageRequest>> insertLanguage(@PathVariable String memberUsername, @RequestHeader String memberToken, @RequestBody InsertWorldLanguageRequest request, UriComponentsBuilder uriBuilder) {
        WorldLanguagesService.SERVICE_HANDLER = "ADD_LANGUAGE";
        WorldLanguagesExecutors.setLanguageMapper(mapper);
        var entity = mapper.toWorldEntity(request);
        PropertiesUtilManager.setProperties("memberToken", memberToken);
        PropertiesUtilManager.setProperties("memberUsername", memberUsername);
        PropertiesUtilManager.setProperties("countryName1", entity.getCountryName());
        PropertiesUtilManager.setProperties("languageName", entity.getLanguageName());
        Log.info(entity.getCountryName());
        Log.info(entity.getLanguageName());
        // creating status 201
        executor.setService(executor);
        entities = executor.initializeWorldLanguageService(false, entity);
        if (executor.checkMemberShipStatusAndTokenMatch()) {
            if (entities.isEmpty())
                return ResponseEntity.badRequest().build();
            else {
                var uri = uriBuilder.path("/api/worldLanguages/findLanguageByName/{languageName}").buildAndExpand(entity.getLanguageName()).toUri();
                return ResponseEntity.created(uri).body(entities);
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}

