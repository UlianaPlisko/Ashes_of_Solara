package com.example.mygame.game;

public class GameThread extends Thread {

    private boolean running = true;

    @Override
    public void run() {
        final int FPS = 60;
        final long TIME_PER_FRAME = 1000000000 / FPS;

        while (running) {
            long start = System.nanoTime();

            updateGameLogic(); // Heavy logic: AI, physics, etc.

            long elapsed = System.nanoTime() - start;
            long sleepTime = TIME_PER_FRAME - elapsed;

            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime / 1_000_000, (int)(sleepTime % 1_000_000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void stopGame() {
        running = false;
    }

    private void updateGameLogic() {
        // Game logic goes here: NPC movement, events, etc.
    }
}
