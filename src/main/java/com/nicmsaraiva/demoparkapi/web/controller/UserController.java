package com.nicmsaraiva.demoparkapi.web.controller;

import com.nicmsaraiva.demoparkapi.entity.User;
import com.nicmsaraiva.demoparkapi.service.UserService;
import com.nicmsaraiva.demoparkapi.web.dto.UserCreateDTO;
import com.nicmsaraiva.demoparkapi.web.dto.UserResponseDTO;
import com.nicmsaraiva.demoparkapi.web.dto.UserUpdatePasswordDTO;
import com.nicmsaraiva.demoparkapi.web.dto.mapper.UserMapper;
import jakarta.validation.Valid;
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
    public ResponseEntity<UserResponseDTO> create(@Valid @RequestBody UserCreateDTO userCreateDTO) {
        User newUser = userService.save(UserMapper.toUser(userCreateDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDTO(newUser));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getById(@PathVariable Long id) {
        User returnedUser = userService.findById(id);
        return ResponseEntity.ok(UserMapper.toDTO(returnedUser));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @RequestBody UserUpdatePasswordDTO dto) {
        User updatedUser = userService.updatePassword(id, dto.getCurrentPassword(), dto.getNewPassword(), dto.getConfirmPassword());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getUsers() {
        List<User> users = userService.getAll();
        return ResponseEntity.ok(UserMapper.listToDTO(users));
    }
}
