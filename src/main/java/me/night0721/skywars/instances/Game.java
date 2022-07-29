package me.night0721.skywars.instances;

import me.night0721.skywars.kit.KitType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class Game {

    private Arena arena;
    private HashMap<UUID, Integer> kills;

    public Game(Arena arena) {
        this.arena = arena;
        kills = new HashMap<>();
    }
    public void start() {
        arena.setState(GameState.LIVE);
        arena.sendMsgToAll(ChatColor.GREEN + "Game started! Kill everyone!");
        arena.sendTitleToAll("", "");
        for(UUID uuid : arena.getKits().keySet()) {
            arena.getKits().get(uuid).onStart(Bukkit.getPlayer(uuid));
        }
        for (Player player : Bukkit.getOnlinePlayers()) {
            UUID id = player.getUniqueId();
            kills.put(id, 0);
            Bukkit.getPlayer(id).closeInventory();
        }

    }
    public String centerText(String text, int lineLength) {
        StringBuilder builder = new StringBuilder();
        char space = ' ';
        int distance = (lineLength - text.length()) / 2;
        for (int i = 0; i < distance; ++i) {
            builder.append(space);
        }
        builder.append(text);
        for (int i = 0; i < distance; ++i) {
            builder.append(space);
        }
        return builder.toString();
    }
    public void addKill(Player Murderer, Player Victim) {
        int playerkills = kills.get(Murderer.getUniqueId()) + 1;
        if(arena.getKits().get(Murderer.getUniqueId()).getUserKitType(Murderer.getUniqueId()) != null) {
            KitType a = arena.getKits().get(Murderer.getUniqueId()).getUserKitType(Murderer.getUniqueId());
            arena.sendMsgToAll(ChatColor.RED + Victim.getName() + " has been murdered by " + a.getColor() + Murderer.getName() + "(" + a.getDisplay() + ")");
        } else {
            arena.sendMsgToAll(ChatColor.RED + Victim.getName() + " has been murdered by " + Murderer.getName());
        }

        if(playerkills == Bukkit.getOnlinePlayers().size() - 1) {
            Murderer.sendTitle(ChatColor.GOLD + "Victory!", ChatColor.GRAY + "You are the last man standing!", 0, 80, 0);
            arena.sendMsgToAll(ChatColor.GREEN + "====================================================\n" + centerText(ChatColor.BOLD + "SkyWars", 52) + centerText(ChatColor.YELLOW + "Winner - " + Murderer.getName(), 52));
            Murderer.sendMessage(ChatColor.GREEN + "You won!" + ChatColor.YELLOW + " Want to play again? " + ChatColor.AQUA + "Click here!");
            arena.reset();
            return;
        }
        kills.replace(Murderer.getUniqueId(), playerkills);
    }
}