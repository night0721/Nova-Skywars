package me.night0721.skywars.kit;

import me.night0721.skywars.Skywars;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.UUID;

public abstract class Kit implements Listener {
    private KitType type;
    private UUID uuid;
    private HashMap<UUID, KitType> kits = new HashMap<>();
    public Kit(Skywars main, KitType type, UUID uuid) {
        this.type = type;
        this.uuid = uuid;
        kits.put(uuid, type);
        Bukkit.getPluginManager().registerEvents(this, main);
    }

    public KitType getType() {
        return type;
    }

    public UUID getUUID() {
        return uuid;
    }

    public KitType getUserKitType(UUID uuid) {
        return kits.get(uuid);
    }
    public abstract void onStart(Player player);
    public void remove() {
        HandlerList.unregisterAll(this);
    }
}
