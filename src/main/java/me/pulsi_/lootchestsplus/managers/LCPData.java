package me.pulsi_.lootchestsplus.managers;

import me.pulsi_.lootchestsplus.LootChestsPlus;
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
        LCPLogger.log("    &aDetected server version: &f" + plugin.getServerVersion());

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
        Values.CONFIG.setupValues();
        Values.MESSAGES.setupValues();
        Values.MULTIPLE_BANKS.setupValues();
        BPMessages.loadMessages();

        CmdRegisterer registerer = new CmdRegisterer();
        registerer.resetCmds();
        registerer.registerCmds();

        if (Values.CONFIG.isLogTransactions()) plugin.getBpLogUtils().setupLoggerFile();
        if (Values.CONFIG.isIgnoringAfkPlayers()) plugin.getAfkManager().startCountdown();
        if (Values.CONFIG.isBanktopEnabled()) plugin.getBankTopManager().startUpdateTask();
        if (Values.CONFIG.isGuiModuleEnabled() && !plugin.getBankGuiRegistry().loadBanks()) success = false;

        AFKManager afkManager = plugin.getAfkManager();
        if (!afkManager.isPlayerCountdownActive()) afkManager.startCountdown();

        BPInterest interest = plugin.getInterest();
        if (Values.CONFIG.isInterestEnabled() && interest.wasDisabled()) interest.startInterest();

        Bukkit.getOnlinePlayers().forEach(p -> {
            BPPlayer player = BankPlus.INSTANCE.getPlayerRegistry().get(p);
            if (player != null && player.getOpenedBank() != null) p.closeInventory();
        });
        return success;
    }

    private void registerEvents() {
        PluginManager plManager = plugin.getServer().getPluginManager();

        plManager.registerEvents(new PlayerJoinListener(), plugin);
    }

    private void setupCommands() {

    }
}