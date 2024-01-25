package com.nicmsaraiva.demoparkapi.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nicmsaraiva.demoparkapi.entity.User;
import com.nicmsaraiva.demoparkapi.service.UserService;
import com.nicmsaraiva.demoparkapi.web.dto.mapper.UserMapper;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    private final String BASE_URL = "/api/v1/users";

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void UserController_Create_ReturnCreated() throws Exception {
        User user = new User(24L, "nick@test.com", "pwd123", User.Role.ROLE_ADMIN);
        when(userService.save(any())).thenReturn(user);

        String body = objectMapper.writeValueAsString(UserMapper.toDTO(user));

        mockMvc.perform(post(BASE_URL)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(body))
                .andExpect(jsonPath("$.id").value(24L));
    }
    @Test
    public void UserController_GetById_ReturnOK() throws Exception {
        User user = new User(24L, "nick@test.com", "pwd123", User.Role.ROLE_ADMIN);
        when(userService.findById(any())).thenReturn(user);

        String body = objectMapper.writeValueAsString(UserMapper.toDTO(user));

        mockMvc.perform(get(BASE_URL + "/{id}", user.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(body))
                .andExpect(jsonPath("$.id").value(24L));
    }
    @Test
    public void UserController_GetUsers_ReturnOK() throws  Exception {
        User firstUser = new User(24L, "nick@test.com", "pwd123", User.Role.ROLE_ADMIN);
        User secondUser = new User(12L, "joe@test.com", "123pwd", User.Role.ROLE_CLIENT);
        List<User> userList = Arrays.asList(firstUser, secondUser);

        when(userService.getAll()).thenReturn(userList);

        String body = objectMapper.writeValueAsString(UserMapper.listToDTO(userList));

        mockMvc.perform(get(BASE_URL).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(body))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(24L))
                .andExpect(jsonPath("$[1].id").value(12L));
    }
}
