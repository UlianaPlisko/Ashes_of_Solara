package com.example.mygame.game.Objects.Tree;

import com.example.mygame.game.Objects.GameObjectAbstract;
import com.example.mygame.game.Objects.GameObjectConstants;
import com.example.mygame.game.Objects.SolidObject;
import com.example.mygame.game.player.Player;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

public class Tree extends GameObjectAbstract implements SolidObject {

    private final double trunkOffsetX;
    private final double trunkOffsetY;
    private final double trunkWidth;
    private final double trunkHeight;

    public Tree(String name, double x, double y) {
        super(x, y,
                getImageForName(name),
                getWidthForName(name),
                getHeightForName(name));

        this.trunkOffsetX = width / 2 - 2;  // center - half of trunk width
        this.trunkOffsetY = height - 10;    // near the bottom of the image
        this.trunkWidth = 4;                // narrow trunk
        this.trunkHeight = 4;
    }

    @Override
    public void interact(Player player) {
        System.out.println("Chopping tree ");
    }

    @Override
    public Rectangle2D getBounds() {
        return new Rectangle2D(
                x + trunkOffsetX,
                y + trunkOffsetY,
                trunkWidth,
                trunkHeight
        );
    }
}
