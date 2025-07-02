package com.example.mygame.game.player;

import com.example.mygame.game.Objects.GameObjectAbstract;
import com.example.mygame.game.Objects.SolidObject;
import com.example.mygame.game.Renderable;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

public class Player implements Renderable {

    @Getter
    private double x;
    @Getter
    private double y;
    private Double targetX = null;
    private Double targetY = null;
    private final double speed = 5.0;
    private double mapWidth;
    private double mapHeight;
    @Getter
    private Image currentImage; // The image to display for the player
    @Getter
    private Direction currentDirection;
    private final PlayerAnimator animator;

    private Image imageUp;
    private Image imageDown;
    private Image imageLeft;
    private Image imageRight;

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    public Player(double x, double y, double mapWidth, double mapHeight) {
        this.x = x;
        this.y = y;
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.currentDirection = Direction.DOWN;

        this.animator = new PlayerAnimator();

        loadPlayerImages();
        updatePlayerImage();
    }

    private void loadPlayerImages() {
        try {
            imageUp = new Image(Objects.requireNonNull(getClass().getResourceAsStream(PlayerConstants.WILSON_BACK)));
            imageDown = new Image(Objects.requireNonNull(getClass().getResourceAsStream(PlayerConstants.WILSON_FRONT)));
            imageLeft = new Image(Objects.requireNonNull(getClass().getResourceAsStream(PlayerConstants.WILSON_LEFT)));
            imageRight = new Image(Objects.requireNonNull(getClass().getResourceAsStream(PlayerConstants.WILSON_RIGHT)));
        } catch (Exception e) {
            System.err.println("Error loading player images: " + e.getMessage());

        }
    }

    private void updatePlayerImage() {
        switch (currentDirection) {
            case UP:
                currentImage = imageUp;
                break;
            case DOWN:
                currentImage = imageDown;
                break;
            case LEFT:
                currentImage = imageLeft;
                break;
            case RIGHT:
                currentImage = imageRight;
                break;
        }
    }

    public void moveLeft(List<GameObjectAbstract> gameObjects) {
        double newX = x - 10;
        setTarget(newX, y);
    }

    public void moveRight(List<GameObjectAbstract> gameObjects) {
        double newX = x + 10;
        setTarget(newX, y);
    }

    public void moveUp(List<GameObjectAbstract> gameObjects) {
        double newY = y - 10;
        setTarget(x, newY);
    }

    public void moveDown(List<GameObjectAbstract> gameObjects) {
        double newY = y + 10;
        setTarget(x, newY);
    }

    public void setTarget(double targetX, double targetY) {
        this.targetX = targetX;
        this.targetY = targetY;
    }

    public void update(List<GameObjectAbstract> gameObjects) {
        if (targetX == null || targetY == null) return;

        double dx = targetX - x;
        double dy = targetY - y;

        double distance = Math.sqrt(dx * dx + dy * dy);
        if (distance < speed) {
            x = targetX;
            y = targetY;
            targetX = null;
            targetY = null;
            currentImage = animator.getCurrentImage(currentDirection, false); // ← Add this line
            return;
        }

        double stepX = (dx / distance) * speed;
        double stepY = (dy / distance) * speed;

        double newX = x + stepX;
        double newY = y + stepY;

        if (Math.abs(dx) > Math.abs(dy)) {
            currentDirection = dx > 0 ? Direction.RIGHT : Direction.LEFT;
        } else {
            currentDirection = dy > 0 ? Direction.DOWN : Direction.UP;
        }

        if (canMoveTo(newX, newY, gameObjects)) {
            x = newX;
            y = newY;
            currentImage = animator.getCurrentImage(currentDirection, true);
            clampToMap();
        } else {
            // Stop moving if collision encountered
            targetX = null;
            targetY = null;
            currentImage = animator.getCurrentImage(currentDirection, false);
        }
    }

    // Ensure player stays within map boundaries
    private void clampToMap() {
        if (x < 0) x = 0;
        if (x > mapWidth) x = mapWidth;
        if (y < 0) y = 0;
        if (y > mapHeight) y = mapHeight;
    }

    public void render(GraphicsContext gc, double cameraX, double cameraY) {
        gc.drawImage(currentImage, x - cameraX, y - cameraY, PlayerConstants.PLAYER_WIDTH, PlayerConstants.PLAYER_HEIGHT);
    }

    public Rectangle2D getBoundsAt(double newX, double newY) {
        double footHeight = 10;  // how tall the collision box is
        double footWidth = PlayerConstants.PLAYER_WIDTH * 0.5; // optional narrower width

        double footX = newX + (PlayerConstants.PLAYER_WIDTH - footWidth) / 2.0;
        double footY = newY + PlayerConstants.PLAYER_HEIGHT - footHeight;

        return new Rectangle2D(footX, footY, footWidth, footHeight);
    }

    private boolean canMoveTo(double newX, double newY, List<GameObjectAbstract> gameObjects) {
        Rectangle2D futureBounds = getBoundsAt(newX, newY);

        for (GameObjectAbstract object : gameObjects) {
            if (object instanceof SolidObject solid) {
                if (solid.getBounds().intersects(futureBounds)) {
                    return false; // Blocked
                }
            }
        }
        return true; // No collisions
    }
}
