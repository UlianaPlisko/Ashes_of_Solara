package com.example.mygame.login;

import com.example.mygame.game.GameController;
import com.example.mygame.game.GameManager;
import com.example.mygame.models.User;
import com.example.mygame.utils.switcher.SwitchPage;
import com.example.mygame.utils.switcher.SwitchPageInterface;
import com.example.mygame.game.GameLoop;
import com.example.mygame.game.GameThread;
import com.example.mygame.utils.InternetMonitor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class LoginController {

    @FXML
    BorderPane loginPage;
    @FXML
    Label loginText;
    @FXML
    Button toGamePage;
    @FXML
    TextField usernameField;
    @FXML
    PasswordField passwordField;
    @FXML
    Hyperlink signupLink;
    


    private SwitchPageInterface pageSwitch;
    private UserService userService;


    @FXML
    public void initialize() {
        pageSwitch = new SwitchPage();
        userService = new UserService();
    }


    @FXML
    protected void onMainPageClick() {
        if (pageSwitch != null && loginPage != null) {
            pageSwitch.goMainMenu(loginPage);
        }
    }

    @FXML
    protected void onGamePageClick() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        try {
            User user = userService.login(username, password);
            if (user == null) {
                System.out.println("Login Failed, Invalid username or password.");
                return;
            }

            // Proceed to game page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/mygame/pages/game-view.fxml"));
            Parent root;
            try {
                root = loader.load();
            } catch (IOException e) {
                throw new RuntimeException("Failed to load game page", e);
            }

            GameController gameController = loader.getController();

            GameLoop loop = new GameLoop(gameController);
            GameManager.setGameLoop(loop);
            loop.start();
            GameThread thread = new GameThread();
            GameManager.setGameThread(thread);
            thread.start();

            InternetMonitor monitor = new InternetMonitor(new SwitchPage(), thread, 5000);
            GameManager.setInternetMonitor(monitor);
            monitor.start();

            if (pageSwitch != null && loginPage != null) {
                pageSwitch.goGame(loginPage);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Input Error");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred during login.");
            e.printStackTrace();
        }
    }

    public void onSignupClick(ActionEvent event) {
        if (pageSwitch != null && loginPage != null) {
            pageSwitch.goRegister(loginPage);
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
