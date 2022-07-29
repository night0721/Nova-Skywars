package me.night0721.skywars.commands;

import me.night0721.skywars.Skywars;
import me.night0721.skywars.instances.Arena;
import me.night0721.skywars.instances.GameState;
import me.night0721.skywars.kit.KitGUI;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KitCommand implements CommandExecutor {
    private Skywars main;
    public KitCommand(Skywars main) {
        this.main = main;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(args.length == 1 && args[0].equalsIgnoreCase("kit")){
                Arena arena = main.getArena();
                if(arena != null) {
                    if(arena.getState() == GameState.LIVE) {
                        player.sendMessage(ChatColor.RED + "That's too late to select a kit");
                    } else if(arena.getState() == GameState.COUNTINGDOWN) {
                        new KitGUI(player);
                    }else {
                        player.sendMessage(ChatColor.RED + "You can't select a kit yet");
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "You aren't in a game");
                }
            }
        }
        return false;
    }
}
