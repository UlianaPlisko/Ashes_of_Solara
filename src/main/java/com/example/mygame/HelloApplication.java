package com.example.mygame;

import com.example.mygame.switcher.SwitchPage;
import com.example.mygame.utils.GameLoop;
import com.example.mygame.utils.GameThread;
import com.example.mygame.utils.InternetMonitor;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private static Stage primaryStage;
    private static HelloController controller;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        controller = fxmlLoader.getController();
        stage.setTitle("My Game");
        stage.setFullScreen(true);
        stage.setScene(scene);
        stage.show();

        GameThread gameThread = new GameThread();
        // Start InternetMonitor
        InternetMonitor internetMonitor = new InternetMonitor(new SwitchPage(), gameThread, 5000);
        internetMonitor.start();

        // Start game loop
        GameLoop gameLoop = new GameLoop();
        gameLoop.start();

        // Start game logic thread

        gameThread.start();

        stage.setOnCloseRequest(event -> {
            gameThread.stopGame();
            internetMonitor.stop();
            gameLoop.stop();
        });
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static HelloController getController() {
        return controller;
    }

    public static void main(String[] args) {
        launch();
    }
}