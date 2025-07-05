package com.example.mygame.game.Objects.Bush;

import com.example.mygame.game.Objects.GameObjectAbstract;
import com.example.mygame.game.Objects.GameObjectConstants;
import com.example.mygame.game.Objects.Tree.Tree;
import javafx.scene.image.Image;

public class Bush extends GameObjectAbstract {
    private Image emptyImage;
    private boolean isPicked;

    public Bush(String name, double x, double y) {
        super(x, y,
                getImageForName(name),
                getWidthForName(name),
                getHeightForName(name));

        this.emptyImage = getImageAfterForName(name);
        this.isPicked = false;
    }

    @Override
    public void interact() {
        if (!isPicked) {
            isPicked = true;
            setImage(emptyImage);
        }
    }

    public boolean isPicked() {
        return isPicked;
    }

    public boolean isPlayerNear(double playerX, double playerY) {
        double dx = playerX - getX();
        double dy = playerY - getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance < 30; // you can adjust this range
    }
}
