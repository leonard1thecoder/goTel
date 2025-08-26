package org.airpenthouse.GoTel.controllers;

import org.airpenthouse.GoTel.dtos.administrators.*;
import org.airpenthouse.GoTel.services.administrator.AdministratorsService;
import org.airpenthouse.GoTel.util.PropertiesUtilManager;
import org.airpenthouse.GoTel.util.executors.AdministratorsExecutors;
import org.airpenthouse.GoTel.util.mappers.AdministratorsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Set;

@RestController
@RequestMapping("api/administrators")
public class AdministratorsController {


    @Autowired
    public AdministratorsMapper mapper;

    @Autowired
    public AdministratorsService service;


    @PostMapping("/registerAdministrator")
    private ResponseEntity<Set<AdministratorsRequest>> registorAdministror(@RequestBody RegisterAdministratorsRequest request, UriComponentsBuilder builder) {
        var entity = mapper.ToEntity(request);
        AdministratorsService.serviceHandler = "REGISTRATOR_ADMINISTRATOR";
        AdministratorsExecutors.setMapper(mapper);
        var entity2 = service.initializeCountriesService(false, entity);
        if (entity2.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else if (entity2 == null)
            return ResponseEntity.badRequest().build();
        else {
            var uri = builder.path("/api/administrators/findAdministratorbyName/{administratorName}").buildAndExpand(entity.getAdministratorName()).toUri();
            return ResponseEntity.created(uri).body(entity2);
        }
    }

    @PostMapping("/administratorLogin")
    private ResponseEntity<Set<AdministratorsRequest>> administrorLogin(@RequestBody AdministratoLoginRequest request) {
        var entity = mapper.ToEntity(request);
        AdministratorsService.serviceHandler = "ADMINISTRATOR_LOGIN";

        AdministratorsExecutors.setMapper(mapper);
        var entity2 = service.initializeCountriesService(false, entity);

        if (entity2.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else if (entity2 == null) {
            return ResponseEntity.badRequest().build();
        } else
            return ResponseEntity.ok(entity2);
    }

    @PostMapping("/updatePassword")
    private ResponseEntity<Void> updateAdministratorPassword(@RequestBody UpdatePasswordRequest request) {
        AdministratorsExecutors.setMapper(mapper);
        var entity = mapper.ToEntity(request);
        AdministratorsService.serviceHandler = "UPDATE_ADMINISTRATOR_PASSWORD";

        var entity2 = service.initializeCountriesService(false, entity);

        if (entity2.isEmpty())
            return ResponseEntity.notFound().build();
        else if (entity2 == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.noContent().build();

    }

    @PostMapping("/updateToken")
    private ResponseEntity<Void> updateAdministratorToken(@RequestBody UpdateTokenRequest request) {
        AdministratorsExecutors.setMapper(mapper);
        AdministratorsService.serviceHandler = "UPDATE_ADMINISTRATOR_TOKEN";

        var entity = mapper.ToEntity(request);
        var entity2 = service.initializeCountriesService(false, entity);

        if (entity2.isEmpty())
            return ResponseEntity.notFound().build();
        else if (entity2 == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.noContent().build();

    }

    @GetMapping("/getAdministratorByEmail/{administratorEmailAddress}")
    private ResponseEntity<Set<AdministratorsRequest>> findAdminstratorsByEmail(@PathVariable String administratorEmailAddress) {
        AdministratorsExecutors.setMapper(mapper);
        AdministratorsService.serviceHandler = "FIND_ADMINISTRATOR_BY_EMAIL";

        PropertiesUtilManager.setProperties("administratorEmailAddress", administratorEmailAddress);
        var entity2 = service.initializeCountriesService(true, null);

        if (entity2.isEmpty())
            return ResponseEntity.notFound().build();
        else if (entity2 == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.ok(entity2);
    }

    @GetMapping("/getAdministratorByName/{administratorName}")
    private ResponseEntity<Set<AdministratorsRequest>> findAdminstratorsByName(@PathVariable String administratorName) {
        AdministratorsExecutors.setMapper(mapper);
        AdministratorsService.serviceHandler = "FIND_ADMINISTRATOR_BY_NAME";

        PropertiesUtilManager.setProperties("administratorEmailAddress", administratorName);
        var entity2 = service.initializeCountriesService(true, null);

        if (entity2.isEmpty())
            return ResponseEntity.notFound().build();
        else if (entity2 == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.ok(entity2);

    }

    @GetMapping("/getAdministrators")
    private ResponseEntity<Set<AdministratorsRequest>> findAllAdminstrators() {
        AdministratorsExecutors.setMapper(mapper);
        AdministratorsService.serviceHandler = "FIND_ALL_ADMINISTRATORS";

        var entity2 = service.initializeCountriesService(true, null);

        if (entity2.isEmpty())
            return ResponseEntity.notFound().build();
        else if (entity2 == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.ok(entity2);
    }
}
