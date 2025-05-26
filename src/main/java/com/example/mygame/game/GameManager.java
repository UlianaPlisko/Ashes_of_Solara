package com.example.mygame.game;

import com.example.mygame.utils.GameLoop;
import com.example.mygame.utils.GameThread;
import com.example.mygame.utils.InternetMonitor;
import lombok.Setter;

public class GameManager {
    private static GameLoop gameLoop;
    private static GameThread gameThread;
    private static InternetMonitor internetMonitor;

    public static void setGameLoop(GameLoop loop) {
        gameLoop = loop;
    }

    public static void setGameThread(GameThread thread) {
        gameThread = thread;
    }

    public static void setInternetMonitor(InternetMonitor monitor) {
        internetMonitor = monitor;
    }

    public static void stopAll() {
        if (gameThread != null) gameThread.stopGame();
        if (internetMonitor != null) internetMonitor.stop();
        if (gameLoop != null) gameLoop.stop();
    }
}
