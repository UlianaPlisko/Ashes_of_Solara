package com.example.mygame.models;

import com.example.mygame.game.Inventory.InventoryItem;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Resource{
    private int id;
    private String name;
}
