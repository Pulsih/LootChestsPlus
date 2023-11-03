package me.pulsi_.lootchestsplus.managers;

import me.pulsi_.lootchestsplus.LootChestsPlus;
import me.pulsi_.lootchestsplus.listener.PlayerInteractListener;
import me.pulsi_.lootchestsplus.utils.LCPChat;
import me.pulsi_.lootchestsplus.utils.LCPLogger;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;

import java.io.File;
import java.io.IOException;

public class LCPData {

    private final LootChestsPlus plugin;

    public LCPData(LootChestsPlus plugin) {
        this.plugin = plugin;
    }

    public void setupPlugin() {
        long startTime = System.currentTimeMillis();

        LCPLogger.log("");
        LCPLogger.log("    " + LCPChat.longPrefix + " &2Enabling plugin...");
        LCPLogger.log("    &aRunning on version &f" + plugin.getDescription().getVersion() + "&a!");
        LCPLogger.log("    &aDetected server version: &f" + plugin.getServer().getVersion());

        LCPLogger.log("    &aSetting up the plugin...");
        plugin.getConfigs().setupConfigs();
        reloadPlugin();

        registerEvents();
        setupCommands();

        LCPLogger.log("    &aDone! &8(&3" + (System.currentTimeMillis() - startTime) + "ms&8)");
        LCPLogger.log("");
    }

    public void shutdownPlugin() {
        LCPConfigs configs = plugin.getConfigs();
        File file = configs.getFile(LCPConfigs.Type.SAVES.name);
        FileConfiguration savesConfig = configs.getConfig(LCPConfigs.Type.SAVES.name);

        try {
            savesConfig.save(file);
        } catch (IOException e) {
            LCPLogger.error(e, "Failed to save changes to \"saves.yml\" file! " + e.getMessage());
        }

        LCPLogger.log("");
        LCPLogger.log("    " + LCPChat.longPrefix + " &cPlugin successfully disabled!");
        LCPLogger.log("");
    }

    public boolean reloadPlugin() {
        boolean success = true;

        plugin.getLootChestRegistry().loadLootChests();

        return success;
    }

    private void registerEvents() {
        PluginManager plManager = plugin.getServer().getPluginManager();

        plManager.registerEvents(new PlayerInteractListener(plugin), plugin);
    }

    private void setupCommands() {

    }
}