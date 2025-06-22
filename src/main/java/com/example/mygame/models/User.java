package com.example.mygame.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    private int id;
    private String username;
    private String email;
    private String password;
}
