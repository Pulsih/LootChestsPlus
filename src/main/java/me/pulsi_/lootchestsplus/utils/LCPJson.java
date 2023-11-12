package me.pulsi_.lootchestsplus.utils;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class LCPJson {

    private final TextComponent text;

    public LCPJson() {
        this.text = new TextComponent("");
    }

    public LCPJson(String text) {
        this.text = new TextComponent(LCPMessages.addPrefix(text));
    }

    public LCPJson setClickAction(ClickEvent.Action action, String value) {
        text.setClickEvent(new ClickEvent(action, value));
        return this;
    }

    public LCPJson setHoverAction(HoverEvent.Action action, String value) {
        text.setHoverEvent(new HoverEvent(action, new ComponentBuilder(value).color(ChatColor.GRAY).create()));
        return this;
    }

    public LCPJson addText(TextComponent text) {
        this.text.addExtra(text);
        return this;
    }

    public LCPJson send(Player p) {
        p.spigot().sendMessage(text);
        return this;
    }

    public TextComponent getText() {
        return text;
    }
}