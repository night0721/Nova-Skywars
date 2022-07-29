package me.night0721.skywars.utils;

import net.md_5.bungee.api.ChatColor;

public enum CustomColor {
    PINK(ChatColor.of("#ff1493").toString());
    private String display;
    CustomColor(String display) {
        this.display = display;
    }
}
