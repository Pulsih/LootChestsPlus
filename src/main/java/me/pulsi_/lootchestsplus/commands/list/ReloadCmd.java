package me.pulsi_.lootchestsplus.commands.list;

import me.pulsi_.lootchestsplus.LootChestsPlus;
import me.pulsi_.lootchestsplus.commands.LCPCommand;
import me.pulsi_.lootchestsplus.utils.LCPMessages;
import org.bukkit.command.CommandSender;

import java.util.List;

public class ReloadCmd extends LCPCommand {

    public ReloadCmd(String... aliases) {
        super(aliases);
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
        long start = System.currentTimeMillis();
        LCPMessages.send(s, "%prefix% &7Initializing reload task...", true);

        if (LootChestsPlus.INSTANCE.getData().reloadPlugin())
            LCPMessages.send(s, "%prefix% &2Successfully reloaded the plugin! &8(&3Took " + (System.currentTimeMillis() - start) + "ms&8)", true);
        else
            LCPMessages.send(s, "%prefix% &cSomething went wrong while reloading the plugin, check the console for more info.", true);

        return true;
    }

    @Override
    public List<String> tabCompletion(CommandSender s, String[] args) {
        return null;
    }
}