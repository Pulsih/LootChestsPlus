package me.pulsi_.lootchestsplus.utils;

import org.bukkit.Bukkit;

public class LCPLogger {

    public static void error(Exception e, String error) {
        if (error == null) error = "null";

        error(error);
        error("");
        error("Additional information:");
        error("Error message: " + e.getMessage());
        error("Involved classes:");
        for (StackTraceElement stackTraceElement : e.getStackTrace()) {
            String iClass = stackTraceElement.getClassName();
            if (!iClass.contains("me.pulsi_.lootchestplus")) continue;

            int line = stackTraceElement.getLineNumber();
            String method = stackTraceElement.getMethodName();
            error("  * " + iClass + " (Line: " + line + ") [Method: " + method + "()]");
        }
    }

    public static void error(String error) {
        if (error == null) error = "null";
        log(LCPChat.prefix + " &8[&cERROR&8] &c" + error);
    }

    public static void warn(String warn) {
        if (warn == null) warn = "null";
        log(LCPChat.prefix + " &8[&eWARN&8] &e" + warn);
    }

    public static void warn(Exception e, String warn) {
        if (warn == null) warn = "null";

        warn(warn);
        warn("");
        warn("Additional information:");
        warn("Error message: " + e.getMessage());
        warn("Involved classes:");
        for (StackTraceElement stackTraceElement : e.getStackTrace()) {
            String iClass = stackTraceElement.getClassName();
            if (!iClass.contains("me.pulsi_.lootchestplus")) continue;

            int line = stackTraceElement.getLineNumber();
            String method = stackTraceElement.getMethodName();
            warn("  * " + iClass + " (Line: " + line + ") [Method: " + method + "()]");
        }
    }

    public static void info(String info) {
        if (info == null) info = "null";
        log(LCPChat.prefix + " &8[&9INFO&8] &9" + info);
    }

    public static void log(String message) {
        Bukkit.getConsoleSender().sendMessage(LCPMessages.addPrefix(message));
    }
}