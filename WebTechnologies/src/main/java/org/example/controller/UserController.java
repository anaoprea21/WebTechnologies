package org.example.controller;

import org.example.helper.User;
import org.example.helper.UserList;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/user")
public class UserController {


    @Autowired
    private UserService service;

    @PostMapping()
    public ResponseEntity<User> create(@RequestBody final User userDetails) {
        final var registeredUser = service.create(userDetails);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/many")
    public ResponseEntity<List<User>> createManyUsers(@RequestBody final UserList usersDetails) {
        return ResponseEntity.ok(service.createManyUsers(usersDetails));
    }
}
