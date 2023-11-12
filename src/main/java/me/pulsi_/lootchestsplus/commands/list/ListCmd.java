package me.pulsi_.lootchestsplus.commands.list;

import me.pulsi_.lootchestsplus.LootChestsPlus;
import me.pulsi_.lootchestsplus.commands.LCPCommand;
import me.pulsi_.lootchestsplus.lootchestRegistry.LootChestRegistry;
import me.pulsi_.lootchestsplus.utils.LCPJson;
import me.pulsi_.lootchestsplus.utils.LCPMessages;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ListCmd extends LCPCommand {

    private final LootChestRegistry registry;

    public ListCmd(String... aliases) {
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
        LCPMessages.send(s, "%prefix% &2Here you have a list of all available lootchests!", true);
        if (s instanceof Player) {
            LCPJson message = new LCPJson("&8* &f&n");
            List<String> names = new ArrayList<>(registry.getLootChests().keySet());

            for (int i = 0; i < names.size(); i++) {
                String name = names.get(i);
                LCPJson lootchest = new LCPJson(name);

                lootchest.setClickAction(ClickEvent.Action.RUN_COMMAND, "lcp edit " + name);
                lootchest.setHoverAction(HoverEvent.Action.SHOW_TEXT, "Left-Click to edit!");

                message.addText(lootchest.getText());

                if (i + 1 < names.size()) message.addText(new LCPJson("&8, &8* &f&n").getText());
                else message.addText(new LCPJson("&8.").getText());
            }
            message.send((Player) s);

        } else {
            StringBuilder builder = new StringBuilder("&8* &f&n");
            List<String> names = new ArrayList<>(registry.getLootChests().keySet());

            for (int i = 0; i < names.size(); i++) {
                builder.append(names.get(i));

                if (i + 1 < names.size()) builder.append("&8, &8* &f&n");
                else builder.append("&8.");
            }
            LCPMessages.send(s, builder.toString(), true);
        }

        return true;
    }

    @Override
    public List<String> tabCompletion(CommandSender s, String[] args) {
        return null;
    }
}