package org.perez.maintenance.users.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.perez.maintenance.users.model.User;
import org.perez.maintenance.users.repository.UserRepository;

import java.lang.reflect.Array;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        user = new User();
        user.setId(1L);
        user.setName("First");
        user.setLastName("Test");
        user.setLogin("test");
        user.setPassword("test");
        user.setState("Active");
    }

    @Test
    void findAll() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));
        assertNotNull(userService.findAll());
    }

    @Test
    void save() {
        when(userRepository.save(user)).thenReturn(user);
        assertNotNull(userService.save(user));
    }

    @Test
    void findById() {
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(user));
        assertNotNull(userService.findById(1L));
    }

    @Test
    void findByLogin() {
        when(userRepository.findByLogin("test")).thenReturn(java.util.Optional.ofNullable(user));
        assertNotNull(userService.findByLogin("test"));
    }

    @Test
    void existsByLogin() {
        when(userRepository.existsByLogin("test")).thenReturn(true);
        assertTrue(userService.existsByLogin("test"));
    }
}