package com.example.mygame.game.player;

import javafx.scene.image.Image;
import lombok.Getter;

import java.util.*;

public class PlayerAnimator {

    private final Map<Player.Direction, Image[]> animationFrames = new HashMap<>();
    private final Map<Player.Direction, Image> idleFrames = new HashMap<>();
    private final long frameDurationNs = 50_000_000; // 100ms
    private int frameIndex = 0;
    private long lastFrameTime = 0;

    public PlayerAnimator() {
        loadFrames();
    }

    private void loadFrames() {
        try {
            animationFrames.put(Player.Direction.DOWN, PlayerConstants.WILSON_MOVING_DOWN_FRAMES);

            idleFrames.put(Player.Direction.DOWN,
                    new Image(Objects.requireNonNull(getClass().getResourceAsStream(PlayerConstants.WILSON_FRONT))));

            animationFrames.put(Player.Direction.UP, PlayerConstants.WILSON_MOVING_UP_FRAMES);

            idleFrames.put(Player.Direction.UP,
                    new Image(Objects.requireNonNull(getClass().getResourceAsStream(PlayerConstants.WILSON_BACK))));

            animationFrames.put(Player.Direction.LEFT, PlayerConstants.WILSON_MOVING_LEFT_FRAMES);

            idleFrames.put(Player.Direction.LEFT,
                    new Image(Objects.requireNonNull(getClass().getResourceAsStream(PlayerConstants.WILSON_LEFT))));


            animationFrames.put(Player.Direction.RIGHT, PlayerConstants.WILSON_MOVING_RIGHT_FRAMES);

            idleFrames.put(Player.Direction.RIGHT,
                    new Image(Objects.requireNonNull(getClass().getResourceAsStream(PlayerConstants.WILSON_RIGHT))));

        } catch (Exception e) {
            System.err.println("Failed to load animation frames: " + e.getMessage());
        }
    }

    public Image getCurrentImage(Player.Direction direction, boolean isMoving) {
        if (!isMoving || !animationFrames.containsKey(direction)) {
            return idleFrames.get(direction);
        }

        long now = System.nanoTime();
        if (now - lastFrameTime >= frameDurationNs) {
            frameIndex = (frameIndex + 1) % animationFrames.get(direction).length;
            lastFrameTime = now;
        }

        return animationFrames.get(direction)[frameIndex];
    }
}