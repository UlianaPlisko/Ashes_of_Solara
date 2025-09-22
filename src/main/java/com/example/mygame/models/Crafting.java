package com.example.mygame.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Crafting {
    private long toolId;
    private long resourceId;
    private long resourceQuantity;
}
