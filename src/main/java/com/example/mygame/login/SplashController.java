package com.example.mygame.login;

import javafx.fxml.FXML;
import com.example.mygame.utils.switcher.SwitchPage;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.util.Objects;

public class SplashController {

    @FXML
    private BorderPane splashPage;
    @FXML
    private ImageView backgroundImage;
    @FXML
    private Button startButton;

    @FXML
    public void initialize() {
        backgroundImage.setImage(new Image(Objects.requireNonNull(
                getClass().getResourceAsStream("/com/example/mygame/img/ui/splash.jpg")
        )));

        backgroundImage.setPreserveRatio(false);

        backgroundImage.fitWidthProperty().bind(splashPage.widthProperty());
        backgroundImage.fitHeightProperty().bind(splashPage.heightProperty());

        startButton.setOnAction(e -> {
            SwitchPage switchPage = new SwitchPage();
            switchPage.goLogin(splashPage);
        });
    }
}