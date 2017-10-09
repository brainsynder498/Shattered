package shattered.brainsynder.bows.list;

import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import shattered.brainsynder.Shattered;
import shattered.brainsynder.bows.Bow;
import simple.brainsynder.api.ItemMaker;
import simple.brainsynder.api.ParticleMaker;
import simple.brainsynder.sound.SoundMaker;

import java.util.Collection;

public class DrunkerBow extends Bow {
    public DrunkerBow(Shattered shattered) {
        super(shattered, "Drunker");
    }

    @Override
    public ItemStack getItem() {
        ItemMaker maker = new ItemMaker(Material.BOW);
        maker.setName("Drunker Bow");
        maker.addLoreLine("Intoxicates players near were it lands (nausea).");
        maker.enchant(Enchantment.ARROW_INFINITE, 1);
        ItemStack stack = maker.create();
        stack.setDurability((short) getMaxUses());
        return stack;
    }

    @Override
    public void onHit(Location location) {
        Collection<Entity> collection = location.getWorld().getNearbyEntities(location, 5, 5, 5);
        if (collection.isEmpty()) return;
        collection.forEach(entity -> {
            if (entity == null) return;
            if (entity instanceof Player) {
                Player player = (Player)entity;

                ParticleMaker eggPop = new ParticleMaker(ParticleMaker.Particle.ITEM_CRACK, 120, 0.5, 1.0, 0.5);
                eggPop.setData(Material.EGG);
                eggPop.sendToLocation(location);

                ParticleMaker color = new ParticleMaker(ParticleMaker.Particle.SPELL_MOB_AMBIENT, 100, Color.LIME);
                color.sendToLocation(location);

                if (player.getGameMode() == GameMode.SPECTATOR) return;
                if (player.hasPotionEffect(PotionEffectType.CONFUSION)) return;
                SoundMaker.ENTITY_GENERIC_SPLASH.playSound(location, 1.0f, 1.0f);
                player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 120, 2));
            }
        });
    }
}
