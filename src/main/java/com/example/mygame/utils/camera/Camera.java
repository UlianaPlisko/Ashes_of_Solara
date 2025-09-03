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

    private double zoom = 1.0;        // current zoom
    private double targetZoom = 1.0;  // desired zoom
    private double zoomSmoothing = 0.1; // how quickly to interpolate zoom

    private boolean firstUpdate = true;

    public Camera(double width, double height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Set the desired zoom level.
     * The actual zoom will smoothly interpolate towards this value.
     */
    public void setZoom(double zoom) {
        this.targetZoom = Math.max(0.7, Math.min(zoom, 1.2));
    }

    /**
     * Call once per frame to gradually update zoom towards target.
     */
    public void updateZoom() {
        zoom += (targetZoom - zoom) * zoomSmoothing;
    }

    public double getZoomedWidth() {
        return width / zoom;
    }

    public double getZoomedHeight() {
        return height / zoom;
    }

    public void centerOn(double targetX, double targetY) {
        double viewWidth = getZoomedWidth();
        double viewHeight = getZoomedHeight();

        double targetCamX = targetX - viewWidth / 2;
        double targetCamY = targetY - viewHeight / 2;

        if (firstUpdate) {
            this.x = targetCamX;
            this.y = targetCamY;
            firstUpdate = false;
        } else {
            this.x += (targetCamX - this.x) * smoothing;
            this.y += (targetCamY - this.y) * smoothing;
        }
    }

    public void clampToMap(double mapWidth, double mapHeight) {
        double viewWidth = getZoomedWidth();
        double viewHeight = getZoomedHeight();

        x = Math.max(0, Math.min(x, mapWidth - viewWidth));
        y = Math.max(0, Math.min(y, mapHeight - viewHeight));
    }

    public void setSize(double width, double height) {
        this.width = width;
        this.height = height;
        this.firstUpdate = true;
    }
}
