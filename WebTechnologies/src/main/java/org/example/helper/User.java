package org.example.helper;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor

@Data
public class User {

    private String username;
    private String email;
    private String password;
    private UserRole role;
    private String pseudonym;


}
