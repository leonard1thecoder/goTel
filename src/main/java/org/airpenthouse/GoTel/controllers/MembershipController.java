package org.airpenthouse.GoTel.controllers;

import org.airpenthouse.GoTel.dtos.membership.MembershipRequest;
import org.airpenthouse.GoTel.dtos.membership.RegisterMemberRequest;
import org.airpenthouse.GoTel.dtos.membership.UpdateMembershipStatus;
import org.airpenthouse.GoTel.entities.membership.MembershipEntity;
import org.airpenthouse.GoTel.services.membership.MembershipService;
import org.airpenthouse.GoTel.util.PropertiesUtilManager;
import org.airpenthouse.GoTel.util.mappers.MembershipMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Set;

@RestController
@RequestMapping("/api/membership")
public class MembershipController extends MembershipService {

    private final MembershipMapper mapper;

    public MembershipController() {
        mapper = super.getMapper();
    }

    @PostMapping("/register")
    private ResponseEntity<Set<MembershipRequest>> registerMember(@RequestBody RegisterMemberRequest request, UriComponentsBuilder uriBuilder) {
        MembershipService.serviceHandler = "REGISTER_MEMBER";
        MembershipEntity entity = mapper.toEntity(request);
        PropertiesUtilManager.setProperties("privilegeId", String.valueOf(entity.getPrivilegeId()));
        PropertiesUtilManager.setProperties("memberName", entity.getMemberName());
        PropertiesUtilManager.setProperties("membershipEmailAddress", entity.getMemberEmailAddress());
        PropertiesUtilManager.setProperties("registrationDate", entity.getMemberEmailAddress());
        PropertiesUtilManager.setProperties("membershipToken", entity.getMemberToken());
        PropertiesUtilManager.setProperties("membershipStatus", String.valueOf(entity.getMembershipStatus()));
        var set = initializeMembershipService();

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

        MembershipService.serviceHandler = "UPDATE_MEMBERSHIP_STATUS";
        MembershipEntity entity = mapper.toEntity(request);
        PropertiesUtilManager.setProperties("membershipStatus", String.valueOf(entity.getMembershipStatus()));
        PropertiesUtilManager.setProperties("modifiedDate", entity.getRegisteredDate().toString());
        PropertiesUtilManager.setProperties("memberName", entity.getMemberName());
        var set = initializeMembershipService();

        if (set.isEmpty()) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/updateMembershipToken")
    private ResponseEntity<Void> updateMembershipToken(@RequestBody UpdateMembershipStatus request) {
        MembershipService.serviceHandler = "UPDATE_MEMBERSHIP_TOKEN";
        MembershipEntity entity = mapper.toEntity(request);
        PropertiesUtilManager.setProperties("membershipToken", String.valueOf(entity.getMemberToken()));
        PropertiesUtilManager.setProperties("modifiedDate", entity.getRegisteredDate().toString());
        PropertiesUtilManager.setProperties("memberName", entity.getMemberName());
        var set = initializeMembershipService();

        if (set.isEmpty()) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/getMemberByName/{memberName}")
    private ResponseEntity<Set<MembershipRequest>> getMemberByName(@PathVariable String memberName) {
        MembershipService.serviceHandler = "GET_MEMBER_BY_NAME";
        PropertiesUtilManager.setProperties("memberName", memberName);

        var set = initializeMembershipService();

        if (set.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(set);
        }
    }

    @GetMapping("/getAllMembers")
    private ResponseEntity<Set<MembershipRequest>> getAllMembers() {
        MembershipService.serviceHandler = "GET_ALL_MEMBERS";
        var set = initializeMembershipService();

        if (set.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(set);
        }
    }
}
