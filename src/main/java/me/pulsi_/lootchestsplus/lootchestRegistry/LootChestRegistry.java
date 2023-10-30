package me.pulsi_.lootchestsplus.lootchestRegistry;

import me.pulsi_.lootchestsplus.LootChestsPlus;
import me.pulsi_.lootchestsplus.utils.LCPLogger;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

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
        if (!folder.exists()) folder.mkdirs();

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


            lootChests.add(lootChest);
        }
    }
}