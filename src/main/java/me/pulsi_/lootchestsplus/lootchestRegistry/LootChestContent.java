package me.pulsi_.lootchestsplus.lootchestRegistry;

import me.pulsi_.lootchestsplus.utils.LCPLogger;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class LootChestContent {

    private final LootChestItem[] items = {};

    public LootChestItem getItem(int position) {
        return items[position];
    }

    public void setItem(int position, LootChestItem item) {
        items[position] = item;
    }

    public void loadItems(LootChest lootChest, ConfigurationSection itemsSection) {
        if (itemsSection == null) return;

        int count = 0, notLoadedItems = 0;
        for (String key : itemsSection.getKeys(false)) {
            ConfigurationSection values = itemsSection.getConfigurationSection(key);
            if (values == null) continue;

            LootChestItem item = new LootChestItem();

            ItemStack itemStack = values.getItemStack("item");
            if (itemStack == null) {
                LCPLogger.warn("Could not load the item in slot \"" + key + "\" in the lootchest \"" + lootChest.getName() + "\".");
                notLoadedItems++;
                continue;
            }
            item.setItem(itemStack);

            double chance = 100D;
            String sChance = values.getString("chance");
            if (sChance != null) {
                try {
                    chance = Double.parseDouble(sChance);
                } catch (NumberFormatException e) {
                    LCPLogger.warn("The lootchest \"" + lootChest.getName() + "\", in the item slot \"" + key + "\" does not specify a valid chance number: " + sChance + " (Using 100% as default)");
                }
            }
            item.setChance(chance);

            item.setRandomPosition(values.getBoolean("random-position"));

            items[count] = item;
            count++;
        }

        if (notLoadedItems > 0) LCPLogger.warn("Could not load " + notLoadedItems + " items in the lootchest \"" + lootChest.getName() + "\" (Did you manually configured the lootchest config?)");
    }

    public static class LootChestItem {

        private ItemStack item;
        private double chance;
        private boolean randomPosition;

        public ItemStack getItem() {
            return item;
        }

        public double getChance() {
            return chance;
        }

        public boolean isRandomPosition() {
            return randomPosition;
        }

        public void setItem(ItemStack item) {
            this.item = item;
        }

        public void setChance(double chance) {
            this.chance = chance;
        }

        public void setRandomPosition(boolean randomPosition) {
            this.randomPosition = randomPosition;
        }
    }
}