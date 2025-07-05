package com.example.mygame.dao;

import com.example.mygame.models.Inventory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class InventoryDAO extends BaseDAO<Inventory> {

    @Override
    protected Inventory mapResultSet(ResultSet rs) throws SQLException {
        return new Inventory(
                rs.getInt("id"),
                rs.getInt("Character_id"),
                rs.getInt("Resource_id"),
                rs.getInt("Tool_id"),
                rs.getInt("Meal_id"),
                rs.getInt("Quantity")
        );
    }

    public int addInventory(Inventory inventory) {
        String query = "INSERT INTO \"Inventory\" (\"Character_id\", \"Resource_id\", \"Tool_id\", \"Meal_id\", \"Quantity\") VALUES (?, ?, ?, ?, ?)";
        return executeUpdate(query,
                inventory.getCharacterId(),
                inventory.getResourceId(),
                inventory.getToolId(),
                inventory.getMealId(),
                inventory.getQuantity());
    }

    public int deleteInventory(Long id) {
        String query = "DELETE FROM \"Inventory\" WHERE \"id\" = ?";
        return executeUpdate(query, id);
    }

    public Inventory getInventoryById(Long id) {
        String query = "SELECT * FROM \"Inventory\" WHERE \"id\" = ?";
        List<Inventory> inventories = findAll(query, id);
        return inventories.isEmpty() ? null : inventories.get(0);
    }

    public int updateInventory(Inventory inventory) {
        String query = "UPDATE \"Inventory\" SET \"Character_id\" = ?, \"Resource_id\" = ?, \"Tool_id\" = ?, \"Meal_id\" = ?, \"Quantity\" = ? WHERE \"id\" = ?";
        return executeUpdate(query,
                inventory.getCharacterId(),
                inventory.getResourceId(),
                inventory.getToolId(),
                inventory.getMealId(),
                inventory.getQuantity(),
                inventory.getId());
    }

    public List<Inventory> getAllInventories() {
        String query = "SELECT * FROM \"Inventory\"";
        return findAll(query);
    }

    public Inventory getByCharacterAndResource(int characterId, int resourceId) {
        String query = "SELECT * FROM \"Inventory\" WHERE \"Character_id\" = ? AND \"Resource_id\" = ?";
        List<Inventory> inventories = findAll(query, characterId, resourceId);
        return inventories.isEmpty() ? null : inventories.get(0);
    }
}
