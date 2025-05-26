package com.example.mygame;

import com.example.mygame.game.GameManager;
import com.example.mygame.login.LoginController;
import com.example.mygame.switcher.SwitchPage;
import com.example.mygame.utils.GameLoop;
import com.example.mygame.utils.GameThread;
import com.example.mygame.utils.InternetMonitor;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;

import java.io.IOException;

public class HelloApplication extends Application {
    @Getter
    private static Stage primaryStage;
    private static LoginController controller;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/example/mygame/pages/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        controller = fxmlLoader.getController();
        stage.setTitle("My Game");
        stage.setFullScreen(true);

        stage.setMinWidth(800);
        stage.setMinHeight(600);

        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(event -> {
            GameManager.stopAll();
        });
    }


    public static void main(String[] args) {
        launch();
    }
}