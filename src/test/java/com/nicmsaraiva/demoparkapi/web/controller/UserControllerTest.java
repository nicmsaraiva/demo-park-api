package com.nicmsaraiva.demoparkapi.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nicmsaraiva.demoparkapi.entity.User;
import com.nicmsaraiva.demoparkapi.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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

        String body = objectMapper.writeValueAsString(user);

        mockMvc.perform(post(BASE_URL)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(body))
                .andExpect(jsonPath("$.id").value(24L));
    }
    @Test
    public void UserController_GetById_ReturnOK() throws Exception {
        User user = new User(12L, "joe@test.com", "123pwd", User.Role.ROLE_CLIENT);
        when(userService.findById(any())).thenReturn(user);

        String responseBody = objectMapper.writeValueAsString(user);

        mockMvc.perform(get(BASE_URL + "/{id}", user.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(responseBody))
                .andExpect(jsonPath("$.id").value(12L));
    }
    @Test
    public void UserController_GetUsers_ReturnOK() throws  Exception {
        User firstUser = new User(24L, "nick@test.com", "pwd123", User.Role.ROLE_ADMIN);
        User secondUser = new User(12L, "joe@test.com", "123pwd", User.Role.ROLE_CLIENT);
        List<User> userList = Arrays.asList(firstUser, secondUser);

        when(userService.getAll()).thenReturn(userList);

        String responseBody = objectMapper.writeValueAsString(userList);

        mockMvc.perform(get(BASE_URL).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(responseBody))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(24L))
                .andExpect(jsonPath("$[1].id").value(12L));
    }

    @Test
    public void UserController_UpdatePassword_ReturnOK() throws Exception {
        User user = new User(12L, "joe@test.com", "password321", User.Role.ROLE_CLIENT);
        when(userService.updatePassword(anyLong(), anyString()))
                .thenAnswer(invocation -> {
                    Long userId = invocation.getArgument(0);
                    String newPassword = invocation.getArgument(1);

                    user.setPassword(newPassword);
                    return user;
                });

        String body = objectMapper.writeValueAsString(user);

        mockMvc.perform(patch(BASE_URL+"/{id}", user.getId())
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(body))
                .andExpect(jsonPath("$.id").value(12L))
                .andExpect(jsonPath("$.password").value("password321"));
    }
}
