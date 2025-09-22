package com.example.mygame.dao;

import com.example.mygame.models.Crafting;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CraftingDAO extends BaseDAO<Crafting> {

    @Override
    protected Crafting mapResultSet(ResultSet rs) throws SQLException {
        return new Crafting(
                rs.getLong("Tool_id"),
                rs.getLong("Resource_id"),
                rs.getLong("ResourceQuantity")
        );
    }

    public int addCrafting(Crafting crafting) {
        String query = "INSERT INTO \"Crafting\" (\"Tool_id\", \"Resource_id\", \"ResourceQuantity\") VALUES (?, ?, ?)";
        return executeUpdate(query,
                crafting.getToolId(),
                crafting.getResourceId(),
                crafting.getResourceQuantity()
        );
    }

    public int deleteCrafting(long toolId, long resourceId) {
        String query = "DELETE FROM \"Crafting\" WHERE \"Tool_id\" = ? AND \"Resource_id\" = ?";
        return executeUpdate(query, toolId, resourceId);
    }

    public Crafting getCrafting(long toolId, long resourceId) {
        String query = "SELECT * FROM \"Crafting\" WHERE \"Tool_id\" = ? AND \"Resource_id\" = ?";
        List<Crafting> list = findAll(query, toolId, resourceId);
        return list.isEmpty() ? null : list.get(0);
    }

    public int updateCrafting(Crafting crafting) {
        String query = "UPDATE \"Crafting\" SET \"ResourceQuantity\" = ? WHERE \"Tool_id\" = ? AND \"Resource_id\" = ?";
        return executeUpdate(query,
                crafting.getResourceQuantity(),
                crafting.getToolId(),
                crafting.getResourceId()
        );
    }

    public List<Crafting> getAllCraftings() {
        String query = "SELECT * FROM \"Crafting\"";
        return findAll(query);
    }
}