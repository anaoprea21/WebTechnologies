package org.example.service;

import org.example.entity.UserEntity;
import org.example.helper.User;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public User create(final User user) {

        UserEntity customer = new UserEntity();

        customer.setEmail(user.getEmail());
        customer.setPassword(user.getPassword());
        customer.setUsername(user.getUsername());

        customer.setRole(user.getRole());
        customer.setPseudonym(user.getPseudonym());
        repository.save(customer);

        return user;
    }

}
