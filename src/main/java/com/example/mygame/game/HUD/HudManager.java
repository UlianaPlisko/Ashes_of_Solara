package com.example.mygame.game.HUD;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class HudManager {

    private final HBox hudOverlay = new HBox(10);
    private final ImageView healthIcon = new ImageView();
    private final ImageView hungerIcon = new ImageView();
    private final ImageView sanityIcon = new ImageView();

    private final Image healthFull;
    private final Image healthLow;
    private final Image hungerFull;
    private final Image hungerLow;
    private final Image sanityFull;
    private final Image sanityLow;

    public HudManager() {
        healthFull = new Image(getClass().getResourceAsStream("/com/example/mygame/img/hud/Health_full.png"));
        healthLow = new Image(getClass().getResourceAsStream("/com/example/mygame/img/hud/Health_full.png"));
        hungerFull = new Image(getClass().getResourceAsStream("/com/example/mygame/img/hud/Hunger_full.png"));
        hungerLow = new Image(getClass().getResourceAsStream("/com/example/mygame/img/hud/Hunger_full.png"));
        sanityFull = new Image(getClass().getResourceAsStream("/com/example/mygame/img/hud/Sanity_full.png"));
        sanityLow = new Image(getClass().getResourceAsStream("/com/example/mygame/img/hud/Sanity_full.png"));

        hudOverlay.getChildren().addAll(healthIcon, hungerIcon, sanityIcon);
        hudOverlay.setAlignment(Pos.TOP_RIGHT);

        healthIcon.setFitWidth(40);
        healthIcon.setPreserveRatio(true);
        hungerIcon.setFitWidth(40);
        hungerIcon.setPreserveRatio(true);
        sanityIcon.setFitWidth(40);
        sanityIcon.setPreserveRatio(true);

        healthIcon.setImage(healthFull);
        hungerIcon.setImage(hungerFull);
        sanityIcon.setImage(sanityFull);
    }

    public void attachTo(StackPane root) {
        StackPane.setAlignment(hudOverlay, Pos.TOP_RIGHT);
        StackPane.setMargin(hudOverlay, new Insets(10, 20, 0, 0));
        root.getChildren().add(hudOverlay);
    }

    public void updateHUD(int health, int hunger, int sanity) {
        healthIcon.setImage(health < 30 ? healthLow : healthFull);
        hungerIcon.setImage(hunger < 30 ? hungerLow : hungerFull);
        sanityIcon.setImage(sanity < 30 ? sanityLow : sanityFull);
    }
}