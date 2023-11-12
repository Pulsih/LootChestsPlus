package me.pulsi_.lootchestsplus;

import me.pulsi_.lootchestsplus.lootchestRegistry.LootChestRegistry;
import me.pulsi_.lootchestsplus.managers.LCPConfigs;
import me.pulsi_.lootchestsplus.managers.LCPData;
import me.pulsi_.lootchestsplus.playerRegistry.PlayerRegistry;
import me.pulsi_.lootchestsplus.utils.LCPLogger;
import org.bukkit.plugin.java.JavaPlugin;

public final class LootChestsPlus extends JavaPlugin {

    public static LootChestsPlus INSTANCE;

    private LootChestRegistry lootChestRegistry;
    private PlayerRegistry playerRegistry;

    private LCPConfigs configs;
    private LCPData data;

    private int serverVersionInt;

    @Override
    public void onEnable() {
        INSTANCE = this;

        lootChestRegistry = new LootChestRegistry(this);
        playerRegistry = new PlayerRegistry();

        String serverVersion = getServer().getVersion();

        int index = serverVersion.lastIndexOf("MC:");
        String version = serverVersion.substring(index, serverVersion.length() - 1);

        int number;
        try {
            number = Integer.parseInt(version.split("\\.")[1]);
        } catch (NumberFormatException e) {
            LCPLogger.error("Failed to identify server version, contact the developer if the issue persist!");
            number = -1;
        }

        this.serverVersionInt = number;

        configs = new LCPConfigs(this);
        data = new LCPData(this);
        data.setupPlugin();
    }

    @Override
    public void onDisable() {
        data.shutdownPlugin();
    }

    public LootChestRegistry getLootChestRegistry() {
        return lootChestRegistry;
    }

    public PlayerRegistry getPlayerRegistry() {
        return playerRegistry;
    }

    public LCPConfigs getConfigs() {
        return configs;
    }

    public LCPData getData() {
        return data;
    }

    public int getServerVersionInt() {
        return serverVersionInt;
    }
}
