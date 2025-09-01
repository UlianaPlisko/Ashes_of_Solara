package com.example.mygame.game.Inventory;

import com.example.mygame.dao.InventoryDAO;
import com.example.mygame.dao.ResourceDAO;
import com.example.mygame.game.Resource.ResourceDisplay;
import com.example.mygame.models.Inventory;
import com.example.mygame.models.Resource;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class InventoryService {

    private final InventoryDAO inventoryDAO = new InventoryDAO();
    private final ResourceDAO resourceDAO = new ResourceDAO();

    public List<ResourceDisplay> getInventoryDisplayItems(int characterId) {
        List<Inventory> rawInventory = inventoryDAO.getInventoriesByCharacterId(characterId);
        List<InventoryItem> inventoryItems = new ArrayList<>();

        for (Inventory inv : rawInventory) {
            if (inv.getResourceId() != 0) {
                Resource resource = resourceDAO.getResourceById(inv.getResourceId());
                ResourceInventoryItem item = new ResourceInventoryItem(resource, inv.getQuantity());
                inventoryItems.add(item);
            }
            // You can add cases for Tool and Meal similarly:
            // else if (inv.getToolId() != 0) { ... }
            // else if (inv.getMealId() != 0) { ... }
        }

        List<ResourceDisplay> displayItems = new ArrayList<>();

        for (InventoryItem item : inventoryItems) {
            Image image = loadImageForName(item.getName());
            int quantity = item.getQuantity();
            int slot = determineInventorySlot(item); // you define this
            displayItems.add(new ResourceDisplay(image, quantity, slot));
        }

        return displayItems;
    }

    private Image loadImageForName(String name) {
        String path = "/com/example/mygame/img/resources/" + name + ".png";
        return new Image(getClass().getResourceAsStream(path));
    }

    private int determineInventorySlot(InventoryItem item) {
        return 1; // or your own logic
    }
}
