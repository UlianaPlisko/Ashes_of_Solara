package com.example.mygame.game.Resource;

import javafx.scene.image.Image;
import lombok.Getter;

@Getter
public class ResourceDisplay {
    private Image image;
    private int quantity;
    private int inventorySlot;

    public ResourceDisplay(Image image, int quantity, int inventorySlot) {
        this.image = image;
        this.quantity = quantity;
        this.inventorySlot = inventorySlot;
    }
}
