package com.example.mygame.game;

import javafx.animation.AnimationTimer;

public class GameLoop extends AnimationTimer {

    private final GameController controller;

    public GameLoop(GameController controller) {
        this.controller = controller;
    }


    @Override
    public void handle(long now) {
        controller.update();  // Game logic (move player, etc.)
        controller.render();  // Drawing
    }

    private void update() {
        // Update game state, input, physics, etc.
    }

    private void render() {
        // Call rendering system to draw current frame
    }
}
