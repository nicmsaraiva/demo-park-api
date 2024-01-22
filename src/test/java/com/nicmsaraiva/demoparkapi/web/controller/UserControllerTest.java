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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)

public class UserControllerTest {

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

        mockMvc.perform(post("/api/v1/users")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(body))
                .andExpect(jsonPath("$.id").value(24L));
    }
    @Test
    public void UserController_GetById_ReturnOk() throws Exception {
        User user = new User(12L, "joe@test.com", "123pwd", User.Role.ROLE_CLIENT);
        when(userService.findById(any())).thenReturn(user);

        String responseBody = objectMapper.writeValueAsString(user);

        mockMvc.perform(get("/api/v1/users/{id}", user.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(responseBody))
                .andExpect(jsonPath("$.id").value(12L));
    }
}
