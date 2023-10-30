package me.pulsi_.lootchestsplus;

import me.pulsi_.lootchestsplus.lootchestRegistry.LootChestRegistry;
import me.pulsi_.lootchestsplus.managers.LCPConfigs;
import me.pulsi_.lootchestsplus.managers.LCPData;
import org.bukkit.plugin.java.JavaPlugin;

public final class LootChestsPlus extends JavaPlugin {

    public static LootChestsPlus INSTANCE;

    private LootChestRegistry lootChestRegistry;
    private LCPConfigs configs;
    private LCPData data;

    @Override
    public void onEnable() {
        INSTANCE = this;

        lootChestRegistry = new LootChestRegistry(this);
        configs = new LCPConfigs(this);
        data = new LCPData(this);
    }

    @Override
    public void onDisable() {

    }

    public LootChestRegistry getLootChestRegistry() {
        return lootChestRegistry;
    }

    public LCPConfigs getConfigs() {
        return configs;
    }

    public LCPData getData() {
        return data;
    }
}
