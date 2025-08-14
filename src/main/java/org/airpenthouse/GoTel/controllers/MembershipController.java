package org.airpenthouse.GoTel.controllers;


import org.airpenthouse.GoTel.dtos.membership.MembershipRequest;
import org.airpenthouse.GoTel.dtos.membership.RegisterMemberRequest;
import org.airpenthouse.GoTel.dtos.membership.UpdateMembershipStatus;
import org.airpenthouse.GoTel.entities.membership.MembershipEntity;
import org.airpenthouse.GoTel.services.membership.MembershipService;
import org.airpenthouse.GoTel.util.PropertiesUtilManager;
import org.airpenthouse.GoTel.util.executors.MembershipExecutor;
import org.airpenthouse.GoTel.util.mappers.MembershipMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Set;

@RestController
@RequestMapping("/api/membership")
public class MembershipController {

    @Autowired
    public MembershipMapper mapper;
    @Autowired
    public MembershipService executor;

    @PostMapping("/register")
    private ResponseEntity<Set<MembershipRequest>> registerMember(@RequestBody RegisterMemberRequest request, UriComponentsBuilder uriBuilder) {
        MembershipExecutor.setMapper(mapper);
        MembershipService.serviceHandler = "REGISTER_MEMBER";
        MembershipEntity entity = mapper.toEntity(request);
        var set = executor.initializeMembershipService(false, entity);

        if (set.isEmpty()) {
            return ResponseEntity.badRequest().build();
        } else {
            // creating status 201
            var uri = uriBuilder.path("/api/membership/getMemberByName/{memberName}").buildAndExpand(entity.getMemberName()).toUri();
            return ResponseEntity.created(uri).body(set);
        }
    }

    @PutMapping("/updateMembershipStatus")
    private ResponseEntity<Void> updateMembershipStatus(@RequestBody UpdateMembershipStatus request) {
        MembershipExecutor.setMapper(mapper);
        MembershipService.serviceHandler = "UPDATE_MEMBERSHIP_STATUS";
        MembershipEntity entity = mapper.toEntity(request);
        var set = executor.initializeMembershipService(false, entity);

        if (set.isEmpty()) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/updateMembershipToken")
    private ResponseEntity<Void> updateMembershipToken(@RequestBody UpdateMembershipStatus request) {
        MembershipExecutor.setMapper(mapper);
        MembershipService.serviceHandler = "UPDATE_MEMBERSHIP_TOKEN";
        MembershipEntity entity = mapper.toEntity(request);
        var set = executor.initializeMembershipService(false, entity);

        if (set.isEmpty()) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/getMemberByName/{memberName}")
    private ResponseEntity<Set<MembershipRequest>> getMemberByName(@PathVariable String memberName) {
        MembershipExecutor.setMapper(mapper);
        MembershipService.serviceHandler = "GET_MEMBER_BY_NAME";
        PropertiesUtilManager.setProperties("memberName", memberName);

        var set = executor.initializeMembershipService(true, null);

        if (set.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(set);
        }
    }

    @GetMapping("/getAllMembers")
    private ResponseEntity<Set<MembershipRequest>> getAllMembers() {
        MembershipExecutor.setMapper(mapper);
        MembershipService.serviceHandler = "GET_ALL_MEMBERS";
        var set = executor.initializeMembershipService(true, null);
        if (set.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(set);
        }
    }
}
