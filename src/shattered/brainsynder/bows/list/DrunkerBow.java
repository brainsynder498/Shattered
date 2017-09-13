package shattered.brainsynder.bows.list;

import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import shattered.brainsynder.bows.Bow;
import simple.brainsynder.api.ItemMaker;
import simple.brainsynder.api.ParticleMaker;
import simple.brainsynder.sound.SoundMaker;

import java.util.Collection;

public class DrunkerBow extends Bow {
    @Override
    public ItemStack getItem() {
        ItemMaker maker = new ItemMaker(Material.BOW);
        maker.setName("Drunker Bow");
        maker.addLoreLine("Intoxicates players near were it lands (nausea).");
        maker.enchant(Enchantment.ARROW_INFINITE, 1);
        return maker.create();
    }

    @Override
    public void onHit(Location location) {
        Collection<Entity> collection = location.getWorld().getNearbyEntities(location, 5, 5, 5);
        if (collection.isEmpty()) return;
        collection.forEach(entity -> {
            if (entity == null) return;
            if (entity instanceof Player) {
                Player player = (Player)entity;
                if (player.getGameMode() == GameMode.SPECTATOR) return;
                if (player.hasPotionEffect(PotionEffectType.CONFUSION)) return;
                SoundMaker.ENTITY_GENERIC_DRINK.playSound(location, 1.0f, 0.5f);

                ParticleMaker eggPop = new ParticleMaker(ParticleMaker.Particle.ITEM_CRACK, 30, 1.5);
                eggPop.setData(Material.EGG);
                eggPop.sendToLocation(location);

                ParticleMaker color = new ParticleMaker(ParticleMaker.Particle.SPELL_MOB, 15, Color.LIME);
                color.sendToLocation(location);

                player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 120, 2));
            }
        });
    }
}
