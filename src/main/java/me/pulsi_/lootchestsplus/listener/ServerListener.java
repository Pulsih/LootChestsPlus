package me.pulsi_.lootchestsplus.listener;

import me.pulsi_.lootchestsplus.LootChestsPlus;
import me.pulsi_.lootchestsplus.playerRegistry.PlayerRegistry;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ServerListener implements Listener {

    private final PlayerRegistry registry;

    public ServerListener(LootChestsPlus plugin) {
        registry = plugin.getPlayerRegistry();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        registry.registerPlayer(e.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        registry.unregisterPlayer(e.getPlayer());
    }
}