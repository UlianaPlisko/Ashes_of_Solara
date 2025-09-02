package com.example.mygame.game;

import com.example.mygame.game.Inventory.InventoryService;
import com.example.mygame.game.Objects.Bush.Bush;
import com.example.mygame.game.Objects.GameObjectAbstract;
import com.example.mygame.game.Objects.ObjectService;
import com.example.mygame.game.Objects.Tree.Tree;
import com.example.mygame.game.Resource.ResourceDisplay;
import com.example.mygame.game.player.Player;
import com.example.mygame.game.player.PlayerConstants;
import com.example.mygame.utils.InternetMonitor;
import com.example.mygame.utils.camera.Camera;
import com.example.mygame.utils.switcher.SwitchPage;
import com.example.mygame.utils.switcher.SwitchPageInterface;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
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

    private HBox inventoryOverlay = new HBox();

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
    private List<GameObjectAbstract> gameObjects = new ArrayList<>();
    private ObjectService objectService;
    private SwitchPageInterface pageSwitch;
    private ImageView inventoryView;
    private Pane inventoryItemsLayer = new Pane();

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

        objectService = new ObjectService();
        gameObjects = objectService.getAllGameObjects();

        if (gameCanvas == null) {
            System.err.println("Canvas is null!");
            return;
        }
        gc = gameCanvas.getGraphicsContext2D();
        try {
            // Load map image
            URL mapResource = getClass().getResource("/com/example/mygame/img/dessert_test.png");
            if (mapResource == null) {
                System.err.println("Map image resource not found!");
                return;
            }
            mapImage = new Image(mapResource.toExternalForm());

            // Load inventory image
            URL inventoryResource = getClass().getResource("/com/example/mygame/img/inventory_empty.png");
            if (inventoryResource == null) {
                System.err.println("Inventory image resource not found!");
                return;
            }
            Image inventoryImage = new Image(inventoryResource.toExternalForm());

            inventoryView = new ImageView(inventoryImage);
            inventoryView.setPreserveRatio(true);
            inventoryView.setFitWidth(800);

            StackPane.setAlignment(inventoryView, Pos.BOTTOM_CENTER);
            StackPane.setMargin(inventoryView, new Insets(10));
            canvasContainer.getChildren().add(inventoryView);

            inventoryItemsLayer.setPickOnBounds(false);        // clicks pass through if you want
            inventoryItemsLayer.setManaged(false);             // we’ll control its children by relocate()
            StackPane.setAlignment(inventoryItemsLayer, Pos.TOP_LEFT); // (0,0) = top-left of canvasContainer
            canvasContainer.getChildren().add(inventoryItemsLayer);

            inventoryView.boundsInParentProperty().addListener((obs, oldB, newB) -> layoutInventoryItems());
            canvasContainer.widthProperty().addListener((o, ov, nv) -> layoutInventoryItems());
            canvasContainer.heightProperty().addListener((o, ov, nv) -> layoutInventoryItems());

        } catch (Exception e) {
            System.err.println("Failed to load image: " + e.getMessage());
            e.printStackTrace();
        }

        inventoryOverlay = new HBox();
        inventoryOverlay.setAlignment(Pos.BOTTOM_CENTER);
        inventoryOverlay.setSpacing(10);
        StackPane.setAlignment(inventoryOverlay, Pos.BOTTOM_CENTER); // same as inventory image
        StackPane.setMargin(inventoryOverlay, new Insets(40, 0, 20, 0));

        canvasContainer.getChildren().add(inventoryOverlay);

        pageSwitch = new SwitchPage();

        player = new Player(mapImage.getWidth() / 2, mapImage.getHeight() / 2, mapImage.getWidth(), mapImage.getHeight());

        displayInventory(1);

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
                        case SPACE:
                            for (GameObjectAbstract obj : gameObjects) {
                                if (!obj.isPicked() && obj.isPlayerNear(player.getX(), player.getY())) {
                                    obj.interact(player);
                                    displayInventory(1);
                                    break;
                                }
                            }
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
        for (GameObjectAbstract object : gameObjects) {
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

    private void displayInventory(int characterId) {
        InventoryService inventoryService = new InventoryService();
        List<ResourceDisplay> inventoryItems = inventoryService.getInventoryDisplayItems(characterId);

        inventoryItemsLayer.getChildren().clear();

        for (ResourceDisplay item : inventoryItems) {
            ImageView itemImage = new ImageView(item.getImage());
            itemImage.setFitWidth(30);
            itemImage.setPreserveRatio(true);

            String qtyText = String.valueOf(item.getQuantity());
            Label quantityLabel = new Label(qtyText);
            quantityLabel.setStyle("-fx-text-fill: white; -fx-font-size: 12px; -fx-background-color: rgba(0,0,0,0.6);");

            int charCount = qtyText.length();
            double width = charCount * 7;

            quantityLabel.setPrefWidth(width);
            quantityLabel.setMinWidth(width);
            quantityLabel.setMaxWidth(width);

            quantityLabel.setTextOverrun(OverrunStyle.CLIP);
            quantityLabel.setAlignment(Pos.CENTER_RIGHT);

            StackPane itemNode = new StackPane(itemImage, quantityLabel);
            StackPane.setAlignment(quantityLabel, Pos.BOTTOM_RIGHT);
            StackPane.setMargin(quantityLabel, new Insets(0, 3, 3, 0));
            itemNode.setManaged(false);

            itemNode.setPrefSize(50, 50);
            itemNode.setMinSize(50, 50);
            itemNode.setMaxSize(50, 50);

            inventoryItemsLayer.getChildren().add(itemNode);
        }

        layoutInventoryItems();
    }

    private void layoutInventoryItems() {
        if (inventoryView == null || inventoryItemsLayer.getChildren().isEmpty()) return;

        Bounds b = inventoryView.getBoundsInParent();

        double paddingLeft  = 35;
        double paddingBottom = -25;
        double shiftX = 35;

        double itemH = 50;

        double startX = b.getMinX() + paddingLeft;
        double baseY  = b.getMaxY() - paddingBottom - itemH;

        int i = 0;
        for (javafx.scene.Node node : inventoryItemsLayer.getChildren()) {
            double x = startX + i * shiftX;
            double y = baseY;
            node.relocate(x, y);
            i++;
        }
    }
}
