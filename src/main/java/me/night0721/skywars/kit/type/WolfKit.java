package me.night0721.skywars.kit.type;

import me.night0721.skywars.Skywars;
import me.night0721.skywars.kit.Kit;
import me.night0721.skywars.kit.KitType;
import org.bukkit.entity.Player;

import java.util.UUID;

public class WolfKit extends Kit {
    public WolfKit(Skywars main, UUID uuid) {
        super(main, KitType.WOLF, uuid);
    }

    @Override
    public void onStart(Player player) {

    }
}
