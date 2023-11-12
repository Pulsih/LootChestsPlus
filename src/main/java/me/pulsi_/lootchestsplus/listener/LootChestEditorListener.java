package me.pulsi_.lootchestsplus.listener;

import me.pulsi_.lootchestsplus.LootChestsPlus;
import me.pulsi_.lootchestsplus.playerRegistry.LCPPlayer;
import me.pulsi_.lootchestsplus.playerRegistry.PlayerRegistry;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class LootChestEditorListener implements Listener {

    private final PlayerRegistry registry;

    public LootChestEditorListener(LootChestsPlus plugin) {
        registry = plugin.getPlayerRegistry();
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        HumanEntity entity = e.getPlayer();
        if (!(entity instanceof Player)) return;

        LCPPlayer p = getPlayer((Player) entity);
        if (!p.isEditingContent()) return;



        p.setEditingContent(false);
    }

    @EventHandler
    public void onInteract(InventoryClickEvent e) {
        HumanEntity entity = e.getWhoClicked();
        if (!(entity instanceof Player)) return;

        LCPPlayer p = getPlayer((Player) entity);
        if (!p.isEditingContent()) return;
        e.setCancelled(true);

        Bukkit.broadcastMessage(e.getClickedInventory().toString());
    }

    private LCPPlayer getPlayer(Player p) {
        return registry.getPlayer(p);
    }
}