package com.example.mygame.dao;

import com.example.mygame.models.GameObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class GameObjectDAO extends BaseDAO<GameObject> {

    @Override
    protected GameObject mapResultSet(ResultSet rs) throws SQLException {
        return new GameObject(
                rs.getInt("id"),
                rs.getString("Name"),
                rs.getString("Type"),
                rs.getInt("x"),
                rs.getInt("y")
        );
    }

    public int addGameObject(GameObject gameObject) {
        String query = "INSERT INTO \"GameObjectAbstract\" (\"id\", \"Name\", \"Type\", \"x\", \"y\") VALUES (?, ?, ?, ?, ?)";
        return executeUpdate(query,
                gameObject.getId(),
                gameObject.getName(),
                gameObject.getType(),
                gameObject.getX(),
                gameObject.getY());
    }

    public int deleteGameObject(Long id) {
        String query = "DELETE FROM \"GameObjectAbstract\" WHERE \"id\" = ?";
        return executeUpdate(query, id);
    }

    public GameObject getGameObjectById(Long id) {
        String query = "SELECT * FROM \"GameObjectAbstract\" WHERE \"id\" = ?";
        List<GameObject> gameObjects = findAll(query, id);
        return gameObjects.isEmpty() ? null : gameObjects.get(0);
    }

    public int updateGameObject(GameObject gameObject) {
        String query = "UPDATE \"GameObjectAbstract\" SET \"Name\" = ?, \"Type\" = ?, \"x\" = ?, \"y\" = ? WHERE \"id\" = ?";
        return executeUpdate(query,
                gameObject.getName(),
                gameObject.getType(),
                gameObject.getX(),
                gameObject.getY(),
                gameObject.getId());
    }

    public List<GameObject> getAllGameObjects() {
        String query = "SELECT * FROM \"GameObject\"";
        return findAll(query);
    }
}
