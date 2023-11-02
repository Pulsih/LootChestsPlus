package me.pulsi_.lootchestsplus.lootchestRegistry;

import me.pulsi_.lootchestsplus.LootChestsPlus;
import me.pulsi_.lootchestsplus.utils.LCPLogger;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.inventory.InventoryType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LootChestRegistry {

    private final List<LootChest> lootChests = new ArrayList<>();

    private final LootChestsPlus plugin;

    public LootChestRegistry(LootChestsPlus plugin) {
        this.plugin = plugin;
    }

    public void loadLootChests() {
        lootChests.clear();

        File folder = new File(plugin.getDataFolder(), "lootchests");
        if (!folder.exists()) plugin.saveResource("lootchests" + File.separator + "TestLootChest.yml", true);

        File[] lootChestFiles = folder.listFiles();
        if (lootChestFiles == null || lootChestFiles.length == 0) return;

        for (File file : lootChestFiles) {
            FileConfiguration values = new YamlConfiguration();

            try {
                values.load(file);
            } catch (IOException | InvalidConfigurationException e) {
                LCPLogger.warn(e, "Could not load \"" + file.getName() + "\" lootchest file! (Check if there are any yaml errors)");
                continue;
            }

            LootChest lootChest = new LootChest();
            lootChest.setTitle(values.getString("title"));
            lootChest.setLines(values.getInt("lines"));

            String configType = values.getString("inventory-type");
            InventoryType type = InventoryType.PLAYER;
            try {
                type = InventoryType.valueOf(configType);
            } catch (IllegalArgumentException e) {
                LCPLogger.warn("The lootchest \"" + file.getName() + "\" does not specify a valid inventory type (\"" + configType + "\"), using PLAYER as default.");
            }
            lootChest.setInventoryType(type);

            lootChests.add(lootChest);
        }
    }

    public List<LootChest> getLootChests() {
        return lootChests;
    }
}