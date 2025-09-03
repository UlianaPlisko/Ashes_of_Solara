package com.example.mygame.game.Objects.Bush;

import com.example.mygame.game.Objects.GameObjectAbstract;
import com.example.mygame.game.Objects.GameObjectConstants;
import com.example.mygame.game.Objects.Tree.Tree;
import com.example.mygame.game.player.Player;
import javafx.scene.image.Image;

public class Bush extends GameObjectAbstract {
    private Image emptyImage;

    public Bush(String name, double x, double y) {
        super(x, y,
                getImageForName(name),
                getWidthForName(name),
                getHeightForName(name));

        this.emptyImage = getImageAfterForName(name);
        this.isPicked = false;
        this.name = name;
    }

    @Override
    public void interact(Player player) {
        if (!isPicked) {
            isPicked = true;
            setImage(emptyImage);
            player.addInInventory("Juicy_Berries");
        }
    }
}
