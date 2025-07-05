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

    public int deleteResource(int id) {
        String query = "DELETE FROM \"Resource\" WHERE \"id\" = ?";
        return executeUpdate(query, id);
    }

    public Resource getResourceById(int id) {
        String query = "SELECT * FROM \"Resource\" WHERE \"id\" = ?";
        List<Resource> resources = findAll(query, id);
        return resources.isEmpty() ? null : resources.get(0);
    }

    public int getIdByName(String name) {
        String query = "SELECT \"id\" FROM \"Resource\" WHERE \"Name\" = ?";
        Number result = findSingleValue(query, name); // returns Long from JDBC
        return result != null ? result.intValue() : -1;
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
