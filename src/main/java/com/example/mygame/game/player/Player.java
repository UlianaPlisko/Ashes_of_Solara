package com.example.mygame.game.player;

import lombok.Getter;

public class Player {
    @Getter
    private double x;
    @Getter
    private double y;
    private double mapWidth;
    private double mapHeight;

    public Player(double x, double y, double mapWidth, double mapHeight) {
        this.x = x;
        this.y = y;
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
    }

    // Movement methods: These methods allow the player to move on the map by changing their x and y coordinates
    // Each method adjusts the player's position by 10 pixels in the specified direction
    public void moveLeft() {
        x -= 10;
        clampToMap();
    }

    public void moveRight() {
        x += 10;
        clampToMap();
    }

    public void moveUp() {
        y -= 10;
        clampToMap();
    }

    public void moveDown() {
        y += 10;
        clampToMap();
    }

    // Ensure player stays within map boundaries
    private void clampToMap() {
        if (x < 0) x = 0;
        if (x > mapWidth) x = mapWidth;
        if (y < 0) y = 0;
        if (y > mapHeight) y = mapHeight;
    }
}
