package com.example.mygame.game.Inventory;

import com.example.mygame.models.Resource;
import lombok.Getter;

public class ResourceInventoryItem implements InventoryItem {
    private final Resource resource;
    @Getter
    private final int quantity;

    public ResourceInventoryItem(Resource resource, int quantity) {
        this.resource = resource;
        this.quantity = quantity;
    }

    @Override
    public int getId(){
        return resource.getId();
    }

    @Override
    public String getName(){
        return resource.getName();
    }
}