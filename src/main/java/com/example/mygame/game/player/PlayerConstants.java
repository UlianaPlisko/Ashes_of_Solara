package com.example.mygame.game.player;

public final class PlayerConstants {

    public static final String WILSON_FRONT = "/com/example/mygame/img/wilson/Wilson_front.png";
    public static final String WILSON_BACK = "/com/example/mygame/img/wilson/Wilson_back.png";
    public static final String WILSON_LEFT = "/com/example/mygame/img/wilson/Wilson_left.png";
    public static final String WILSON_RIGHT = "/com/example/mygame/img/wilson/Wilson_right.png";

    public static final String[] WILSON_IDLE_DOWN_FRAMES = {WILSON_FRONT};
    public static final String[] WILSON_IDLE_UP_FRAMES = {WILSON_BACK};
    public static final String[] WILSON_IDLE_LEFT_FRAMES = {WILSON_LEFT}; // Static idle for left
    public static final String[] WILSON_IDLE_RIGHT_FRAMES = {WILSON_RIGHT};

    public static final String[] WILSON_MOVING_UP_FRAMES = {
            "/com/example/mygame/img/wilson/Wilson_back1.png",
            "/com/example/mygame/img/wilson/Wilson_back2.png",
    };

    public static final String[] WILSON_MOVING_DOWN_FRAMES = {};
    public static final String[] WILSON_MOVING_LEFT_FRAMES = {};
    public static final String[] WILSON_MOVING_RIGHT_FRAMES = {};

    public static final double PLAYER_WIDTH = 32.0 * 3; // Example width
    public static final double PLAYER_HEIGHT = 32.0 * 3; // Example height
    public static final double PLAYER_MOVE_SPEED = 10.0;

}
