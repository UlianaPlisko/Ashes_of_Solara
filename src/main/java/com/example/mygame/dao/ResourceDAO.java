package com.example.mygame.dao;

import com.example.mygame.models.Resource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ResourceDAO extends BaseDAO<Resource> {

    @Override
    protected Resource mapResultSet(ResultSet rs) throws SQLException {
        return new Resource(
                rs.getInt("id"),
                rs.getString("Name")
        );
    }

    public int addResource(Resource resource) {
        String query = "INSERT INTO \"Resource\" (\"id\", \"Name\") VALUES (?, ?)";
        return executeUpdate(query, resource.getId(), resource.getName());
    }

    public int deleteResource(Long id) {
        String query = "DELETE FROM \"Resource\" WHERE \"id\" = ?";
        return executeUpdate(query, id);
    }

    public Resource getResourceById(Long id) {
        String query = "SELECT * FROM \"Resource\" WHERE \"id\" = ?";
        List<Resource> resources = findAll(query, id);
        return resources.isEmpty() ? null : resources.get(0);
    }

    public int updateResource(Resource resource) {
        String query = "UPDATE \"Resource\" SET \"Name\" = ? WHERE \"id\" = ?";
        return executeUpdate(query, resource.getName(), resource.getId());
    }

    public List<Resource> getAllResources() {
        String query = "SELECT * FROM \"Resource\"";
        return findAll(query);
    }
}
