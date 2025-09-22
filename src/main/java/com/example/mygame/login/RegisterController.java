package com.example.mygame.login;

import com.example.mygame.game.player.PlayerService;
import com.example.mygame.game.player.Session;
import com.example.mygame.utils.exceptions.DataAccessException;
import com.example.mygame.models.User;
import com.example.mygame.utils.switcher.SwitchPage;
import com.example.mygame.utils.switcher.SwitchPageInterface;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

public class RegisterController {
    @FXML
    BorderPane registerPage;
    @FXML
    TextField usernameField;
    @FXML
    TextField emailField;
    @FXML
    PasswordField passwordField;
    @FXML
    PasswordField confirmPasswordField;
    @FXML
    Hyperlink loginLink;
    @FXML
    Button registerButton;

    private SwitchPageInterface pageSwitch;
    private UserService userService;

    @FXML
    public void initialize() {
        pageSwitch = new SwitchPage();
        userService = new UserService();
    }

    @FXML
    private void onLoginClick() {
        if (pageSwitch != null && registerPage != null) {
            pageSwitch.goLogin(registerPage);
        }
    }

    @FXML
    private void onRegisterClick() {
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        try {
            // Basic password confirmation check
            if (!password.equals(confirmPassword)) {
                showAlert(Alert.AlertType.ERROR, "Registration Failed", "Passwords do not match.");
                return;
            }

            User newUser = userService.register(username, email, password);
            showAlert(Alert.AlertType.INFORMATION, "Registration Successful", "Welcome, " + newUser.getUsername() + "!");

            PlayerService characterService = new PlayerService();
            com.example.mygame.models.Character character =
                    characterService.getCharacterForUser(userService.getUserId(newUser));

            Session.setCurrentCharacter(character);

            if (pageSwitch != null && registerPage != null) {
                pageSwitch.goGame(registerPage);
            }

            // Optionally, redirect to login or game page here
        } catch (IllegalArgumentException e) {
            showAlert(Alert.AlertType.WARNING, "Invalid Input", e.getMessage());
        } catch (DataAccessException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Could not access the database. Try again later.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Unexpected Error", "Something went wrong: " + e.getMessage());
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
