package com.example.mygame.game.Objects.Bush;

import com.example.mygame.game.Objects.GameObject;
import com.example.mygame.game.Objects.GameObjectConstans;
import com.example.mygame.game.Objects.Tree.Tree;
import javafx.scene.image.Image;

public class Bush extends GameObject {

    public Bush(double x, double y) {
        super(x, y, new Image(Tree.class.getResource(GameObjectConstans.JUICY_BERRY_BUSH).toExternalForm()),
                GameObjectConstans.JUICY_BERRY_BUSH_WIDTH, GameObjectConstans.JUICY_BERRY_BUSH_HEIGHT);
    }

    @Override
    public void interact() {

    }
}
