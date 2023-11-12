package me.pulsi_.lootchestsplus.managers;

import me.pulsi_.lootchestsplus.LootChestsPlus;
import me.pulsi_.lootchestsplus.commands.CmdRegisterer;
import me.pulsi_.lootchestsplus.commands.MainCmd;
import me.pulsi_.lootchestsplus.listener.LootChestEditorListener;
import me.pulsi_.lootchestsplus.listener.PlayerInteractListener;
import me.pulsi_.lootchestsplus.listener.ServerListener;
import me.pulsi_.lootchestsplus.utils.LCPChat;
import me.pulsi_.lootchestsplus.utils.LCPLogger;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;

import java.io.File;
import java.io.IOException;

public class LCPData {

    private final LootChestsPlus plugin;
    private final CmdRegisterer registerer;

    public LCPData(LootChestsPlus plugin) {
        this.plugin = plugin;
        this.registerer = new CmdRegisterer();
    }

    public void setupPlugin() {
        long startTime = System.currentTimeMillis();

        LCPLogger.log("");
        LCPLogger.log("    " + LCPChat.longPrefix + " &2Enabling plugin...");
        LCPLogger.log("    &cRunning on version &a" + plugin.getDescription().getVersion() + "&a!");
        LCPLogger.log("    &cDetected server version: &a" + plugin.getServer().getVersion());

        LCPLogger.log("    &cSetting up the plugin...");
        plugin.getConfigs().setupConfigs();
        reloadPlugin();

        registerEvents();
        setupCommands();
        registerer.registerCmds();

        LCPLogger.log("    &cDone! &8(&a" + (System.currentTimeMillis() - startTime) + "ms&8)");
        LCPLogger.log("");
    }

    public void shutdownPlugin() {
        LCPConfigs configs = plugin.getConfigs();
        File file = configs.getFile("saves.yml");
        FileConfiguration savesConfig = configs.getConfig(file);

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

        plManager.registerEvents(new LootChestEditorListener(plugin), plugin);
        plManager.registerEvents(new PlayerInteractListener(plugin), plugin);
        plManager.registerEvents(new ServerListener(plugin), plugin);

    }

    private void setupCommands() {
        plugin.getCommand("lootchestsplus").setExecutor(new MainCmd());
    }
}