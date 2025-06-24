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
    @Setter
    private double smoothing = 0.4;

    private boolean firstUpdate = true;

    public Camera(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public void setSize(double width, double height) {
        this.width = width;
        this.height = height;
        this.firstUpdate = true; // trigger instant recenter after resize
    }

    public void centerOn(double targetX, double targetY) {
        double targetCamX = targetX - width / 2;
        double targetCamY = targetY - height / 2;

        if (firstUpdate) {
            // Snap instantly on the first update
            this.x = targetCamX;
            this.y = targetCamY;
            firstUpdate = false;
        } else {
            // Interpolate smoothly afterward
            this.x += (targetCamX - this.x) * smoothing;
            this.y += (targetCamY - this.y) * smoothing;
        }
    }

    public void clampToMap(double mapWidth, double mapHeight) {
        x = Math.max(0, Math.min(x, mapWidth - width));
        y = Math.max(0, Math.min(y, mapHeight - height));
    }
}
