package com.example.mygame;

import com.example.mygame.db.DatabaseWrapper;
import com.example.mygame.game.GameManager;
import com.example.mygame.login.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.Connection;

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

        connect_db();

        stage.setOnCloseRequest(event -> {
            GameManager.stopAll();
        });
    }


    public static void main(String[] args) {
        launch();
//        String hashedPassword = BCrypt.hashpw("123456789", BCrypt.gensalt());
//        System.out.println(hashedPassword);
    }

    private void connect_db() {
    try {
        Connection conn = DatabaseWrapper.getInstance().getConnection();
        if (conn != null) {
            System.out.println("✅ Database connection successful!");
        } else {
            System.out.println("❌ Failed to connect to database.");
        }
    } catch (Exception e) {
        System.err.println("❌ Database error: " + e.getMessage());
                e.printStackTrace();
    }
}
}