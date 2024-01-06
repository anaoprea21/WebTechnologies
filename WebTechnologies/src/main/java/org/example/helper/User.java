package org.example.helper;

import lombok.Data;

import java.util.UUID;
@Data
public class User {


    private String username;
    private String email;
    private String password;
    private UserRole role;
    private String pseudonym;


}
