package com.example.mygame.dao;

import com.example.mygame.models.ToolCategory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ToolCategoryDAO extends BaseDAO<ToolCategory> {

    @Override
    protected ToolCategory mapResultSet(ResultSet rs) throws SQLException {
        return new ToolCategory(
                rs.getLong("id"),
                rs.getString("Name"),
                rs.getInt("Slot")
        );
    }

    public int addToolCategory(ToolCategory category) {
        String query = "INSERT INTO \"ToolCategory\" (\"Name\", \"Slot\") VALUES (?, ?)";
        return executeUpdate(query, category.getName(), category.getSlot());
    }

    public int deleteToolCategory(long id) {
        String query = "DELETE FROM \"ToolCategory\" WHERE \"id\" = ?";
        return executeUpdate(query, id);
    }

    public ToolCategory getToolCategoryById(long id) {
        String query = "SELECT * FROM \"ToolCategory\" WHERE \"id\" = ?";
        List<ToolCategory> categories = findAll(query, id);
        return categories.isEmpty() ? null : categories.get(0);
    }

    public int updateToolCategory(ToolCategory category) {
        String query = "UPDATE \"ToolCategory\" SET \"Name\" = ?, \"Slot\" = ? WHERE \"id\" = ?";
        return executeUpdate(query, category.getName(), category.getSlot(), category.getId());
    }

    public List<ToolCategory> getAllToolCategories() {
        String query = "SELECT * FROM \"ToolCategory\"";
        return findAll(query);
    }
}