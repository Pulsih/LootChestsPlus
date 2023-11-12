package me.pulsi_.lootchestsplus.commands.list;

import me.pulsi_.lootchestsplus.LootChestsPlus;
import me.pulsi_.lootchestsplus.commands.LCPCommand;
import me.pulsi_.lootchestsplus.lootchestRegistry.LootChestRegistry;
import me.pulsi_.lootchestsplus.playerRegistry.LCPPlayer;
import me.pulsi_.lootchestsplus.utils.LCPMessages;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class EditCmd extends LCPCommand {

    private final LootChestRegistry registry;

    public EditCmd(String... aliases) {
        super(aliases);
        registry = LootChestsPlus.INSTANCE.getLootChestRegistry();
    }

    @Override
    public boolean needConfirm() {
        return false;
    }

    @Override
    public boolean hasCooldown() {
        return false;
    }

    @Override
    public String getUsage() {
        return null;
    }

    @Override
    public String getConfirmMessage() {
        return null;
    }

    @Override
    public String getCooldownMessage() {
        return null;
    }

    @Override
    public boolean playerOnly() {
        return false;
    }

    @Override
    public boolean skipUsageWarn() {
        return true;
    }

    @Override
    public boolean onCommand(CommandSender s, String[] args) {
        if (!(s instanceof Player)) {
            LCPMessages.send(s, "%prefix% &7Sorry, this command can only be executed by players.", true);
            LCPMessages.send(s, "%prefix% &7You can still edit specific values of a lootchest using the command /lcp editValue <lootchest> <valueName> <newValue>.", true);
            return true;
        }

        if (args.length == 1) {
            // Open the editor gui
            return true;
        }

        String lootChestName = args[1];
        if (!registry.getLootChests().containsKey(lootChestName)) {
            LCPMessages.send(s, "%prefix% &cThe selected lootchest does not exist! Please type /lcp list to view a list of all available lootchests", true);
            return false;
        }

        LCPPlayer player = LootChestsPlus.INSTANCE.getPlayerRegistry().getPlayer((Player) s);
        player.setEditingLootChest(registry.getLootChests().get(lootChestName));
        return true;
    }

    @Override
    public List<String> tabCompletion(CommandSender s, String[] args) {
        return null;
    }
}