package com.example.mygame.utils;

import com.example.mygame.HelloApplication;
import com.example.mygame.switcher.SwitchPageInterface;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class InternetMonitor {
    private final SwitchPageInterface pageSwitcher;
    private final GameThread gameThread;
    private final long checkIntervalMs;
    private volatile boolean isEnd = false;
    private volatile boolean running = true;

    private final HttpClient httpClient;

    public InternetMonitor(SwitchPageInterface pageSwitcher, GameThread gameThread, long checkIntervalMs) {
        this.pageSwitcher = pageSwitcher;
        this.gameThread = gameThread;
        this.checkIntervalMs = checkIntervalMs;
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(2))
                .build();
    }

    public void start() {
        new Thread(() -> {
            while (!isEnd) {
                boolean connected = hasInternetConnection();
                if (!connected && running) {
                    Platform.runLater(() -> {
                        // Stop game logic
                        if (gameThread != null) {
                            gameThread.stopGame();
                        }

                    });
                    running = false;
                } else if (connected) {
                    running = true; // Reset to allow future popups if connection is lost again
                }

                try {
                    Thread.sleep(checkIntervalMs);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }, "Internet-Monitor-Thread").start();
    }

    public void stop() {
        isEnd = true;
    }

    private boolean hasInternetConnection() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://www.google.com"))
                    .timeout(Duration.ofSeconds(4))
                    .GET()
                    .build();
            HttpResponse<Void> response = httpClient.send(request, HttpResponse.BodyHandlers.discarding());
            return response.statusCode() == 200;
        } catch (Exception e) {
            return false;
        }
    }
}