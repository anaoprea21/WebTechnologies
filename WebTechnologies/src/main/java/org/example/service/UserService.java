package org.example.service;

import org.example.entity.UserEntity;
import org.example.helper.User;
import org.example.helper.UserList;
import org.example.mapper.UserMapper;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserMapper mapper;

    public User create(final User user) {
        final UserEntity entity = mapper.toEntity(user);
        repository.save(entity);

        return mapper.toResponse(entity);
    }

    public List<User> createManyUsers(final UserList users) {
        final List<User> userList = new ArrayList<>();

        for (User user : users.getUsers()) {
            final UserEntity entity = mapper.toEntity(user);
            repository.save(entity);
            userList.add(mapper.toResponse(entity));
        }

        return userList;
    }

}
