package com.example.mygame.game;

import com.example.mygame.game.player.Player;
import com.example.mygame.utils.camera.Camera;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.net.URL;

public class GameController {

    @FXML
    Label dayLabel;
    @FXML
    BorderPane gamePage;
    @FXML
    Canvas gameCanvas;
    @FXML
    Label healthLabel;
    @FXML
    StackPane canvasContainer;
    @FXML
    Label sanityLabel;
    @FXML
    Label hungerLabel;
    @FXML
    Button settingsButton;

    @FXML
    private VBox dialogOverlay;

    @FXML
    private Button viewPlayersButton;
    @FXML
    private Button togglePauseButton;
    @FXML
    private Button pauseServerButton;
    @FXML
    private Button serverCommandsButton;
    @FXML
    private Button settingsButtonDialog;
    @FXML
    private Button reportIssueButton;
    @FXML
    private Button disconnectButton;
    @FXML
    private Button closeButton;

    private GraphicsContext gc;
    private Image mapImage;
    private Camera camera;
    private Player player;

    private GameLoop gameLoop;

    public void initialize() {
        if (gameCanvas == null) {
            System.err.println("Canvas is null!");
            return;
        }
        gc = gameCanvas.getGraphicsContext2D();
        try {
            URL resource = getClass().getResource("/com/example/mygame/img/dessert_test.png");
            if (resource == null) {
                System.err.println("Image resource not found!");
                return;
            }
            mapImage = new Image(resource.toExternalForm());
        } catch (Exception e) {
            System.err.println("Failed to load image: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        player = new Player(mapImage.getWidth() / 2, mapImage.getHeight() / 2, mapImage.getWidth(), mapImage.getHeight());

        gameCanvas.widthProperty().bind(gamePage.widthProperty());
        gameCanvas.heightProperty().bind(gamePage.heightProperty());

        camera = new Camera(gameCanvas.getWidth(), gameCanvas.getHeight());
        camera.centerOn(player.getX(), player.getY());
        camera.clampToMap(mapImage.getWidth(), mapImage.getHeight());

        gameCanvas.widthProperty().addListener((obs, oldVal, newVal) -> {
            camera.setWidth(newVal.doubleValue());
            update();
            render();
        });
        gameCanvas.heightProperty().addListener((obs, oldVal, newVal) -> {
            camera.setHeight(newVal.doubleValue());
            update();
            render();
        });

        // Wait for the Canvas to be added to a Scene
        gameCanvas.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.setOnKeyPressed(event -> {
                    KeyCode code = event.getCode();
                    switch (code) {
                        case LEFT:
                        case A:
                            player.moveLeft();
                            break;
                        case RIGHT:
                        case D:
                            player.moveRight();
                            break;
                        case UP:
                        case W:
                            player.moveUp();
                            break;
                        case DOWN:
                        case S:
                            player.moveDown();
                            break;
                    }
                    update();
                    render();
                });
            }
        });

        // Ensure canvas is focusable to receive key events
        gameCanvas.setFocusTraversable(true);
        gameCanvas.requestFocus();

        render();

        settingsButton.setOnAction(event -> {
            dialogOverlay.setVisible(true);
            dialogOverlay.setManaged(true);
        });

        closeButton.setOnAction(event -> {
            dialogOverlay.setVisible(false);
            dialogOverlay.setManaged(false);
        });
    }

    public void update() {
        camera.centerOn(player.getX(), player.getY());
        camera.clampToMap(mapImage.getWidth(), mapImage.getHeight());
    }

    public void render() {
        if (gc == null || mapImage == null) {
            System.err.println("Cannot render: gc or mapImage is null");
            return;
        }
        gc.clearRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
        gc.save();
        gc.setGlobalAlpha(1.0);
        gc.setTransform(1, 0, 0, 1, 0, 0);
        gc.drawImage(
                mapImage,
                camera.getX(), camera.getY(), camera.getWidth(), camera.getHeight(),
                0, 0, gameCanvas.getWidth(), gameCanvas.getHeight()
        );

        double screenX = player.getX() - camera.getX();
        double screenY = player.getY() - camera.getY();

        gc.setFill(Color.RED);
        gc.fillOval(screenX - 10, screenY - 10, 20, 20);
        gc.restore();
    }
}
