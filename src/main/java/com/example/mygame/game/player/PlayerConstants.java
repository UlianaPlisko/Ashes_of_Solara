package com.example.mygame.game.player;

import javafx.scene.image.Image;

import java.util.Objects;

public final class PlayerConstants {

    public static final String WILSON_FRONT = "/com/example/mygame/img/wilson/Wilson_front.png";
    public static final String WILSON_BACK = "/com/example/mygame/img/wilson/Wilson_back.png";
    public static final String WILSON_LEFT = "/com/example/mygame/img/wilson/Wilson_left.png";
    public static final String WILSON_RIGHT = "/com/example/mygame/img/wilson/Wilson_right.png";

    public static final Image[] WILSON_MOVING_UP_FRAMES = {
            new Image(Objects.requireNonNull(PlayerConstants.class.getResourceAsStream("/com/example/mygame/img/wilson/Wilson_back.png"))),
            new Image(Objects.requireNonNull(PlayerConstants.class.getResourceAsStream("/com/example/mygame/img/wilson/Wilson_back1.png"))),
            new Image(Objects.requireNonNull(PlayerConstants.class.getResourceAsStream("/com/example/mygame/img/wilson/Wilson_back2.png"))),
            new Image(Objects.requireNonNull(PlayerConstants.class.getResourceAsStream("/com/example/mygame/img/wilson/Wilson_back1.png"))),
            new Image(Objects.requireNonNull(PlayerConstants.class.getResourceAsStream("/com/example/mygame/img/wilson/Wilson_back.png"))),
            new Image(Objects.requireNonNull(PlayerConstants.class.getResourceAsStream("/com/example/mygame/img/wilson/Wilson_back3.png"))),
            new Image(Objects.requireNonNull(PlayerConstants.class.getResourceAsStream("/com/example/mygame/img/wilson/Wilson_back4.png"))),
            new Image(Objects.requireNonNull(PlayerConstants.class.getResourceAsStream("/com/example/mygame/img/wilson/Wilson_back3.png")))
    };

    public static final Image[] WILSON_MOVING_DOWN_FRAMES = {
            new Image(Objects.requireNonNull(PlayerConstants.class.getResourceAsStream("/com/example/mygame/img/wilson/Wilson_front.png"))),
            new Image(Objects.requireNonNull(PlayerConstants.class.getResourceAsStream("/com/example/mygame/img/wilson/Wilson_front1.png"))),
            new Image(Objects.requireNonNull(PlayerConstants.class.getResourceAsStream("/com/example/mygame/img/wilson/Wilson_front2.png"))),
            new Image(Objects.requireNonNull(PlayerConstants.class.getResourceAsStream("/com/example/mygame/img/wilson/Wilson_front1.png"))),
            new Image(Objects.requireNonNull(PlayerConstants.class.getResourceAsStream("/com/example/mygame/img/wilson/Wilson_front.png"))),
            new Image(Objects.requireNonNull(PlayerConstants.class.getResourceAsStream("/com/example/mygame/img/wilson/Wilson_front3.png"))),
            new Image(Objects.requireNonNull(PlayerConstants.class.getResourceAsStream("/com/example/mygame/img/wilson/Wilson_front4.png"))),
            new Image(Objects.requireNonNull(PlayerConstants.class.getResourceAsStream("/com/example/mygame/img/wilson/Wilson_front3.png")))
    };

    public static final Image[] WILSON_MOVING_LEFT_FRAMES = {
            new Image(Objects.requireNonNull(PlayerConstants.class.getResourceAsStream("/com/example/mygame/img/wilson/Wilson_left.png"))),
            new Image(Objects.requireNonNull(PlayerConstants.class.getResourceAsStream("/com/example/mygame/img/wilson/Wilson_left1.png"))),
            new Image(Objects.requireNonNull(PlayerConstants.class.getResourceAsStream("/com/example/mygame/img/wilson/Wilson_left2.png"))),
            new Image(Objects.requireNonNull(PlayerConstants.class.getResourceAsStream("/com/example/mygame/img/wilson/Wilson_left1.png"))),
            new Image(Objects.requireNonNull(PlayerConstants.class.getResourceAsStream("/com/example/mygame/img/wilson/Wilson_left.png"))),
            new Image(Objects.requireNonNull(PlayerConstants.class.getResourceAsStream("/com/example/mygame/img/wilson/Wilson_left3.png"))),
            new Image(Objects.requireNonNull(PlayerConstants.class.getResourceAsStream("/com/example/mygame/img/wilson/Wilson_left4.png"))),
            new Image(Objects.requireNonNull(PlayerConstants.class.getResourceAsStream("/com/example/mygame/img/wilson/Wilson_left3.png")))
    };
    public static final Image[] WILSON_MOVING_RIGHT_FRAMES = {
            new Image(Objects.requireNonNull(PlayerConstants.class.getResourceAsStream("/com/example/mygame/img/wilson/Wilson_right.png"))),
            new Image(Objects.requireNonNull(PlayerConstants.class.getResourceAsStream("/com/example/mygame/img/wilson/Wilson_right1.png"))),
            new Image(Objects.requireNonNull(PlayerConstants.class.getResourceAsStream("/com/example/mygame/img/wilson/Wilson_right2.png"))),
            new Image(Objects.requireNonNull(PlayerConstants.class.getResourceAsStream("/com/example/mygame/img/wilson/Wilson_right1.png"))),
            new Image(Objects.requireNonNull(PlayerConstants.class.getResourceAsStream("/com/example/mygame/img/wilson/Wilson_right.png"))),
            new Image(Objects.requireNonNull(PlayerConstants.class.getResourceAsStream("/com/example/mygame/img/wilson/Wilson_right3.png"))),
            new Image(Objects.requireNonNull(PlayerConstants.class.getResourceAsStream("/com/example/mygame/img/wilson/Wilson_right4.png"))),
            new Image(Objects.requireNonNull(PlayerConstants.class.getResourceAsStream("/com/example/mygame/img/wilson/Wilson_right3.png")))

    };

    public static final double PLAYER_WIDTH = 32.0 * 3; // Example width
    public static final double PLAYER_HEIGHT = 32.0 * 3; // Example height
    public static final double PLAYER_MOVE_SPEED = 10.0;

}
