package com.example.mygame.game;

import com.example.mygame.game.Objects.Bush.Bush;
import com.example.mygame.game.Objects.GameObject;
import com.example.mygame.game.Objects.Tree.Tree;
import com.example.mygame.game.player.Player;
import com.example.mygame.game.player.PlayerConstants;
import com.example.mygame.utils.InternetMonitor;
import com.example.mygame.utils.camera.Camera;
import com.example.mygame.utils.switcher.SwitchPage;
import com.example.mygame.utils.switcher.SwitchPageInterface;
import javafx.application.Platform;
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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
    private List<GameObject> gameObjects = new ArrayList<>();
    private SwitchPageInterface pageSwitch;

    public void initialize() {

        GameLoop loop = new GameLoop(this);
        GameManager.setGameLoop(loop);
        loop.start();
        GameThread thread = new GameThread();
        GameManager.setGameThread(thread);
        thread.start();

        InternetMonitor monitor = new InternetMonitor(new SwitchPage(), thread, 5000);
        GameManager.setInternetMonitor(monitor);
        monitor.start();

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

        pageSwitch = new SwitchPage();

        player = new Player(mapImage.getWidth() / 2, mapImage.getHeight() / 2, mapImage.getWidth(), mapImage.getHeight());
        Tree tree1 = new Tree(player.getX() + 100, player.getY());  // Place near player
        Tree tree2 = new Tree(player.getX() - 200, player.getY() + 150);
        Bush bush1 = new Bush(player.getX() - 300, player.getY() + 150);

        gameObjects.add(tree1);
        gameObjects.add(tree2);
        gameObjects.add(bush1);

        gameCanvas.widthProperty().bind(gamePage.widthProperty());
        gameCanvas.heightProperty().bind(gamePage.heightProperty());

        camera = new Camera(gameCanvas.getWidth(), gameCanvas.getHeight());
        camera.centerOn(player.getX(), player.getY());
        camera.clampToMap(mapImage.getWidth(), mapImage.getHeight());
        Platform.runLater(() -> {
            double canvasWidth = gameCanvas.getWidth();
            double canvasHeight = gameCanvas.getHeight();

            camera = new Camera(canvasWidth, canvasHeight);
            camera.centerOn(player.getX(), player.getY());
            camera.clampToMap(mapImage.getWidth(), mapImage.getHeight());

            update();
            render(); // now that everything is properly initialized
        });

        gameCanvas.widthProperty().addListener((obs, oldVal, newVal) -> {
            camera.setSize(newVal.doubleValue(), gameCanvas.getHeight());
            update();
            render();
        });
        gameCanvas.heightProperty().addListener((obs, oldVal, newVal) -> {
            camera.setSize(gameCanvas.getWidth(), newVal.doubleValue());
            update();
            render();
        });

        gamePage.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.setOnKeyPressed(event -> {
                    KeyCode code = event.getCode();
                    switch (code) {
                        case LEFT:
                        case A:
                            player.moveLeft(gameObjects);
                            break;
                        case RIGHT:
                        case D:
                            player.moveRight(gameObjects);
                            break;
                        case UP:
                        case W:
                            player.moveUp(gameObjects);
                            break;
                        case DOWN:
                        case S:
                            player.moveDown(gameObjects);
                            break;
                    }
                });
            }
        });

        gamePage.setOnMouseClicked(event -> {
            double clickX = event.getX();
            double clickY = event.getY();

            double worldX = clickX + camera.getX();
            double worldY = clickY + camera.getY();

            player.setTarget(worldX, worldY);
        });

        gameCanvas.setFocusTraversable(true);
        gameCanvas.requestFocus();

//        render();

        settingsButton.setOnAction(event -> {
            dialogOverlay.setVisible(true);
            dialogOverlay.setManaged(true);
        });

        closeButton.setOnAction(event -> {
            dialogOverlay.setVisible(false);
            dialogOverlay.setManaged(false);
        });

        disconnectButton.setOnAction(event -> {
            if (pageSwitch != null && gamePage != null) {
                pageSwitch.goLogin(gamePage);
            }
        });
    }

    public void update() {
        camera.centerOn(player.getX(), player.getY());
        camera.clampToMap(mapImage.getWidth(), mapImage.getHeight());
        player.update(gameObjects);
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

        List<Renderable> renderables = new ArrayList<>();
        for (GameObject object : gameObjects) {
            renderables.add(new Renderable() {
                @Override
                public double getY() {
                    return object.getY() + object.getHeight(); // Use base y-coordinate
                }

                @Override
                public void render(GraphicsContext gc, double cameraX, double cameraY) {
                    object.render(gc, cameraX, cameraY);
                }
            });
        }
        // Add player as a renderable
        renderables.add(new Renderable() {
            @Override
            public double getY() {
                return player.getY() + PlayerConstants.PLAYER_HEIGHT; // Use base y-coordinate
            }

            @Override
            public void render(GraphicsContext gc, double cameraX, double cameraY) {
                if (player.getCurrentImage() != null) {
                    player.render(gc, cameraX, cameraY);
                } else {
                    System.err.println("Player image is null, drawing red dot at (" + (player.getX() - cameraX) + ", " + (player.getY() - cameraY) + ")");
                    gc.setFill(Color.RED);
                    gc.fillRect(player.getX() - cameraX, player.getY() - cameraY, 32, 32);
                }
            }
        });

        renderables.sort(Comparator.comparingDouble(Renderable::getY));

        // Render all objects in sorted order with debug output
        for (Renderable renderable : renderables) {
            renderable.render(gc, camera.getX(), camera.getY());
        }

        gc.restore();
    }
}
