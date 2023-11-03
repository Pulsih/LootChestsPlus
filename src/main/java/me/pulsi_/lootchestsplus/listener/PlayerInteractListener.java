package me.pulsi_.lootchestsplus.listener;

import me.pulsi_.lootchestsplus.LootChestsPlus;
import me.pulsi_.lootchestsplus.lootchestRegistry.LootChestRegistry;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {

    private final LootChestRegistry registry;

    public PlayerInteractListener(LootChestsPlus plugin) {
        registry = plugin.getLootChestRegistry();
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        Action action = e.getAction();
        Block block = e.getClickedBlock();

        if (action == Action.LEFT_CLICK_BLOCK && registry.getSpawnedLootChests().containsKey(block.getLocation()) && p.hasPermission("lootchestsplus.admin")) {
            e.setCancelled(true);
            return;
        }
    }
}