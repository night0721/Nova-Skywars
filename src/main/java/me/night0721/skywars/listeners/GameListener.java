package me.night0721.skywars.listeners;

import me.night0721.skywars.instances.Arena;
import me.night0721.skywars.instances.GameState;
import me.night0721.skywars.Skywars;
import me.night0721.skywars.kit.KitType;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.world.WorldUnloadEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static me.night0721.skywars.instances.Arena.copyWorld;
import static me.night0721.skywars.instances.Arena.deleteWorldFiles;

public class GameListener implements Listener {
    private Skywars main;
    public GameListener(Skywars main) {
        this.main = main;
    }

    @EventHandler
    public void onKill(EntityDamageByEntityEvent e) {
        if(e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
            Arena arena = main.getArena();
            if(arena.getState().equals(GameState.LIVE)) {
                arena.getGame().addKill((Player) e.getDamager(), (Player) e.getEntity());
            }
        }
    }
    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if(e.getView().getTitle().contains("Kit Selection") && e.getInventory() != null && e.getCurrentItem() != null) {
            e.setCancelled(true);
            KitType type = KitType.valueOf(e.getCurrentItem().getItemMeta().getLocalizedName());
            Arena arena = main.getArena();
            if(arena != null) {
                KitType activated = arena.getKitType(player);
                if(activated != null && activated == type) {
                    player.sendMessage(ChatColor.RED + "You have already equipped this kit");
                } else {
                    player.sendMessage(ChatColor.GREEN + "You have equipped " + type.getDisplay() + ChatColor.GREEN + " kit");
                    arena.setKit(player.getUniqueId(), type);
                }
            }
            player.closeInventory();
        }
    }
    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        Player player = (Player) e.getPlayer();
        if(e.getView().getTitle().contains("Kit Selection")) {
            Arena arena = main.getArena();
            if(arena != null) {
                if(arena.getState() == GameState.COUNTINGDOWN) {
                    KitType activated = arena.getKitType(player);
                    if(activated == null) {
                        Bukkit.getScheduler().runTaskLater(main, () -> {
                            player.openInventory(e.getInventory());
                        }, 1L);
                    }
                }
            }
        }
    }
    @EventHandler
    public void onWorldLoad(WorldLoadEvent e) {
        Arena arena = main.getArena();
        if (arena != null) {
            arena.toggleJoin();
        }
    }
    @EventHandler
    public void onWorldUnload(WorldUnloadEvent e) {
        String name = e.getWorld().getName();
        deleteWorldFiles(Bukkit.getWorld(name).getWorldFolder());
        System.out.println("done delete world");
        List<String> splitted = new ArrayList<>(Arrays.asList(Bukkit.getWorld("baseskywars").getWorldFolder().getAbsolutePath().split(("\\\\"))));
        splitted.remove(3);
        splitted.add("skywars");
        Bukkit.createWorld(new WorldCreator(name));
        copyWorld(Bukkit.getWorld("baseskywars").getWorldFolder(), new File(String.join("\\\\", splitted)));

    }

}
