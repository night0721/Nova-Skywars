package me.night0721.skywars;

import me.night0721.skywars.commands.KitCommand;
import me.night0721.skywars.instances.Arena;
import me.night0721.skywars.listeners.ConnectListener;
import me.night0721.skywars.listeners.GameListener;
import me.night0721.skywars.managers.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.java.JavaPlugin;

public final class Skywars extends JavaPlugin {
    private Arena arena;
    @Override
    public void onEnable() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        ConfigManager.setConfig(this);
        Bukkit.createWorld(new WorldCreator("baseskywars")).setAutoSave(false);
        arena = new Arena(this);
        Bukkit.getPluginManager().registerEvents(new GameListener(this), this);
        Bukkit.getPluginManager().registerEvents(new ConnectListener(this), this);
        getCommand("kit").setExecutor(new KitCommand(this));
    }

    public Arena getArena() {
        return arena;
    }

    }
