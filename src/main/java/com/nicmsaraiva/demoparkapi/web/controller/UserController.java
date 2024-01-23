package com.nicmsaraiva.demoparkapi.web.controller;

import com.nicmsaraiva.demoparkapi.entity.User;
import com.nicmsaraiva.demoparkapi.service.UserService;
import com.nicmsaraiva.demoparkapi.web.dto.UserCreateDTO;
import com.nicmsaraiva.demoparkapi.web.dto.UserResponseDTO;
import com.nicmsaraiva.demoparkapi.web.dto.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@RequestBody UserCreateDTO userCreateDTO) {
        User newUser = userService.save(UserMapper.toUser(userCreateDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDTO(newUser));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getById(@PathVariable Long id) {
        User returnedUser = userService.findById(id);
        return ResponseEntity.ok(UserMapper.toDTO(returnedUser));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> updatePassword(@PathVariable Long id, @RequestBody User user) {
        User updatedUser = userService.updatePassword(id, user.getPassword());
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getAll();
        return ResponseEntity.ok(users);
    }
}
