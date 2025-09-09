package org.airpenthouse.GoTel.controllers;

import org.airpenthouse.GoTel.dtos.sponsors.SponsorsDonateRequest;
import org.airpenthouse.GoTel.dtos.sponsors.SponsorsRequest;
import org.airpenthouse.GoTel.services.sponsors.SponsorsService;
import org.airpenthouse.GoTel.util.PropertiesUtilManager;
import org.airpenthouse.GoTel.util.executors.SponsorsExecutors;
import org.airpenthouse.GoTel.util.mappers.SponsorsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RequestMapping("/api/sponsors")
@RestController
@CrossOrigin(origins="http://localhost:4200")
public class SponsorsController {

    @Autowired
    public SponsorsMapper mapper;
    @Autowired
    public SponsorsService service;

    @PostMapping("/sponsorDonote")
    public ResponseEntity<List<SponsorsRequest>> sponsorsDonate(@RequestBody SponsorsDonateRequest request, UriComponentsBuilder builder) {
        var entity = mapper.toEntity(request);
        SponsorsExecutors.setMapper(mapper);
        SponsorsService.serviceHandle = "DONATE";

        var entity2 = service.initializeSponsorsService(false, entity);
        if (entity2 == null) {
            return ResponseEntity.badRequest().build();
        } else if (entity2.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            var uri = builder.path("/api/sponsors/getSponsorByName/{sponsorName}").buildAndExpand(entity.getSponsorName()).toUri();
            return ResponseEntity.created(uri).body(entity2);
        }
    }

    @GetMapping("/getSponsorByName/{sponsorName}")
    public ResponseEntity<List<SponsorsRequest>> getAllSponsors(@PathVariable String sponsorName) {
        SponsorsExecutors.setMapper(mapper);
        SponsorsService.serviceHandle = "GET_SPONSOR_BY_NAME";
        PropertiesUtilManager.setProperties("sponsorName", sponsorName);
        var entity = service.initializeSponsorsService(true, null);

        if (entity.isEmpty())
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(entity);
    }

    @GetMapping("/getAllSponsors")
    public ResponseEntity<List<SponsorsRequest>> getSponsorByName() {
        SponsorsExecutors.setMapper(mapper);
        SponsorsService.serviceHandle = "GET_ALL_SPONSOR";
        var entity = service.initializeSponsorsService(true, null);
        if (entity.isEmpty())
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(entity);
    }
}
