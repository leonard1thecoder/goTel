package org.airpenthouse.GoTel.controllers;

import org.airpenthouse.GoTel.util.CountApiUsers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/countApiUsers")
public class CountApiUsersController extends CountApiUsers {

    @GetMapping("/")
    public ResponseEntity<List<CountApiUsers>> getAllCounts() {
        var entities = prepareToGetAllCounties();
        if (entities.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(entities);
        }
    }
}
