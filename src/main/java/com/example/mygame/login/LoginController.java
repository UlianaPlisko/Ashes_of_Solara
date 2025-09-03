package com.example.mygame.login;

import com.example.mygame.game.GameController;
import com.example.mygame.game.player.PlayerService;
import com.example.mygame.game.player.Session;
import com.example.mygame.models.User;
import com.example.mygame.utils.switcher.SwitchPage;
import com.example.mygame.utils.switcher.SwitchPageInterface;

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
                showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password.");
                return;
            }

            PlayerService characterService = new PlayerService();
            com.example.mygame.models.Character character =
                    characterService.getCharacterForUser(userService.getUserId(user));

            Session.setCurrentCharacter(character);

            if (pageSwitch != null && loginPage != null) {
                pageSwitch.goGame(loginPage);
            }


        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Unexpected error.");
            e.printStackTrace();
        }
    }

    @FXML
    protected void onSignupClick() {
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
