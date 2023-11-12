package me.pulsi_.lootchestsplus.lootchestRegistry;

import me.pulsi_.lootchestsplus.LootChestsPlus;
import me.pulsi_.lootchestsplus.utils.LCPLogger;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.inventory.InventoryType;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class LootChestRegistry {

    private final HashMap<Location, LootChest> spawnedLootChests = new HashMap<>();
    private final HashMap<String, LootChest> lootChests = new HashMap<>();

    private final LootChestsPlus plugin;

    public LootChestRegistry(LootChestsPlus plugin) {
        this.plugin = plugin;
    }

    public void loadLootChests() {
        lootChests.clear();

        File folder = new File(plugin.getDataFolder(), "lootchests");
        if (!folder.exists()) plugin.saveResource("lootchests" + File.separator + "TestLootChest.yml", true);

        File[] lootChestFiles = folder.listFiles();
        if (lootChestFiles == null) return;

        for (File file : lootChestFiles) {
            String name = file.getName().replace(".yml", "").replace(".yaml", "");
            FileConfiguration values = new YamlConfiguration();

            try {
                values.load(file);
            } catch (IOException | InvalidConfigurationException e) {
                LCPLogger.warn(e, "Could not load \"" + name + "\" lootchest file! (Check if there are any yaml errors)");
                continue;
            }

            LootChest lootChest = new LootChest(name);
            lootChest.setTitle(values.getString("title"));
            lootChest.setLines(values.getInt("lines"));

            String configType = values.getString("inventory-type");
            InventoryType type = InventoryType.PLAYER;
            try {
                type = InventoryType.valueOf(configType);
            } catch (IllegalArgumentException e) {
                LCPLogger.warn("The lootchest \"" + name + "\" does not specify a valid inventory type (\"" + configType + "\"), using PLAYER as default.");
            }
            lootChest.setInventoryType(type);

            lootChests.put(name, lootChest);
        }
    }

    public HashMap<Location, LootChest> getSpawnedLootChests() {
        return spawnedLootChests;
    }

    public HashMap<String, LootChest> getLootChests() {
        return lootChests;
    }
}