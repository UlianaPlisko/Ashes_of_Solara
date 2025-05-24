package com.example.mygame.switcher;

import javafx.scene.layout.BorderPane;

public interface SwitchPageInterface {

    void goMainMenu(BorderPane anchorPane);

    void goLogin(BorderPane anchorPane);

    void goRegister(BorderPane anchorPane);
}
