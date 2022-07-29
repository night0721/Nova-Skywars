package me.night0721.skywars.instances;

import me.night0721.skywars.Skywars;
import me.night0721.skywars.kit.KitGUI;
import me.night0721.skywars.managers.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CountDown extends BukkitRunnable {
    private Skywars main;
    private Arena arena;
    private int countdownsecs;

    public CountDown(Skywars main, Arena arena) {
        this.main = main;
        this.arena = arena;
        this.countdownsecs = ConfigManager.getCountDownSecs();
    }
    public void start() {
        arena.setState(GameState.COUNTINGDOWN);
        for (Player player : Bukkit.getOnlinePlayers()) {
            arena.teleportPlayerToGameSpawns();
            player.sendMessage(ChatColor.RED + "Choose your kit");
            new KitGUI(player);
        }

        runTaskTimer(main, 0L, 20L);
    }
    @Override
    public void run() {
        if(countdownsecs == 0) {
            cancel();
            arena.start();
            return;
        }
        if(countdownsecs % 10 == 0 || countdownsecs <= 5) {
            arena.sendMsgToAll(ChatColor.YELLOW + "The game starts in " + (countdownsecs <= 5 ? ChatColor.RED : ChatColor.GOLD) + countdownsecs + ChatColor.YELLOW + " second" + (countdownsecs == 1 ? "" : "s"));
            arena.sendTitleToAll(ChatColor.GREEN.toString() + "Game starts in " + countdownsecs + " second" + (countdownsecs == 1 ? "" : "s"), ChatColor.GRAY + "Get ready to fight!");
        }
        countdownsecs--;
    }
}
