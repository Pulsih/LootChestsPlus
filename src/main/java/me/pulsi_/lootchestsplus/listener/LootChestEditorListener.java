package me.pulsi_.lootchestsplus.listener;

import me.pulsi_.lootchestsplus.LootChestsPlus;
import me.pulsi_.lootchestsplus.playerRegistry.LCPPlayer;
import me.pulsi_.lootchestsplus.playerRegistry.PlayerRegistry;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

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

        ItemStack[] content = e.getInventory().getContents();


        p.setEditingContent(false);
    }

    private LCPPlayer getPlayer(Player p) {
        return registry.getPlayer(p);
    }
}