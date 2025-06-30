package com.example.mygame.game.Objects.Tree;

import com.example.mygame.game.Objects.GameObject;
import com.example.mygame.game.Objects.GameObjectConstans;
import com.example.mygame.game.Objects.SolidObject;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

public class Tree extends GameObject implements SolidObject {
    protected String treeType;

    private final double trunkOffsetX;
    private final double trunkOffsetY;
    private final double trunkWidth;
    private final double trunkHeight;

    public Tree(double x, double y) {
        super(x, y, new Image(Tree.class.getResource(GameObjectConstans.BIG_TREE_IMAGE).toExternalForm()),
                GameObjectConstans.BIG_TREE_WIDTH, GameObjectConstans.BIG_TREE_HEIGHT);
        treeType = "Big tree";

        this.trunkOffsetX = width / 2 - 2;  // center - half of trunk width
        this.trunkOffsetY = height - 10;    // near the bottom of the image
        this.trunkWidth = 4;                // narrow trunk
        this.trunkHeight = 4;
    }

    @Override
    public void interact() {
        System.out.println("Chopping tree (" + treeType + ")");
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
