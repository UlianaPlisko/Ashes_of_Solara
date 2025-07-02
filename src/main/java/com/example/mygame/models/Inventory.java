package com.example.mygame.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Inventory {
    private int id;
    private int characterId;
    private int resourceId;
    private int toolId;
    private int mealId;
    private int quantity;
}
