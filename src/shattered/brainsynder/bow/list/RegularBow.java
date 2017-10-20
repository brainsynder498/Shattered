package shattered.brainsynder.bow.list;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import shattered.brainsynder.Shattered;
import shattered.brainsynder.bow.Bow;
import simple.brainsynder.api.ItemMaker;

public class RegularBow extends Bow {
    public RegularBow(Shattered shattered) {
        super(shattered, "Shatter");
    }

    @Override
    public ItemStack getItem() {
        ItemMaker maker = new ItemMaker(Material.BOW);
        maker.setName("Shatter Bow");
        maker.enchant(Enchantment.ARROW_INFINITE, 1);
        return maker.create();
    }
}
