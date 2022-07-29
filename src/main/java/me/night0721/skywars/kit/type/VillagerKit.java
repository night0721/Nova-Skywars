package me.night0721.skywars.kit.type;

import me.night0721.skywars.Skywars;
import me.night0721.skywars.kit.Kit;
import me.night0721.skywars.kit.KitType;
import org.bukkit.entity.Player;

import java.util.UUID;

public class VillagerKit extends Kit {
    public VillagerKit(Skywars main, UUID uuid) {
        super(main, KitType.VILLAGER, uuid);
    }

    @Override
    public void onStart(Player player) {

    }
}
