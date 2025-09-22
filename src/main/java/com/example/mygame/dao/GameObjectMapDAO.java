package com.example.mygame.dao;

import com.example.mygame.models.GameObjectMap;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class GameObjectMapDAO extends BaseDAO<GameObjectMap> {
    @Override
    protected GameObjectMap mapResultSet(ResultSet rs) throws SQLException {
        return new GameObjectMap(
                rs.getLong("id"),
                rs.getLong("GameObject_id"),
                rs.getLong("x"),
                rs.getLong("y")
        );
    }

    public int addMapping(GameObjectMap mapping) {
        String query = "INSERT INTO \"GameObjectMap\" (\"GameObject_id\", \"x\", \"y\") VALUES (?, ?, ?)";
        return executeUpdate(query,
                mapping.getGameObjectId(),
                mapping.getX(),
                mapping.getY());
    }

    public int deleteMappingById(Long id) {
        String query = "DELETE FROM \"GameObjectMap\" WHERE \"id\" = ?";
        return executeUpdate(query, id);
    }

    public int deleteMappingsForGameObject(Long gameObjectId) {
        String query = "DELETE FROM \"GameObjectMap\" WHERE \"GameObject_id\" = ?";
        return executeUpdate(query, gameObjectId);
    }

    public GameObjectMap getMappingById(Long id) {
        String query = "SELECT * FROM \"GameObjectMap\" WHERE \"id\" = ?";
        List<GameObjectMap> list = findAll(query, id);
        return list.isEmpty() ? null : list.get(0);
    }

    public List<GameObjectMap> getMappingsForGameObject(Long gameObjectId) {
        String query = "SELECT * FROM \"GameObjectMap\" WHERE \"GameObject_id\" = ?";
        return findAll(query, gameObjectId);
    }

    public List<GameObjectMap> getAllMappings() {
        String query = "SELECT * FROM \"GameObjectMap\"";
        return findAll(query);
    }

    public int updateMapping(GameObjectMap mapping) {
        String query = "UPDATE \"GameObjectMap\" SET \"GameObject_id\" = ?, \"x\" = ?, \"y\" = ? WHERE \"id\" = ?";
        return executeUpdate(query,
                mapping.getGameObjectId(),
                mapping.getX(),
                mapping.getY(),
                mapping.getId());
    }
}
