package me.pulsi_.lootchestsplus.playerRegistry;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class PlayerRegistry {

    private final HashMap<UUID, LCPPlayer> players = new HashMap<>();

    public LCPPlayer getPlayer(Player p) {
        return players.get(p.getUniqueId());
    }

    public void registerPlayer(Player p) {
        players.put(p.getUniqueId(), new LCPPlayer());
    }

    public void unregisterPlayer(Player p) {
        players.remove(p.getUniqueId(), new LCPPlayer());
    }
}