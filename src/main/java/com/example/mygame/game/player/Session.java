package com.example.mygame.game.player;

import lombok.Getter;
import lombok.Setter;

public class Session {
    @Getter
    @Setter
    private static com.example.mygame.models.Character currentCharacter;

    public static void clear() {
        currentCharacter = null;
    }
}
