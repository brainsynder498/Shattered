package shattered.brainsynder.bows.list;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;
import shattered.brainsynder.Shattered;
import shattered.brainsynder.bows.Bow;
import shattered.brainsynder.listeners.BowListener;
import simple.brainsynder.api.ItemMaker;
import simple.brainsynder.api.ParticleMaker;
import simple.brainsynder.math.MathUtils;

public class ExplosiveBow extends Bow {
    public ExplosiveBow(Shattered shattered) {
        super(shattered, "Drunker");
    }

    @Override
    public ItemStack getItem() {
        ItemMaker maker = new ItemMaker(Material.BOW);
        maker.setName("Explosive Bow");
        maker.addLoreLine("Explodes on impact into arrow shrapnel.");
        maker.enchant(Enchantment.ARROW_INFINITE, 1);
        ItemStack stack = maker.create();
        stack.setDurability((short) getMaxUses());
        return stack;
    }

    @Override
    public void onHit(Location location) {
        BowListener listener = getShattered().getModuleManager().getModule("BowListener");
        location = location.add(0, 1, 0);
        ParticleMaker explode = new ParticleMaker(ParticleMaker.Particle.EXPLOSION_LARGE, 10, 0.5, 0.5, 0.5);
        explode.sendToLocation(location);

        for (int i = 0; i < 20; i++) {
            float random = MathUtils.random(1f, 20f);
            float x = MathUtils.random(-1, 1);
            float y = MathUtils.random(-1, 1);
            float z = MathUtils.random(-1, 1);
            Arrow newArrow = location.getWorld().spawnArrow(location.clone().add(0, 2, 0), new Vector(x, y, z), 0.6f, random);
            newArrow.setMetadata("INSTA-KILL", new FixedMetadataValue(getShattered(), "INSTA-KILL"));
            newArrow.setMetadata("REMOVE_ME", new FixedMetadataValue(getShattered(), "REMOVE_ME"));
            listener.bowCache.inject(newArrow.getUniqueId().toString(), null);
        }
    }
}
