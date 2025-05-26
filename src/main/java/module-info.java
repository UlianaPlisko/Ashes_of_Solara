module com.example.mygame {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires static lombok;

    opens com.example.mygame.login to javafx.fxml;
    opens com.example.mygame.game to javafx.fxml;
    opens com.example.mygame to javafx.fxml;
    exports com.example.mygame;
    exports com.example.mygame.login;
    exports com.example.mygame.game;
}