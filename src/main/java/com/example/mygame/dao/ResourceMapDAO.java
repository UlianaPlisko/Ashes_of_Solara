package com.example.mygame.dao;

import com.example.mygame.models.ResourceMap;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ResourceMapDAO extends BaseDAO<ResourceMap> {

    @Override
    protected ResourceMap mapResultSet(ResultSet rs) throws SQLException {
        return new ResourceMap(
                rs.getInt("id"),
                rs.getInt("GameObjectId"),
                rs.getInt("ResourceId"),
                rs.getFloat("Probability")
        );
    }

    public int addResourceMap(ResourceMap resourceMap) {
        String query = "INSERT INTO \"ResourceMap\" (\"id\", \"GameObjectId\", \"ResourceId\", \"Probability\") VALUES (?, ?, ?, ?)";
        return executeUpdate(query,
                resourceMap.getId(),
                resourceMap.getGameObjectId(),
                resourceMap.getResourceId(),
                resourceMap.getProbability());
    }

    public int deleteResourceMap(Long id) {
        String query = "DELETE FROM \"ResourceMap\" WHERE \"id\" = ?";
        return executeUpdate(query, id);
    }

    public ResourceMap getResourceMapById(Long id) {
        String query = "SELECT * FROM \"ResourceMap\" WHERE \"id\" = ?";
        List<ResourceMap> resourceMaps = findAll(query, id);
        return resourceMaps.isEmpty() ? null : resourceMaps.get(0);
    }

    public int updateResourceMap(ResourceMap resourceMap) {
        String query = "UPDATE \"ResourceMap\" SET \"GameObjectId\" = ?, \"ResourceId\" = ?, \"Probability\" = ? WHERE \"id\" = ?";
        return executeUpdate(query,
                resourceMap.getGameObjectId(),
                resourceMap.getResourceId(),
                resourceMap.getProbability(),
                resourceMap.getId());
    }

    public List<ResourceMap> getAllResourceMaps() {
        String query = "SELECT * FROM \"ResourceMap\"";
        return findAll(query);
    }
}
