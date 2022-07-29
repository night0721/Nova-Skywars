package me.night0721.skywars.listeners;

import me.night0721.skywars.instances.GameState;
import me.night0721.skywars.managers.ConfigManager;
import me.night0721.skywars.Skywars;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectListener implements Listener {
    private Skywars main;
    public ConnectListener(Skywars main) {
        this.main = main;
    }
    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        if(main.getArena().getState() == GameState.LIVE || main.getArena().getState() == GameState.COUNTINGDOWN || Bukkit.getServer().getOnlinePlayers().size() >= ConfigManager.getReqPlayers()) {
            e.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.RED + "You can't join the game now as it has been started");
        }
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.getPlayer().teleport(ConfigManager.getSpawnLocation());
        main.getArena().addPlayer();
    }
    @EventHandler
    public void onQuit(PlayerQuitEvent e) { main.getArena().removePlayer(e.getPlayer()); }
}
