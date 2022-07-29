package me.night0721.skywars.managers;

import me.night0721.skywars.Skywars;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {
    private static FileConfiguration config;
    public static void setConfig(Skywars main) {
        ConfigManager.config = main.getConfig();
    }
    public static int getReqPlayers() {
        return config.getInt("requirement");
    }
    public static int getCountDownSecs() {
        return config.getInt("countdown");
    }
    public static Location getSpawnLocation() {
        return new Location(
                Bukkit.getWorld(config.getString("spawn.world")),
                config.getDouble("spawn.x"),
                config.getDouble("spawn.y"),
                config.getDouble("spawn.z"),
                (float) config.getDouble("spawn.yaw"),
                (float) config.getDouble("spawn.pitch")
        );
    }
}
