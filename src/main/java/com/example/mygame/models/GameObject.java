package com.example.mygame.models;

import com.example.mygame.game.Objects.GameObjectAbstract;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class GameObject {
    private int id;
    private String Name;
    private String Type;
    private int x;
    private int y;
}
