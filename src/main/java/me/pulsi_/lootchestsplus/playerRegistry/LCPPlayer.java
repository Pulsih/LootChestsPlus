package me.pulsi_.lootchestsplus.playerRegistry;

import me.pulsi_.lootchestsplus.lootchestRegistry.LootChest;

public class LCPPlayer {

    private LootChest editingLootChest;
    private boolean editingContent;

    public LootChest getEditingLootChest() {
        return editingLootChest;
    }

    public boolean isEditingContent() {
        return editingContent;
    }

    public void setEditingLootChest(LootChest editingLootChest) {
        this.editingLootChest = editingLootChest;
    }

    public void setEditingContent(boolean editingContent) {
        this.editingContent = editingContent;
    }
}