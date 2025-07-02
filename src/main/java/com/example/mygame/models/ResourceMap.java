package com.example.mygame.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResourceMap {
    private int id;
    private int gameObjectId;
    private int resourceId;
    private float probability;
}
