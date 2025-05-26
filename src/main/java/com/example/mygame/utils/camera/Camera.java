package com.example.mygame.utils.camera;

import lombok.Data;
import lombok.Setter;

/**
 * The Camera class is responsible for rendering a 15x15 viewport of the map,
 * centered around a specific point, typically the player's position.
 * It only shows a portion of the full map to simulate a camera view.
 */
@Data
public class Camera {

    private double x, y;
    @Setter
    private double width;
    @Setter
    private double height;

    public Camera(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public void centerOn(double targetX, double targetY) {
        this.x = targetX - width / 2;
        this.y = targetY - height / 2;
    }

    public void clampToMap(double mapWidth, double mapHeight) {
        x = Math.max(0, Math.min(x, mapWidth - width));
        y = Math.max(0, Math.min(y, mapHeight - height));
    }
}
