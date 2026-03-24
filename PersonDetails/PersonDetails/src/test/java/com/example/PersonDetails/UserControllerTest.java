package com.example.PersonDetails;

import com.example.PersonDetails.controller.UserController;
import com.example.PersonDetails.dto.UserRequest;
import com.example.PersonDetails.dto.UserResponse;
import com.example.PersonDetails.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createUser_shouldReturn201() throws Exception {
        UserRequest request = new UserRequest();
        request.setName("Akhib Khan");
        request.setEmail("akhib@gmail.com");
        request.setPhone("9876543210");

        UserResponse response = new UserResponse();
        response.setId("123");
        response.setName("Akhib Khan");
        response.setEmail("akhib@gmail.com");
        response.setPhone("9876543210");

        when(userService.createUser(any(UserRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("123"))
                .andExpect(jsonPath("$.name").value("Akhib Khan"))
                .andExpect(jsonPath("$.email").value("akhib@gmail.com"))
                .andExpect(jsonPath("$.phone").value("9876543210"));
    }

    @Test
    void getUserById_shouldReturn200() throws Exception {
        UserResponse response = new UserResponse();
        response.setId("123");
        response.setName("Akhib Khan");
        response.setEmail("akhib@gmail.com");
        response.setPhone("9876543210");

        when(userService.getUserById("123")).thenReturn(response);

        mockMvc.perform(get("/api/users/123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("123"))
                .andExpect(jsonPath("$.name").value("Akhib Khan"))
                .andExpect(jsonPath("$.email").value("akhib@gmail.com"))
                .andExpect(jsonPath("$.phone").value("9876543210"));
    }

    @Test
    void getAllUsers_shouldReturn200() throws Exception {
        UserResponse response = new UserResponse();
        response.setId("123");
        response.setName("Akhib Khan");
        response.setEmail("akhib@gmail.com");
        response.setPhone("9876543210");

        when(userService.getAllUsers(0, 10)).thenReturn(List.of(response));

        mockMvc.perform(get("/api/users?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("123"))
                .andExpect(jsonPath("$[0].name").value("Akhib Khan"));
    }

    @Test
    void updateUser_shouldReturn200() throws Exception {
        UserRequest request = new UserRequest();
        request.setName("Updated Name");
        request.setEmail("updated@gmail.com");
        request.setPhone("1111111111");

        UserResponse response = new UserResponse();
        response.setId("123");
        response.setName("Updated Name");
        response.setEmail("updated@gmail.com");
        response.setPhone("1111111111");

        when(userService.updateUser(eq("123"), any(UserRequest.class))).thenReturn(response);

        mockMvc.perform(put("/api/users/123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("123"))
                .andExpect(jsonPath("$.name").value("Updated Name"))
                .andExpect(jsonPath("$.email").value("updated@gmail.com"))
                .andExpect(jsonPath("$.phone").value("1111111111"));
    }

    @Test
    void deleteUser_shouldReturn200() throws Exception {
        doNothing().when(userService).deleteUser("123");

        mockMvc.perform(delete("/api/users/123"))
                .andExpect(status().isOk());
    }
}
