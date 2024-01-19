package com.nicmsaraiva.demoparkapi.web.controller;

import com.nicmsaraiva.demoparkapi.entity.User;
import com.nicmsaraiva.demoparkapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> create(@RequestBody  User user) {
        User newUser = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        User returnedUser = userService.findById(id);
        return ResponseEntity.ok(returnedUser);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> updatePassword(@PathVariable Long id, @RequestBody User user) {
        User updatedUser = userService.updatePassword(id, user.getPassword());
        return ResponseEntity.ok(updatedUser);
    }
}
