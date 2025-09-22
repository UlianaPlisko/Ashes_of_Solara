package com.example.mygame.dao;

import com.example.mygame.models.Tool;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ToolDAO extends BaseDAO<Tool> {

    @Override
    protected Tool mapResultSet(ResultSet rs) throws SQLException {
        return new Tool(
                rs.getLong("id"),
                rs.getString("Name"),
                rs.getString("Description"),
                rs.getLong("Category_id"),
                rs.getObject("MaxUses") != null ? rs.getInt("MaxUses") : null
        );
    }

    public int addTool(Tool tool) {
        String query = "INSERT INTO \"Tool\" (\"Name\", \"Description\", \"Category_id\", \"MaxUses\") VALUES (?, ?, ?, ?)";
        return executeUpdate(query,
                tool.getName(),
                tool.getDescription(),
                tool.getCategoryId(),
                tool.getMaxUses()
        );
    }

    public int deleteTool(long id) {
        String query = "DELETE FROM \"Tool\" WHERE \"id\" = ?";
        return executeUpdate(query, id);
    }

    public Tool getToolById(long id) {
        String query = "SELECT * FROM \"Tool\" WHERE \"id\" = ?";
        List<Tool> tools = findAll(query, id);
        return tools.isEmpty() ? null : tools.get(0);
    }

    public int updateTool(Tool tool) {
        String query = "UPDATE \"Tool\" SET \"Name\" = ?, \"Description\" = ?, \"Category_id\" = ?, \"MaxUses\" = ? WHERE \"id\" = ?";
        return executeUpdate(query,
                tool.getName(),
                tool.getDescription(),
                tool.getCategoryId(),
                tool.getMaxUses(),
                tool.getId()
        );
    }

    public List<Tool> getAllTools() {
        String query = "SELECT * FROM \"Tool\"";
        return findAll(query);
    }
}
