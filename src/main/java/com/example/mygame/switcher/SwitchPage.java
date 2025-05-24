package com.example.mygame.switcher;

import javafx.scene.layout.BorderPane;

public class SwitchPage implements SwitchPageInterface{

    @Override
    public void goMainMenu(BorderPane anchorPane) {
        new SceneSwitchComponent(anchorPane, "/com/example/mygame/hello-view.fxml");
    }

    @Override
    public void goLogin(BorderPane anchorPane) {

    }

    @Override
    public void goRegister(BorderPane anchorPane) {

    }
}
