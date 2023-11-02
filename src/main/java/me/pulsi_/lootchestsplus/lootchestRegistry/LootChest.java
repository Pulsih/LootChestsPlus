package me.pulsi_.lootchestsplus.lootchestRegistry;

import org.bukkit.event.inventory.InventoryType;

public class LootChest {

    private String title;
    private int lines;
    private InventoryType inventoryType;

    public String getTitle() {
        return title;
    }

    public int getLines() {
        return lines;
    }

    public InventoryType getInventoryType() {
        return inventoryType;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLines(int lines) {
        this.lines = lines;
    }

    public void setInventoryType(InventoryType inventoryType) {
        this.inventoryType = inventoryType;
    }
}