package org.example.mapper;

import org.example.entity.UserEntity;
import org.example.helper.User;
import org.example.helper.UserRole;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public static User toResponse(UserEntity user) {
        User response = new User();

        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setPassword(user.getPassword());

        response.setRole(user.getRole());
        if (user.getRole() == UserRole.REPORTER)
            response.setPseudonym(user.getPseudonym());
        return response;
    }

    public static UserEntity toEntity(User user) {
        UserEntity userEntity = new UserEntity();

        userEntity.setUsername(user.getUsername());
        userEntity.setEmail(user.getEmail());
        userEntity.setPassword(user.getPassword());

        userEntity.setRole(user.getRole());
        if (user.getRole() == UserRole.REPORTER)
            userEntity.setPseudonym(user.getPseudonym());

        return userEntity;
    }
}
