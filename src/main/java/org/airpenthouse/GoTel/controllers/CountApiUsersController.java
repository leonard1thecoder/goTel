package org.airpenthouse.GoTel.controllers;

import org.airpenthouse.GoTel.dtos.countApiUsers.CountApiUsersRequest;
import org.airpenthouse.GoTel.util.CountApiUsers;
import org.airpenthouse.GoTel.util.mappers.CountApiUsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


@RestController
@RequestMapping("api/countApiUsers")
public class CountApiUsersController extends CountApiUsers {

    @Autowired
    private CountApiUsersMapper mapper;


    @GetMapping("/")
    public ResponseEntity<List<CountApiUsersRequest>> getAllCounts() {
        var entities = prepareToGetAllCounties().stream().map(mapper::toDto).toList();
        if (entities.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(entities);
        }
    }
}
