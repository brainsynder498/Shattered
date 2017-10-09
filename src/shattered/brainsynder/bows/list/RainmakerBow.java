package shattered.brainsynder.bows.list;

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
import simple.brainsynder.math.MathUtils;

public class RainmakerBow extends Bow {
    private boolean fired = false;
    private BowListener listener = null;
    
    public RainmakerBow(Shattered shattered) {
        super(shattered, "Rainmaker");
    }

    @Override
    public ItemStack getItem() {
        ItemMaker maker = new ItemMaker(Material.BOW);
        maker.setName("Rainmaker Bow");
        maker.addLoreLine("Rains arrows down on your enemies.");
        maker.enchant(Enchantment.ARROW_INFINITE, 1);
        ItemStack stack = maker.create();
        stack.setDurability((short) getMaxUses());
        return stack;
    }

    @Override
    public void whileInAir(Arrow arrow) {
        if (!fired) {
            if (listener == null) listener = getShattered().getModuleManager().getModule("BowListener");
            for (int i = 0; i < 3; i++) {
                float random = MathUtils.random(1f, 20f);
                float x = MathUtils.random(-1, 1);
                float z = MathUtils.random(-1, 1);
                Arrow newArrow = arrow.getWorld().spawnArrow(arrow.getLocation(), new Vector(x, -1, z), 0.6f, random);
                newArrow.setMetadata("INSTA-KILL", new FixedMetadataValue(getShattered(), "INSTA-KILL"));
                newArrow.setMetadata("REMOVE_ME", new FixedMetadataValue(getShattered(), "REMOVE_ME"));
                newArrow.setShooter(arrow.getShooter());
                listener.bowCache.inject(newArrow.getUniqueId().toString(), null);
            }
        }
        fired = !fired;
    }
}
