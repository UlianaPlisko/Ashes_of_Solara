package com.example.mygame.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Tool {
    private long id;
    private String name;
    private String description;
    private long categoryId;
    private Integer maxUses;
}