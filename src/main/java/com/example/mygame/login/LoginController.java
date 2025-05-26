package com.example.mygame.login;

import com.example.mygame.game.GameController;
import com.example.mygame.game.GameManager;
import com.example.mygame.utils.switcher.SwitchPage;
import com.example.mygame.utils.switcher.SwitchPageInterface;
import com.example.mygame.game.GameLoop;
import com.example.mygame.game.GameThread;
import com.example.mygame.utils.InternetMonitor;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class LoginController {

    @FXML
    BorderPane loginPage;
    @FXML
    Label loginText;
    @FXML
    Button toMainPage;
    @FXML
    Button toGamePage;


    private SwitchPageInterface pageSwitch;


    @FXML
    public void initialize() {
        pageSwitch = new SwitchPage();
    }


    @FXML
    protected void onMainPageClick() {
        if (pageSwitch != null && loginPage != null) {
            pageSwitch.goMainMenu(loginPage);
        }
    }

    @FXML
    protected void onGamePageClick() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/mygame/pages/game-view.fxml"));
        try {
            Parent root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
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
    }
}
