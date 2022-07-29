package me.night0721.skywars.kit;

import org.bukkit.ChatColor;
import org.bukkit.Material;

public enum KitType {
    VALKYRIE(ChatColor.GOLD + "Valkyrie", Material.IRON_SWORD, "Valkyrie kills everyone", ChatColor.GOLD.toString()),
    THOR(ChatColor.YELLOW + "Thor", Material.DIAMOND_AXE, "Guy with hammer", ChatColor.YELLOW.toString()),
    CUPID(ChatColor.RED + "Cupid", Material.BOW, "Connect every lover", ChatColor.RED.toString()),
    WOLF(ChatColor.GRAY + "Wolf", Material.FLINT_AND_STEEL, "Kills villager", ChatColor.GRAY.toString()),
    PROPHET(ChatColor.BLUE + "Prophet", Material.KNOWLEDGE_BOOK, "He knows everything", ChatColor.BLUE.toString()),
    VILLAGER(ChatColor.AQUA + "Villager", Material.EMERALD, "mhmm.. huh?", ChatColor.AQUA.toString());
    private String display;
    private Material material;
    private String description;
    private String color;
    KitType(String display, Material material, String description, String color) {
        this.display = display;
        this.material = material;
        this.description = description;
        this.color = color;
    }
    public String getDisplay() { return display; }
    public Material getMaterial() { return material; }
    public String getDescription() { return description; }
    public String getColor() { return color; }
}
