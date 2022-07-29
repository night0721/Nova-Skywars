package me.night0721.skywars.kit.type;

import me.night0721.skywars.Skywars;
import me.night0721.skywars.kit.Kit;
import me.night0721.skywars.kit.KitType;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

public class ProphetKit extends Kit {
    public ProphetKit(Skywars main, UUID uuid) {
        super(main, KitType.PROPHET, uuid);
    }

    @Override
    public void onStart(Player player) {
        ItemStack item = new ItemStack(Material.BOW);
        item.addEnchantment(Enchantment.QUICK_CHARGE, 5);
        player.getInventory().addItem(item);
        player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 1000, 5));
    }
    // Maybe add some event here to show special ability of that kit?
}
