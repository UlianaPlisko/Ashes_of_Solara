package com.example.mygame.game.Objects.Tree;

import com.example.mygame.game.Objects.GameObject;
import com.example.mygame.game.Objects.GameObjectConstans;
import javafx.scene.image.Image;

public class Tree extends GameObject {
    protected String treeType;

    public Tree(double x, double y) {
        super(x, y, new Image(Tree.class.getResource("/com/example/mygame/img/tree/Birchnut_Trees_Red_Big.png").toExternalForm()),
                GameObjectConstans.BIG_TREE_WIDTH, GameObjectConstans.BIG_TREE_HEIGHT);
        treeType = "Big tree";
    }

    @Override
    public void interact() {
        System.out.println("Chopping tree (" + treeType + ")");
    }
}
