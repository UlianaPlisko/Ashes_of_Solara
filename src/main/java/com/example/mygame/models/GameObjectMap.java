package com.example.mygame.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GameObjectMap {
    private long id;
    private long gameObjectId;
    private long x;
    private long y;
}