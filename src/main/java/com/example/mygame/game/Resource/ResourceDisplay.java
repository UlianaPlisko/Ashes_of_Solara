package com.example.mygame.game.Resource;

import javafx.scene.image.Image;
import lombok.Getter;

@Getter
public class ResourceDisplay {
    private Image image;
    private int quantity;
    private int inventorySlot; // e.g. 0 = top-left, 1 = next cell, etc.

    public ResourceDisplay(Image image, int quantity, int inventorySlot) {
        this.image = image;
        this.quantity = quantity;
        this.inventorySlot = inventorySlot;
    }
}
