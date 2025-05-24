package com.example.mygame.switcher;

import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

import java.io.IOException;

public class SceneSwitchComponent {
    public SceneSwitchComponent(BorderPane curBorderPane, String fxml) {
        try {
            if (getClass().getResource(fxml) == null) {
                throw new IOException("FXML file not found: " + fxml);
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            BorderPane nextBorderPane = loader.load();

            Scene scene = curBorderPane.getScene();
            if (scene != null) {
                scene.setRoot(nextBorderPane);
                applyFadeIn(nextBorderPane);
            } else {
                System.err.println("No scene found for the current BorderPane");
            }
        } catch (IOException e) {
            System.err.println("Error switching to the page: " + fxml);
            e.printStackTrace();
        }
    }

    private void applyFadeIn(Parent root) {
        FadeTransition fade = new FadeTransition(Duration.millis(300), root);
        fade.setFromValue(0.0);
        fade.setToValue(1.0);
        fade.play();
    }
}