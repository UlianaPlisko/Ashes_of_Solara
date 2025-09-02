package com.example.mygame.game.Objects.Simple;

import com.example.mygame.game.Objects.GameObjectAbstract;
import com.example.mygame.game.player.Player;
import javafx.scene.image.Image;

public class Simple extends GameObjectAbstract {

    public Simple(String name, double x, double y) {
        super(x, y,
                getImageForName(name),
                getWidthForName(name),
                getHeightForName(name));

        this.isPicked = false;
    }

    @Override
    public void interact(Player player) {
        System.out.println("collecting twigs");
    }
}

