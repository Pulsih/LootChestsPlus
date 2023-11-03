package me.pulsi_.lootchestsplus;

import me.pulsi_.lootchestsplus.lootchestRegistry.LootChestRegistry;
import me.pulsi_.lootchestsplus.managers.LCPConfigs;
import me.pulsi_.lootchestsplus.managers.LCPData;
import me.pulsi_.lootchestsplus.playerRegistry.PlayerRegistry;
import org.bukkit.plugin.java.JavaPlugin;

public final class LootChestsPlus extends JavaPlugin {

    public static LootChestsPlus INSTANCE;

    private LootChestRegistry lootChestRegistry;
    private PlayerRegistry playerRegistry;

    private LCPConfigs configs;
    private LCPData data;

    @Override
    public void onEnable() {
        INSTANCE = this;

        lootChestRegistry = new LootChestRegistry(this);
        playerRegistry = new PlayerRegistry();

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
}
