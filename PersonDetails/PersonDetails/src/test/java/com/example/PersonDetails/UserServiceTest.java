package com.example.PersonDetails;

import com.example.PersonDetails.dto.UserRequest;
import com.example.PersonDetails.dto.UserResponse;
import com.example.PersonDetails.entity.User;
import com.example.PersonDetails.repository.UserRepository;
import com.example.PersonDetails.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void deleteUser_shouldDeleteSuccessfully() {
        when(userRepository.existsById("123")).thenReturn(true);

        assertDoesNotThrow(() -> userService.deleteUser("123"));
    }

    @Test
    void updateUser_shouldReturnUpdatedUserResponse() {
        UserRequest request = new UserRequest();
        request.setName("Updated Name");
        request.setEmail("updated@gmail.com");
        request.setPhone("1111111111");

        User existingUser = new User();
        existingUser.setId("123");
        existingUser.setName("Akhib Khan");
        existingUser.setEmail("akhib@gmail.com");
        existingUser.setPhone("9876543210");

        User updatedUser = new User();
        updatedUser.setId("123");
        updatedUser.setName("Updated Name");
        updatedUser.setEmail("updated@gmail.com");
        updatedUser.setPhone("1111111111");

        when(userRepository.findById("123")).thenReturn(java.util.Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        UserResponse response = userService.updateUser("123", request);

        assertNotNull(response);
        assertEquals("123", response.getId());
        assertEquals("Updated Name", response.getName());
        assertEquals("updated@gmail.com", response.getEmail());
        assertEquals("1111111111", response.getPhone());
    }

    @Test
    void getAllUsers_shouldReturnListOfUserResponse() {
        User user = new User();
        user.setId("123");
        user.setName("Akhib Khan");
        user.setEmail("akhib@gmail.com");
        user.setPhone("9876543210");

        org.springframework.data.domain.Page<User> page = new org.springframework.data.domain.PageImpl<>(java.util.List.of(user));
        when(userRepository.findAll(org.springframework.data.domain.PageRequest.of(0, 10))).thenReturn(page);

        java.util.List<UserResponse> response = userService.getAllUsers(0, 10);

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals("123", response.get(0).getId());
        assertEquals("Akhib Khan", response.get(0).getName());
    }

    @Test
    void getUserById_shouldReturnUserResponse() {
        User savedUser = new User();
        savedUser.setId("123");
        savedUser.setName("Akhib Khan");
        savedUser.setEmail("akhib@gmail.com");
        savedUser.setPhone("9876543210");

        when(userRepository.findById("123")).thenReturn(java.util.Optional.of(savedUser));

        UserResponse response = userService.getUserById("123");

        assertNotNull(response);
        assertEquals("123", response.getId());
        assertEquals("Akhib Khan", response.getName());
        assertEquals("akhib@gmail.com", response.getEmail());
        assertEquals("9876543210", response.getPhone());
    }

    @Test
    void createUser_shouldReturnUserResponse() {
        UserRequest request = new UserRequest();
        request.setName("Akhib Khan");
        request.setEmail("akhib@gmail.com");
        request.setPhone("9876543210");

        User savedUser = new User();
        savedUser.setId("123");
        savedUser.setName("Akhib Khan");
        savedUser.setEmail("akhib@gmail.com");
        savedUser.setPhone("9876543210");

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        UserResponse response = userService.createUser(request);

        assertNotNull(response);
        assertEquals("123", response.getId());
        assertEquals("Akhib Khan", response.getName());
        assertEquals("akhib@gmail.com", response.getEmail());
        assertEquals("9876543210", response.getPhone());
    }
}
