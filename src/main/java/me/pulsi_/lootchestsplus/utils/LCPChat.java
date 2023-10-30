package me.pulsi_.lootchestsplus.utils;

import me.pulsi_.lootchestsplus.LootChestsPlus;
import net.md_5.bungee.api.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LCPChat {

    public final static String prefix = "&4&lL&c&lC&a&lP", longPrefix = "&4&lLoot&c&lChest&a&lPlus";

    private final static Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");

    public static String color(String message) {
        if (LootChestsPlus.INSTANCE.getServerVersionInt() >= 16) {
            Matcher matcher = pattern.matcher(message);

            while (matcher.find()) {
                String color = message.substring(matcher.start(), matcher.end());
                message = message.replace(color, ChatColor.of(color) + "");
                matcher = pattern.matcher(message);
            }
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}