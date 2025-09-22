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
                rs.getString("Type")
        );
    }

    public int addGameObject(GameObject gameObject) {
        String query = "INSERT INTO \"GameObject\" (\"Name\", \"Type\") VALUES (?, ?)";
        return executeUpdate(query,
                gameObject.getName(),
                gameObject.getType() != null ? gameObject.getType() : null);
    }

    public int deleteGameObject(Long id) {
        String query = "DELETE FROM \"GameObjectAbstract\" WHERE \"id\" = ?";
        return executeUpdate(query, id);
    }

    public GameObject getGameObjectById(Long id) {
        String query = "SELECT * FROM \"GameObject\" WHERE \"id\" = ?";
        List<GameObject> list = findAll(query, id);
        return list.isEmpty() ? null : list.get(0);
    }

    public int updateGameObject(GameObject gameObject) {
        String query = "UPDATE \"GameObjectAbstract\" SET \"Name\" = ?, \"Type\" = ? WHERE \"id\" = ?";
        return executeUpdate(query,
                gameObject.getName(),
                gameObject.getType(),
                gameObject.getId());
    }

    public List<GameObject> getAllGameObjects() {
        String query = "SELECT * FROM \"GameObject\"";
        return findAll(query);
    }
}
