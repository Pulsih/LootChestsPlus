package me.pulsi_.lootchestsplus.commands;

import me.pulsi_.lootchestsplus.LootChestsPlus;
import me.pulsi_.lootchestsplus.utils.LCPChat;
import me.pulsi_.lootchestsplus.utils.LCPMessages;
import me.pulsi_.lootchestsplus.utils.LCPUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class MainCmd implements CommandExecutor, TabCompleter {

    public static final LinkedHashMap<String, LCPCommand> commands = new LinkedHashMap<>();
    private final String permPrefix = "lootchestsplus";

    public boolean onCommand(CommandSender s, Command command, String label, String[] args) {

        if (args.length == 0) {
            if (LCPUtils.hasPermission(s, permPrefix + ".admin"))
                LCPMessages.send(s, "%prefix% &7Running " + LCPChat.longPrefix + " &7on &fv" + LootChestsPlus.INSTANCE.getDescription().getVersion() + "&7!", true);
            return true;
        }
        String identifier = args[0].toLowerCase();

        if (!commands.containsKey(identifier)) {
            LCPMessages.send(s, "unknown_command");
            return true;
        }

        LCPCommand cmd = commands.get(identifier);
        cmd.execute(s, args);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender s, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> cmds = new ArrayList<>();
            for (LCPCommand cmd : commands.values())
                if (s.hasPermission(permPrefix + "." + cmd.getIdentifier().toLowerCase())) cmds.add(cmd.getIdentifier());

            List<String> args0 = new ArrayList<>();
            for (String arg : cmds)
                if (arg.toLowerCase().startsWith(args[0].toLowerCase())) args0.add(arg);

            return args0;
        }

        String identifier = args[0].toLowerCase();
        if (!commands.containsKey(identifier)) return null;

        LCPCommand cmd = commands.get(identifier);
        if (cmd.playerOnly() && !(s instanceof Player)) return null;

        return s.hasPermission(permPrefix + "." + identifier) ? cmd.tabCompletion(s, args) : null;
    }
}