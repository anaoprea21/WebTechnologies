package org.example.service;

import org.example.entity.UserEntity;
import org.example.helper.UserRole;
import org.example.mapper.UserMapper;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.example.helper.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService service;

    @InjectMocks
    private UserMapper mapper;

    private User user;

    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("jimin");
        user.setEmail("jimin@example.com");
        user.setPseudonym("pseudo");
        user.setRole(UserRole.REPORTER);
        user.setPassword("pass");

        userEntity = new UserEntity();
        userEntity.setUsername("jimin");
        userEntity.setEmail("jimin@example.com");
        userEntity.setPseudonym("pseudo");
        userEntity.setRole(UserRole.REPORTER);
        userEntity.setPassword("pass");
    }

    @Test
    void createUserTest() {
        when(repository.save(any(UserEntity.class))).thenReturn(userEntity);
        User response = service.create(user);

        assertNotNull(response);
        assertEquals(user.getUsername(), response.getUsername());
        verify(repository, times(1)).save(any(UserEntity.class));
    }

}
