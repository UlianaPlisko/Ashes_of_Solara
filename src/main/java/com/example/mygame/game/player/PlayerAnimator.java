package com.example.mygame.game.player;

import javafx.scene.image.Image;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class PlayerAnimator {

    @Getter
    private boolean isMoving; // True if player is currently moving
    private int animationFrameCounter; // Counter for overall animation timing
    @Getter
    private double jumpOffset; // Vertical offset for bobbing animation (used when moving)

    // Constants for animation speed (can be moved to a general GameConstants if preferred)
    private static final int ANIMATION_SPEED_MOVING = 5; // How fast the moving animation cycles (for jump offset and frame change)
    private static final int ANIMATION_SPEED_IDLE = 10; // How fast the idle animation cycles (frame change frequency)
    private static final double MAX_JUMP_OFFSET = 2.0; // Max vertical displacement for bobbing

    // References to the loaded Image assets (passed from Player)
    private Image imageUp;
    private Image imageDown;
    private Image imageLeft;
    private Image imageRight;

    private List<Image> idleAnimationUpFrames;
    private List<Image> idleAnimationDownFrames;
    private List<Image> idleAnimationLeftFrames;
    private List<Image> idleAnimationRightFrames;

    // NEW: Lists for specific moving animations for each direction
    private List<Image> movingAnimationUpFrames;
    private List<Image> movingAnimationDownFrames;
    private List<Image> movingAnimationLeftFrames;
    private List<Image> movingAnimationRightFrames;

    private int currentIdleFrameIndex; // Index for the current idle animation frame
    @Getter
    private Image currentImage; // The image to display for the player based on animation state

    /**
     * Constructor for PlayerAnimator.
     * Initializes animation state and stores references to all necessary image assets.
     * All image lists should be non-null (even if empty).
     */
    public PlayerAnimator(Image imageUp, Image imageDown, Image imageLeft, Image imageRight,
                          List<Image> idleAnimationUpFrames, List<Image> idleAnimationDownFrames,
                          List<Image> idleAnimationLeftFrames, List<Image> idleAnimationRightFrames,
                          List<Image> movingAnimationUpFrames, List<Image> movingAnimationDownFrames,
                          List<Image> movingAnimationLeftFrames, List<Image> movingAnimationRightFrames) {

        this.imageUp = imageUp;
        this.imageDown = imageDown;
        this.imageLeft = imageLeft;
        this.imageRight = imageRight;

        this.idleAnimationUpFrames = idleAnimationUpFrames != null ? idleAnimationUpFrames : new ArrayList<>();
        this.idleAnimationDownFrames = idleAnimationDownFrames != null ? idleAnimationDownFrames : new ArrayList<>();
        this.idleAnimationLeftFrames = idleAnimationLeftFrames != null ? idleAnimationLeftFrames : new ArrayList<>();
        this.idleAnimationRightFrames = idleAnimationRightFrames != null ? idleAnimationRightFrames : new ArrayList<>();

        this.movingAnimationUpFrames = movingAnimationUpFrames != null ? movingAnimationUpFrames : new ArrayList<>();
        this.movingAnimationDownFrames = movingAnimationDownFrames != null ? movingAnimationDownFrames : new ArrayList<>();
        this.movingAnimationLeftFrames = movingAnimationLeftFrames != null ? movingAnimationLeftFrames : new ArrayList<>();
        this.movingAnimationRightFrames = movingAnimationRightFrames != null ? movingAnimationRightFrames : new ArrayList<>();

        // Initialize animation state
        this.isMoving = false;
        this.animationFrameCounter = 0;
        this.jumpOffset = 0;
        this.currentIdleFrameIndex = 0;

        // Set default initial image to facing down idle
        if (!this.idleAnimationDownFrames.isEmpty()) {
            this.currentImage = this.idleAnimationDownFrames.get(0);
        } else {
            this.currentImage = this.imageDown; // Fallback to static image
        }
    }

    /**
     * Sets the moving state of the player.
     * When the player stops moving, animation counters and offsets are reset.
     *
     * @param moving True if the player is currently being moved by user input.
     */
    public void setMoving(boolean moving) {
        isMoving = moving;
        if (!isMoving) {
            animationFrameCounter = 0; // Reset global counter
            jumpOffset = 0; // Ensure no vertical offset when idle
            currentIdleFrameIndex = 0; // Reset idle animation frame to start from the beginning
        }
    }

    /**
     * Updates the player's animation state based on whether they are moving or idle.
     * This method should be called once per game tick.
     *
     * @param currentDirection The current direction the player is facing (UP, DOWN, LEFT, RIGHT).
     */
    public void updateAnimation(Player.Direction currentDirection) {
        animationFrameCounter++; // Increment global counter for timing

        if (isMoving) {
            // Calculate jump offset for general moving animation
            jumpOffset = Math.sin(animationFrameCounter / (double)ANIMATION_SPEED_MOVING) * MAX_JUMP_OFFSET;
            // For moving, frame cycling is directly within updateCurrentDisplayedImage based on direction
        } else { // Player is idle
            jumpOffset = 0; // No vertical bobbing when idle

            // Advance idle animation frame based on ANIMATION_SPEED_IDLE.
            // Even for static idle (1 frame), this logic helps reset currentIdleFrameIndex.
            if (animationFrameCounter % ANIMATION_SPEED_IDLE == 0) {
                currentIdleFrameIndex++;
            }
        }
        updateCurrentDisplayedImage(currentDirection); // Update the displayed image based on current state
    }

    /**
     * Selects and sets the 'currentImage' based on the player's state (moving or idle)
     * and their current direction.
     *
     * @param currentDirection The player's current facing direction.
     */
    private void updateCurrentDisplayedImage(Player.Direction currentDirection) {
        if (isMoving) {
            // When moving, check for specific moving animations first
            List<Image> framesToUse = null;
            Image fallbackImage = null; // Default to the static directional image

            switch (currentDirection) {
                case UP:
                    framesToUse = movingAnimationUpFrames;
                    fallbackImage = imageUp;
                    break;
                case DOWN:
                    framesToUse = movingAnimationDownFrames;
                    fallbackImage = imageDown;
                    break;
                case LEFT:
                    framesToUse = movingAnimationLeftFrames;
                    fallbackImage = imageLeft;
                    break;
                case RIGHT:
                    framesToUse = movingAnimationRightFrames;
                    fallbackImage = imageRight;
                    break;
            }

            // If specific moving animation frames exist, use them
            if (framesToUse != null && !framesToUse.isEmpty()) {
                currentImage = framesToUse.get(animationFrameCounter % framesToUse.size());
            } else {
                // Fallback to static directional image if no specific moving frames or list is empty
                currentImage = fallbackImage;
                if (currentImage == null) { // Double check for null in case image loading failed
                    System.err.println("Fallback image is null for direction: " + currentDirection + ". This should not happen if base images are loaded correctly.");
                }
            }
        } else { // Player is idle
            List<Image> framesToUse = null;
            Image fallbackImage = null;

            switch (currentDirection) {
                case UP:
                    framesToUse = idleAnimationUpFrames;
                    fallbackImage = imageUp;
                    break;
                case DOWN:
                    framesToUse = idleAnimationDownFrames;
                    fallbackImage = imageDown;
                    break;
                case LEFT:
                    framesToUse = idleAnimationLeftFrames;
                    fallbackImage = imageLeft;
                    break;
                case RIGHT:
                    framesToUse = idleAnimationRightFrames;
                    fallbackImage = imageRight;
                    break;
                default: // Should theoretically not happen with defined directions
                    framesToUse = idleAnimationDownFrames;
                    fallbackImage = imageDown;
            }

            // Cycle through the idle animation frames based on currentIdleFrameIndex.
            if (framesToUse != null && !framesToUse.isEmpty()) {
                currentImage = framesToUse.get(currentIdleFrameIndex % framesToUse.size());
            } else {
                System.err.println("No idle frames available for direction: " + currentDirection + ". Using static directional image.");
                // Fallback: If no idle frames loaded for this direction, use the static directional image
                currentImage = fallbackImage;
                if (currentImage == null) { // Double check for null in case image loading failed
                    System.err.println("Fallback idle image is null for direction: " + currentDirection + ". This should not happen if base images are loaded correctly.");
                }
            }
        }
    }
}
