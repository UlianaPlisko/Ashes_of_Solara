package com.example.mygame.game.Objects.Bush;

import com.example.mygame.game.Objects.GameObjectAbstract;
import com.example.mygame.game.Objects.GameObjectConstants;
import com.example.mygame.game.Objects.Tree.Tree;
import javafx.scene.image.Image;

public class Bush extends GameObjectAbstract {

    public Bush(double x, double y) {
        super(x, y, new Image(Tree.class.getResource(GameObjectConstants.JUICY_BERRY_BUSH).toExternalForm()),
                GameObjectConstants.JUICY_BERRY_BUSH_WIDTH, GameObjectConstants.JUICY_BERRY_BUSH_HEIGHT);
    }

    @Override
    public void interact() {

    }
}
