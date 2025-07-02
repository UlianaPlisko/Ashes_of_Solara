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
            // Example for DOWN direction
            animationFrames.put(Player.Direction.DOWN, PlayerConstants.WILSON_MOVING_DOWN_FRAMES);

            // Set idle (static) image for DOWN
            idleFrames.put(Player.Direction.DOWN,
                    new Image(Objects.requireNonNull(getClass().getResourceAsStream(PlayerConstants.WILSON_FRONT))));

            // Repeat for other directions
//            animationFrames.put(Player.Direction.UP, new Image[]{
//                    new Image(Objects.requireNonNull(getClass().getResourceAsStream(PlayerConstants.WILSON_BACK_1))),
//                    new Image(Objects.requireNonNull(getClass().getResourceAsStream(PlayerConstants.WILSON_BACK_2))),
//                    new Image(Objects.requireNonNull(getClass().getResourceAsStream(PlayerConstants.WILSON_BACK_3))),
//                    new Image(Objects.requireNonNull(getClass().getResourceAsStream(PlayerConstants.WILSON_BACK_4))),
//                    new Image(Objects.requireNonNull(getClass().getResourceAsStream(PlayerConstants.WILSON_BACK_5))),
//            });
//            idleFrames.put(Player.Direction.UP,
//                    new Image(Objects.requireNonNull(getClass().getResourceAsStream(PlayerConstants.WILSON_BACK))));
//
//            // LEFT
//            animationFrames.put(Player.Direction.LEFT, new Image[]{
//                    new Image(Objects.requireNonNull(getClass().getResourceAsStream(PlayerConstants.WILSON_LEFT_1))),
//                    new Image(Objects.requireNonNull(getClass().getResourceAsStream(PlayerConstants.WILSON_LEFT_2))),
//                    new Image(Objects.requireNonNull(getClass().getResourceAsStream(PlayerConstants.WILSON_LEFT_3))),
//                    new Image(Objects.requireNonNull(getClass().getResourceAsStream(PlayerConstants.WILSON_LEFT_4))),
//                    new Image(Objects.requireNonNull(getClass().getResourceAsStream(PlayerConstants.WILSON_LEFT_5))),
//            });
//            idleFrames.put(Player.Direction.LEFT,
//                    new Image(Objects.requireNonNull(getClass().getResourceAsStream(PlayerConstants.WILSON_LEFT))));
//
//            // RIGHT
//            animationFrames.put(Player.Direction.RIGHT, new Image[]{
//                    new Image(Objects.requireNonNull(getClass().getResourceAsStream(PlayerConstants.WILSON_RIGHT_1))),
//                    new Image(Objects.requireNonNull(getClass().getResourceAsStream(PlayerConstants.WILSON_RIGHT_2))),
//                    new Image(Objects.requireNonNull(getClass().getResourceAsStream(PlayerConstants.WILSON_RIGHT_3))),
//                    new Image(Objects.requireNonNull(getClass().getResourceAsStream(PlayerConstants.WILSON_RIGHT_4))),
//                    new Image(Objects.requireNonNull(getClass().getResourceAsStream(PlayerConstants.WILSON_RIGHT_5))),
//            });
//            idleFrames.put(Player.Direction.RIGHT,
//                    new Image(Objects.requireNonNull(getClass().getResourceAsStream(PlayerConstants.WILSON_RIGHT))));

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