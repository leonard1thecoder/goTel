package org.airpenthouse.GoTel.controllers;


import org.airpenthouse.GoTel.entities.languanges.WorldLanguagesEntity;
import org.airpenthouse.GoTel.services.language.WorldLanguagesService;
import org.airpenthouse.GoTel.util.ExecutionHandler;
import org.airpenthouse.GoTel.util.PropertiesUtilManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/worldLanguages")
public class WorldLanguagesController extends ExecutionHandler {

    @GetMapping
    public Set<WorldLanguagesEntity> getAllLanguages() {
        WorldLanguagesService.SERVICE_HANDLER = "FIND_ALL_LANGUAGES";
        return super.executeWorldLanguagesService();
    }

    @GetMapping("/findLanguageByName/{languageName}")
    public Set<WorldLanguagesEntity> getLanguageByName(@PathVariable String languageName) {
        WorldLanguagesService.SERVICE_HANDLER = "FIND_LANGUAGE_BY_NAME";
        PropertiesUtilManager.setProperties("languageName", languageName);
        return super.executeWorldLanguagesService();
    }

    @GetMapping("/findLanguagesByCountryName/{countryName}")
    public Set<WorldLanguagesEntity> getLanguageByCountry(@PathVariable String countryName) {
        WorldLanguagesService.SERVICE_HANDLER = "FIND_LANGUAGES_BY_COUNTRY";
        PropertiesUtilManager.setProperties("countryName", countryName);
        return super.executeWorldLanguagesService();
    }

}

