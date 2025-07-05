package com.example.mygame.game.Objects;

import com.example.mygame.game.Renderable;
import com.example.mygame.game.player.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;

public abstract class GameObjectAbstract implements Renderable {

    @Getter
    protected double x, y;
    @Setter
    protected Image image;
    @Getter
    protected double width, height;
    @Setter
    @Getter
    protected boolean solid;

    public GameObjectAbstract(double x, double y, Image image, double width, double height ) {
        this.x = x;
        this.y = y;
        this.image = image;
        this.width = width;
        this.height = height;
    }

    public abstract void interact(Player player); // override in subclasses

    public void render(GraphicsContext gc, double cameraX, double cameraY) {
        gc.drawImage(image, x - cameraX, y - cameraY, width, height);
    }

    public boolean intersects(double px, double py, double pw, double ph) {
        return px + pw > x && px < x + image.getWidth()
                && py + ph > y && py < y + image.getHeight();
    }

    protected static Image getImageForName(String name) {
        try {
            Field field = GameObjectConstants.class.getField(name);
            String imageName = (String) field.get(null);
            return new Image(GameObjectAbstract.class.getResource(imageName).toExternalForm());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalArgumentException("Unknown image for name: " + name, e);
        }
    }

    // Get the width corresponding to the name by reflection
    protected static int getWidthForName(String name) {
        try {
            Field field = GameObjectConstants.class.getField(name + "_WIDTH");
            return ((Double) field.get(null)).intValue();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalArgumentException("Unknown width for name: " + name, e);
        }
    }

    // Get the height corresponding to the name by reflection
    protected static int getHeightForName(String name) {
        try {
            Field field = GameObjectConstants.class.getField(name + "_HEIGHT");
            return ((Double) field.get(null)).intValue();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalArgumentException("Unknown height for name: " + name, e);
        }
    }

    protected static Image getImageAfterForName(String name) {
        try {
            Field field = GameObjectConstants.class.getField(name + "_AFTER");
            String imageName = (String) field.get(null);
            return new Image(GameObjectAbstract.class.getResource(imageName).toExternalForm());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalArgumentException("Unknown image for name: " + name, e);
        }
    }
}
