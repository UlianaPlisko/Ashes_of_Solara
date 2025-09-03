package com.example.mygame.game.Inventory;

import com.example.mygame.dao.InventoryDAO;
import com.example.mygame.dao.ResourceDAO;
import com.example.mygame.exceptions.InventorySlotLimitExceededException;
import com.example.mygame.game.Resource.ResourceDisplay;
import com.example.mygame.models.Inventory;
import com.example.mygame.models.Resource;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryService {

    private final InventoryDAO inventoryDAO = new InventoryDAO();
    private final ResourceDAO resourceDAO = new ResourceDAO();
    private static final int MAX_SLOTS = 15;

    private final Map<Integer, Integer> slotAssignments = new HashMap<>();
    private int nextAvailableSlot = 1;

    public List<ResourceDisplay> getInventoryDisplayItems(int characterId) {
        List<Inventory> rawInventory = inventoryDAO.getInventoriesByCharacterId(characterId);
        List<InventoryItem> inventoryItems = new ArrayList<>();

        for (Inventory inv : rawInventory) {
            if (inv.getResourceId() != 0) {
                Resource resource = resourceDAO.getResourceById(inv.getResourceId());
                ResourceInventoryItem item = new ResourceInventoryItem(resource, inv.getQuantity());
                inventoryItems.add(item);
            }
        }

        if (inventoryItems.size() > MAX_SLOTS) {
            throw new InventorySlotLimitExceededException(
                    "Inventory has " + inventoryItems.size() + " items, max allowed is " + MAX_SLOTS
            );
        }

        List<ResourceDisplay> displayItems = new ArrayList<>();

        for (InventoryItem item : inventoryItems) {
            int resourceId = item.getId();

            // Assign stable slot if not yet assigned
            int slot = slotAssignments.computeIfAbsent(resourceId, id -> {
                if (nextAvailableSlot > MAX_SLOTS) {
                    throw new InventorySlotLimitExceededException("No more free slots in inventory!");
                }
                return nextAvailableSlot++;
            });

            Image image = loadImageForName(item.getName());
            int quantity = item.getQuantity();

            displayItems.add(new ResourceDisplay(image, quantity, slot));
        }

        return displayItems;
    }

    private Image loadImageForName(String name) {
        String path = "/com/example/mygame/img/resources/" + name + ".png";
        return new Image(getClass().getResourceAsStream(path));
    }

}
