package com.example.mygame.utils.switcher;

import javafx.scene.layout.BorderPane;

public class SwitchPage implements SwitchPageInterface{

    @Override
    public void goMainMenu(BorderPane anchorPane) {
        new SceneSwitchComponent(anchorPane, "/com/example/mygame/pages/main-page.fxml");
    }

    @Override
    public void goGame(BorderPane anchorPane) {
        new SceneSwitchComponent(anchorPane, "/com/example/mygame/pages/game-view.fxml");
    }

    @Override
    public void goLogin(BorderPane anchorPane) {
        new SceneSwitchComponent(anchorPane, "/com/example/mygame/pages/login.fxml");
    }

    @Override
    public void goRegister(BorderPane anchorPane) {
        new SceneSwitchComponent(anchorPane, "/com/example/mygame/pages/register.fxml");
    }
}
