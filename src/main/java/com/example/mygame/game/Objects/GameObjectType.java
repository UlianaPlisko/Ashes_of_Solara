package com.example.mygame.game.Objects;

public enum GameObjectType {
    tree,
    bush,
    simple,
    other;

    public static GameObjectType fromString(String s) {
        if (s == null) return other;
        try {
            return GameObjectType.valueOf(s.toLowerCase());
        } catch (IllegalArgumentException ex) {
            return other;
        }
    }
}
