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
                rs.getInt("Quantity"),
                rs.getInt("current_uses")
        );
    }

    public int addInventory(Inventory inventory) {
        String query = "INSERT INTO \"Inventory\" (\"Character_id\", \"Resource_id\", \"Tool_id\", \"Quantity\", \"current_uses\") " +
                "VALUES (?, ?, ?, ?, ?)";

        // Convert 0 (unused ID) to null to satisfy FK + CHECK constraints
        Long resourceId = (inventory.getResourceId() == 0) ? null : Long.valueOf(inventory.getResourceId());
        Long toolId     = (inventory.getToolId() == 0) ? null : Long.valueOf(inventory.getToolId());

        return executeUpdate(query,
                inventory.getCharacterId(),
                resourceId,
                toolId,
                inventory.getQuantity(),
                100); // current_uses
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
        String query = "UPDATE \"Inventory\" SET \"Character_id\" = ?, \"Resource_id\" = ?, \"Tool_id\" = ?, \"Quantity\" = ?, \"current_uses\" = ? WHERE \"id\" = ?";

        Long resourceId = (inventory.getResourceId() == 0) ? null : Long.valueOf(inventory.getResourceId());
        Long toolId     = (inventory.getToolId() == 0) ? null : Long.valueOf(inventory.getToolId());

        return executeUpdate(query,
                inventory.getCharacterId(),
                resourceId,
                toolId,
                inventory.getQuantity(),
                inventory.getCurrent_uses(),
                inventory.getId());
    }

    public List<Inventory> getAllInventories() {
        String query = "SELECT * FROM \"Inventory\"";
        return findAll(query);
    }

    public List<Inventory> getInventoriesByCharacterId(int characterId) {
        String query = "SELECT * FROM \"Inventory\" WHERE \"Character_id\" = ?";
        return findAll(query, characterId);
    }

    public Inventory getByCharacterAndResource(int characterId, int resourceId) {
        String query = "SELECT * FROM \"Inventory\" WHERE \"Character_id\" = ? AND \"Resource_id\" = ?";
        List<Inventory> inventories = findAll(query, characterId, resourceId);
        return inventories.isEmpty() ? null : inventories.get(0);
    }
}
