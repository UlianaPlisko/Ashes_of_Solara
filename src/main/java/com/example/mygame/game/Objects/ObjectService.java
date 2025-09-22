package com.example.mygame.game.Objects;

import com.example.mygame.dao.GameObjectDAO;
import com.example.mygame.dao.GameObjectMapDAO;
import com.example.mygame.game.Objects.Bush.Bush;
import com.example.mygame.game.Objects.Simple.Simple;
import com.example.mygame.game.Objects.Tree.Tree;
import com.example.mygame.models.GameObject;
import com.example.mygame.models.GameObjectMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectService {
    private final GameObjectDAO gameObjectDAO;
    private final GameObjectMapDAO gameObjectMapDAO;

    public ObjectService() {
        gameObjectDAO = new GameObjectDAO();
        gameObjectMapDAO = new GameObjectMapDAO();
    }

    public List<GameObjectAbstract> getAllGameObjects() {
        List<GameObjectAbstract> gameObjects = new ArrayList<>();

        List<GameObject> defs = gameObjectDAO.getAllGameObjects();
        Map<Long, GameObject> defsById = new HashMap<>();
        for (GameObject d : defs) {
            defsById.put((long) d.getId(), d);
        }

        List<GameObjectMap> maps = gameObjectMapDAO.getAllMappings();
        for (GameObjectMap m : maps) {
            GameObject def = defsById.get(m.getGameObjectId());
            if (def == null) {
                System.err.println("ObjectService: missing GameObject definition for id=" + m.getGameObjectId());
                continue;
            }

            int x = (int) m.getX();
            int y = (int) m.getY();

            GameObjectType type = GameObjectType.fromString(def.getType());

            switch (type) {
                case tree -> gameObjects.add(new Tree(def.getName(), x, y));
                case bush -> gameObjects.add(new Bush(def.getName(), x, y));
                case simple -> gameObjects.add(new Simple(def.getName(), x, y));
                default -> gameObjects.add(new Simple(def.getName(), x, y));
            }
        }

        return gameObjects;
    }
}
