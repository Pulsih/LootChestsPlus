package me.pulsi_.lootchestsplus.utils;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.math.BigDecimal;

public class LCPUtils {

    /*public static String formatTime(long milliseconds) {
        long seconds = milliseconds / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        String format = Values.CONFIG.getInterestTimeFormat();
        String[] parts = format.split("%");
        int amount = parts.length;

        for (int i = 1; i < amount; i++) {
            String identifier = parts[i];
            long time = 0;

            switch (identifier) {
                case "s":
                    time = seconds - (minutes * 60);
                    break;
                case "m":
                    time = minutes - (hours * 60);
                    break;
                case "h":
                    time = hours - (days * 24);
                    break;
                case "d":
                    time = days;
                    break;
            }
            if (time <= 0) format = format.replace("%" + identifier, "");
        }

        parts = format.split("%");
        amount = parts.length;

        for (int i = 1; i < amount; i++) {
            String identifier = parts[i], timeIdentifier = "";
            long time = 0;

            switch (identifier) {
                case "s":
                    time = seconds - (minutes * 60);
                    timeIdentifier = "seconds";
                    break;
                case "m":
                    time = minutes - (hours * 60);
                    timeIdentifier = "minutes";
                    break;
                case "h":
                    time = hours - (days * 24);
                    timeIdentifier = "hours";
                    break;
                case "d":
                    time = days;
                    timeIdentifier = "days";
                    break;
            }

            int last = i + 2;
            String separator = last > amount ? "" : last == amount ? Values.CONFIG.getInterestTimeFinalSeparator() : Values.CONFIG.getInterestTimeSeparator();
            String replacer = time <= 0 ? "" : time + getTimeIdentifier(timeIdentifier, time) + separator;

            format = format.replace("%" + identifier, replacer);
        }

        return format;
    }

    private static String getTimeIdentifier(String id, long time) {
        switch (id) {
            case "seconds":
                return time == 1 ? Values.CONFIG.getSecond() : Values.CONFIG.getSeconds();
            case "minutes":
                return time == 1 ? Values.CONFIG.getMinute() : Values.CONFIG.getMinutes();
            case "hours":
                return time == 1 ? Values.CONFIG.getHour() : Values.CONFIG.getHours();
            case "days":
                return time == 1 ? Values.CONFIG.getDay() : Values.CONFIG.getDays();
        }
        return "";
    }*/

    public static boolean isPlayer(CommandSender s) {
        if (s instanceof Player) return true;
        LCPMessages.send(s, "not_player");
        return false;
    }

    public static boolean hasPermission(CommandSender s, String permission) {
        if (s.hasPermission(permission)) return true;
        LCPMessages.send(s, "no_permission", "%permission%$" + permission);
        return false;
    }

    public static void sendTitle(String title, Player p) {
        if (title == null || p == null) return;

        title = LCPMessages.addPrefix(title);
        if (title.contains(",")) {
            String[] titles = title.split(",");
            String title1 = titles[0], title2 = titles[1];

            if (titles.length == 2) p.sendTitle(title1, title2);
            else {
                int[] values = {20, 20, 20};
                boolean error = false;

                for (int i = 2; i < titles.length; i++) {
                    try {
                        values[i - 2] = Integer.parseInt(titles[i]);
                    } catch (NumberFormatException e) {
                        error = true;
                    }
                }
                if (error)
                    LCPLogger.warn("Invalid number in the title fades values! Please correct it as soon as possible! (Title: " + title + "&a)");

                try {
                    p.sendTitle(title1, title2, values[0], values[1], values[2]);
                } catch (NoSuchMethodError e) {
                    p.sendTitle(title1, title2);
                }
            }
        } else p.sendTitle(title, "");
    }

    public static long secondsInMilliseconds(int seconds) {
        return seconds * 1000L;
    }

    public static long minutesInMilliseconds(int minutes) {
        return minutes * secondsInMilliseconds(60);
    }

    public static long hoursInMilliseconds(int hours) {
        return hours * minutesInMilliseconds(60);
    }

    public static long daysInMilliseconds(int days) {
        return days * hoursInMilliseconds(24);
    }

    public static int millisecondsInTicks(long milliseconds) {
        return (int) ((milliseconds / 1000) * 20);
    }

    public static long ticksInMilliseconds(int ticks) {
        return (ticks / 20) * 1000;
    }
}