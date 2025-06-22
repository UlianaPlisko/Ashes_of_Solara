package com.example.mygame.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Character {
    private int id;
    private int userId;
    private String name;
    private int maxHealth;
    private int health;
    private int maxSanity;
    private int sanity;
    private int maxHunger;
    private int hunger;
    private int x;
    private int y;
}
