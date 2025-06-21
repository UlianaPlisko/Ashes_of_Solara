package com.example.mygame.game.player;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import lombok.Getter;

import java.util.Objects;

public class Player {

    @Getter
    private double x;
    @Getter
    private double y;
    private double mapWidth;
    private double mapHeight;
    @Getter
    private Image currentImage; // The image to display for the player
    @Getter
    private Direction currentDirection;
    private PlayerAnimator animator;

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

    public void moveLeft() {
        x -= 10;
        currentDirection = Direction.LEFT;
        updatePlayerImage();
        clampToMap();
    }

    public void moveRight() {
        x += 10;
        currentDirection = Direction.RIGHT;
        updatePlayerImage();
        clampToMap();
    }

    public void moveUp() {
        y -= 10;
        currentDirection = Direction.UP;
        updatePlayerImage();
        clampToMap();
    }

    public void moveDown() {
        y += 10;
        currentDirection = Direction.DOWN;
        updatePlayerImage();
        clampToMap();
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
}
