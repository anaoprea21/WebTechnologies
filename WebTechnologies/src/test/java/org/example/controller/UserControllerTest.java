package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.helper.User;
import org.example.helper.UserList;
import org.example.helper.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private User user;
    private UserList userList;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("username");
        user.setEmail("johndoe@example.com");
        user.setPassword("password");
        user.setRole(UserRole.REPORTER);
        user.setPseudonym("pseudo");

        List<User> users = new ArrayList<>();
        users.add(user);
        users.add(new User("usernamed", "email@email.com", "password", UserRole.SEEKER, "no"));
        users.add(new User("username1d", "edasdail@email.com", "password", UserRole.REPORTER, "pseudo"));
        users.add(new User("username2d", "email@sds.com", "password", UserRole.REPORTER, "pseudo2"));
        users.add(new User("username3d", "asd@email.com", "password", UserRole.SEEKER, "no"));
        users.add(new User("username4d", "email@yahoo.com", "password", UserRole.SEEKER, "no"));

        userList = new UserList();
        userList.setUsers(users);
    }

    @Test
    void createCustomerTest() throws Exception {
        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("username"));
    }

    @Test
    void createManyCustomersTest() throws Exception {
        mockMvc.perform(post("/api/user/many")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userList)))
                .andExpect(status().isOk());
    }

}
