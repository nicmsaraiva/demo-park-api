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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

        String requestBody = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/api/v1/users")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(requestBody))
                .andExpect(jsonPath("$.id").value(24L));
    }
}
