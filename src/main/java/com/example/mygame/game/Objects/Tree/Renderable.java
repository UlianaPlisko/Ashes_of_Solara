package com.example.mygame.game.Objects.Tree;

import javafx.scene.canvas.GraphicsContext;

public interface Renderable {
    double getY();
    void render(GraphicsContext gc, double cameraX, double cameraY);
}
