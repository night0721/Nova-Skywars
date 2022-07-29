package me.night0721.skywars.kit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class KitGUI {
    public KitGUI(Player player) {
        Inventory gui = Bukkit.createInventory(null, 9, ChatColor.GOLD.toString() + ChatColor.BOLD + "Kit Selection");
        for (KitType type : KitType.values()) {
            ItemStack i = new ItemStack(type.getMaterial());
            ItemMeta im = i.getItemMeta();
            im.setDisplayName(type.getDisplay());
            im.setLore(Arrays.asList(type.getDescription()));
            im.setLocalizedName(type.name());
            i.setItemMeta(im);
            gui.addItem(i);
        }
        player.openInventory(gui);
    }
}
