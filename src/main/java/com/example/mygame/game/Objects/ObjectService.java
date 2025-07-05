package com.example.mygame.game.Objects;

import com.example.mygame.dao.GameObjectDAO;
import com.example.mygame.game.Objects.Bush.Bush;
import com.example.mygame.game.Objects.Tree.Tree;
import com.example.mygame.models.GameObject;

import java.util.ArrayList;
import java.util.List;

public class ObjectService {
    private final GameObjectDAO gameObjectDAO;

    public ObjectService() {
        gameObjectDAO = new GameObjectDAO();
    }

    public List<GameObjectAbstract> getAllGameObjects() {
        List<GameObjectAbstract> gameObjects = new ArrayList<>();

        List<GameObject> fromDB = gameObjectDAO.getAllGameObjects();

        // Loop through all game objects from the database
        for (GameObject gameObject : fromDB) {
            switch (gameObject.getType()) {
                case "tree":
                    gameObjects.add(new Tree(gameObject.getName(), gameObject.getX(), gameObject.getY()));
                    break;
                case "bush":
                    gameObjects.add(new Bush(gameObject.getName(), gameObject.getX(), gameObject.getY()));
                    break;
                default:
                    throw new IllegalArgumentException("Unknown GameObject type: " + gameObject.getType());
            }
        }
        return gameObjects;
    }
}
