package com.example.mygame.utils.switcher;

import javafx.scene.layout.BorderPane;

public interface SwitchPageInterface {

    void goMainMenu(BorderPane anchorPane);

    void goGame(BorderPane anchorPane);

    void goLogin(BorderPane anchorPane);

    void goRegister(BorderPane anchorPane);
}
