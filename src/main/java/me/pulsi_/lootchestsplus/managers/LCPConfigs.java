package me.pulsi_.lootchestsplus.managers;

import me.pulsi_.lootchestsplus.LootChestsPlus;
import me.pulsi_.lootchestsplus.utils.LCPLogger;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.util.FileUtil;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class LCPConfigs {

    private static boolean updated = false;
    private boolean autoUpdateFiles = false;

    private final LootChestsPlus plugin;

    public LCPConfigs(LootChestsPlus plugin) {
        this.plugin = plugin;
    }

    public void setupConfigs() {
        setupSavesFile();
        setupConfig();
        setupMessages();
    }

    public void setupSavesFile() {
        File file = new File(plugin.getDataFolder(), "saves.yml");
        if (!file.exists()) {
            try {
                file.getParentFile().mkdir();
                file.createNewFile();
            } catch (IOException e) {
                LCPLogger.error(e, "Failed to to create the saves.yml file! " + e.getMessage());
            }
            updated = false;
        }

        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        if (updated) {
            String v = config.getString("version");
            updated = v != null && v.equals(plugin.getDescription().getVersion());
        }

        config.options().header("DO NOT EDIT / REMOVE THIS FILE OR LOOTCHESTPLUS MAY GET RESET!");
        config.set("version", plugin.getDescription().getVersion());

        try {
            config.save(file);
        } catch (IOException e) {
            LCPLogger.error(e, "Could not save \"saves\" file! (Error: " + e.getMessage().replace("\n", "") + ")");
        }
    }

    public void setupConfig() {
        String name = "config.yml";
        boolean updateFile = true, alreadyExist = getFile(name).exists();
        if (alreadyExist) {
            FileConfiguration config = getConfig(name);

            autoUpdateFiles = config.get("General-Settings.Auto-Update-Files") == null || config.getBoolean("General-Settings.Auto-Update-Files");
            updateFile = !updated && autoUpdateFiles;
        }

        if (updateFile) setupFile(name, alreadyExist);
    }

    public void setupMessages() {
        String name = "messages.yml";
        boolean updateFile = true, alreadyExist = getFile(name).exists();
        if (alreadyExist) updateFile = !updated && autoUpdateFiles;

        if (updateFile) setupFile(name, alreadyExist);
    }

    public File getFile(String path) {
        return new File(plugin.getDataFolder(), path);
    }

    public FileConfiguration getConfig(String path) {
        return YamlConfiguration.loadConfiguration(getFile(path));
    }

    public FileConfiguration getConfig(File file) {
        return YamlConfiguration.loadConfiguration(file);
    }

    public void setupFile(String fileName, boolean backup) {
        File folderFile = getFile(fileName);
        if (!folderFile.exists()) {
            plugin.saveResource(fileName, true);
            return;
        }

        if (backup) {
            File copyFile = new File(plugin.getDataFolder(), "backups" + File.separator + fileName);
            if (!copyFile.exists()) {
                try {
                    copyFile.getParentFile().mkdirs();
                    copyFile.createNewFile();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            FileUtil.copy(folderFile, copyFile);
        }

        HashMap<Integer, FileLine> file = new HashMap<>();
        int positions = 1;

        List<String> fileAsList = new ArrayList<>();

        File fileToScan;
        try {
            fileToScan = File.createTempFile(fileName, null);
            fileToScan.deleteOnExit();
        } catch (IOException e) {
            LCPLogger.warn(e, "Could not load \"" + fileName + "\" file!");
            return;
        }

        FileOutputStream out;
        try {
            out = new FileOutputStream(fileToScan);
        } catch (FileNotFoundException e) {
            LCPLogger.warn(e, "Could not load \"" + fileName + "\" file!");
            return;
        }
        copyStream(plugin.getResource(fileName), out);

        Scanner scanner;
        try {
            scanner = new Scanner(fileToScan, "UTF-8");
        } catch (FileNotFoundException e) {
            LCPLogger.warn(e, "Could not find \"" + fileName + "\" file!");
            return;
        }
        while (scanner.hasNext()) fileAsList.add(scanner.nextLine());

        for (int i = 0; i < fileAsList.size(); i++) {
            String line = fileAsList.get(i);
            if (isListContent(line)) continue;

            if (!line.isEmpty() && !isComment(line) && line.contains(":")) {
                String[] split = line.split(":");

                boolean isValue = split.length > 1 && !isComment(split[1]);
                boolean isHeader = split.length == 1 || isComment(split[1]);
                boolean isList = isHeader && i + 1 < fileAsList.size() && isListContent(fileAsList.get(i + 1));
                file.put(positions, new FileLine(line, isValue, isHeader, isList));
                positions++;
                continue;
            }

            file.put(positions, new FileLine(line, false, false, false));
            positions++;
        }

        YamlConfiguration folderConfig = YamlConfiguration.loadConfiguration(folderFile), jarConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(plugin.getResource(fileName), StandardCharsets.UTF_8));

        boolean hasChanges = false;
        for (String key : jarConfig.getKeys(true)) {
            if (folderConfig.get(key) != null) continue;

            folderConfig.set(key, jarConfig.get(key));
            hasChanges = true;
        }
        if (!hasChanges) return;

        StringBuilder builder = new StringBuilder();
        HashMap<Integer, String> headers = new HashMap<>();

        for (int pos = 1; pos < positions; pos++) {
            FileLine fileLine = file.get(pos);

            String line = fileLine.getLine();
            if (!fileLine.isValue() && !fileLine.isHeader()) {
                builder.append(line).append("\n");
                continue;
            }

            int spaces = 0;
            for (char c : line.toCharArray()) {
                if (c == ' ') spaces++;
                else break;
            }

            int point = spaces / 2;
            String identifier = line.substring(spaces).split(":")[0];
            if (fileLine.isHeader()) headers.put(point, identifier);

            if (fileLine.isValue()) {
                StringBuilder path = new StringBuilder();

                for (int i = 0; i <= point - 1; i++) {
                    String header = headers.get(i);
                    if (header != null) path.append(header).append(".");
                }
                path.append(identifier);

                for (int i = 0; i < spaces; i++) builder.append(" ");
                builder.append(identifier).append(": ");

                Object value = folderConfig.get(path.toString());
                if (value instanceof String) builder.append("\"").append(value).append("\"\n");
                else builder.append(value).append("\n");
                continue;
            }

            if (fileLine.isList()) {
                StringBuilder path = new StringBuilder();

                for (int i = 0; i <= point - 1; i++) {
                    String header = headers.get(i);
                    if (header != null) path.append(header).append(".");
                }
                path.append(identifier);

                for (int i = 0; i < spaces; i++) builder.append(" ");
                builder.append(identifier).append(":");

                List<String> value = folderConfig.getStringList(path.toString());

                if (value.isEmpty()) builder.append(" []\n");
                else {
                    builder.append("\n");
                    for (String listLine : value) {
                        for (int i = 0; i < spaces; i++) builder.append(" ");
                        builder.append("- \"").append(listLine).append("\"\n");
                    }
                }
                continue;
            }

            builder.append(line).append("\n");
        }
        recreateFile(folderFile, builder.toString());
    }

    public void recreateFile(File file, String fileBuilder) {
        if (fileBuilder == null) return;
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(fileBuilder);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            LCPLogger.error(e, e.getMessage());
        }
    }

    public static boolean isUpdated() {
        return updated;
    }

    private boolean isComment(String s) {
        return s.replace(" ", "").startsWith("#");
    }

    private boolean isListContent(String s) {
        return s.replace(" ", "").startsWith("-");
    }

    private void copyStream(InputStream in, OutputStream out) {
        try {
            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1)
                out.write(buffer, 0, read);
        } catch (Exception e) {
            LCPLogger.warn(e, "Could not copy stream!");
        }
    }

    private static class FileLine {

        private final String line;
        private final boolean isValue, isHeader, isList;

        public FileLine(String line, boolean isValue, boolean isHeader, boolean isList) {
            this.line = line;
            this.isValue = isValue;
            this.isHeader = isHeader;
            this.isList = isList;
        }

        public String getLine() {
            return line;
        }

        public boolean isValue() {
            return isValue;
        }

        public boolean isHeader() {
            return isHeader;
        }

        public boolean isList() {
            return isList;
        }
    }
}