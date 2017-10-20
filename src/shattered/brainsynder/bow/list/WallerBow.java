package shattered.brainsynder.bow.list;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import shattered.brainsynder.Shattered;
import shattered.brainsynder.bow.Bow;
import shattered.brainsynder.bow.Special;
import shattered.brainsynder.modules.list.GlassModule;
import shattered.brainsynder.utils.BlockUtils;
import simple.brainsynder.api.ItemMaker;
import simple.brainsynder.api.ParticleMaker;

public class WallerBow extends Bow implements Special {
    public WallerBow(Shattered shattered) {
        super(shattered, "Waller");
    }

    @Override
    public ItemStack getItem() {
        ItemMaker maker = new ItemMaker(Material.BOW);
        maker.setName("Waller Bow");
        maker.addLoreLine("Builds a Wall of glass");
        maker.enchant(Enchantment.ARROW_INFINITE, 1);
        ItemStack stack = maker.create();
        stack.setDurability((short) getMaxUses());
        return stack;
    }

    @Override
    public void onHit(final Location location, float force) {
        int radius = 1;
        if (force == 1.0) {
            radius = 3;
        }else if ((force <= 1.6) && (force >= 1.4)) {
            radius = 2;
        }
        
        BlockUtils.getBlocksInRadius(location, radius, false).forEach(block ->{
            GlassModule module = getShattered().getModuleManager().getModule("GlassModule");
            if (module.getBlockSave(getShooter().getArena()).contains(block)) {
                module.getBlockSave(getShooter().getArena()).reset(block);
            }
            ParticleMaker explode = new ParticleMaker(ParticleMaker.Particle.TOTEM, 5, 0.75, 0.75, 0.75);
            explode.sendToLocation(block.getLocation());
        });
    }
}
