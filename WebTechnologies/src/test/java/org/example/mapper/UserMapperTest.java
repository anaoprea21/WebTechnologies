package org.example.mapper;

import org.example.helper.User;
import org.example.helper.UserRole;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserMapperTest {

    private UserMapper userMapper = new UserMapper();

    @Test
    void toEntityTest() {
        final var user = new User("usernamed", "email@email.com", "password", UserRole.SEEKER, "no");

        final var userEntity = userMapper.toEntity(user);

        assertNotNull(userEntity);
        assertNotNull(userEntity.getEmail());
        assertEquals("usernamed", userEntity.getUsername());
        assertNotNull(userEntity.getPassword());
        assertEquals(UserRole.SEEKER, userEntity.getRole());
    }
}
