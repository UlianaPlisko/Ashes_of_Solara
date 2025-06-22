package com.example.mygame.game.Objects;

import com.example.mygame.game.Renderable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import lombok.Getter;

public abstract class GameObject implements Renderable {

    @Getter
    protected double x, y;
    protected Image image;
    @Getter
    protected double width, height;

    public GameObject(double x, double y, Image image, double width, double height ) {
        this.x = x;
        this.y = y;
        this.image = image;
        this.width = width;
        this.height = height;
    }

    public abstract void interact(); // override in subclasses

    public void render(GraphicsContext gc, double cameraX, double cameraY) {
        gc.drawImage(image, x - cameraX, y - cameraY, width, height);
    }

    public boolean intersects(double px, double py, double pw, double ph) {
        return px + pw > x && px < x + image.getWidth()
                && py + ph > y && py < y + image.getHeight();
    }

}
