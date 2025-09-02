package com.example.mygame.game.Inventory;

import com.example.mygame.dao.InventoryDAO;
import com.example.mygame.dao.ResourceDAO;
import com.example.mygame.exceptions.InventorySlotLimitExceededException;
import com.example.mygame.game.Resource.ResourceDisplay;
import com.example.mygame.models.Inventory;
import com.example.mygame.models.Resource;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class InventoryService {

    private final InventoryDAO inventoryDAO = new InventoryDAO();
    private final ResourceDAO resourceDAO = new ResourceDAO();
    private static final int MAX_SLOTS = 15;

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
        int slotCounter = 1;

        for (InventoryItem item : inventoryItems) {
            Image image = loadImageForName(item.getName());
            int quantity = item.getQuantity();
            int slot = slotCounter++;
            displayItems.add(new ResourceDisplay(image, quantity, slot));
        }

        return displayItems;
    }

    private Image loadImageForName(String name) {
        String path = "/com/example/mygame/img/resources/" + name + ".png";
        return new Image(getClass().getResourceAsStream(path));
    }

}
