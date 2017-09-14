package shattered.brainsynder.utils;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import shattered.brainsynder.Shattered;
import shattered.brainsynder.modules.IModule;

public class ItemUtils extends IModule {
    public ItemUtils(Shattered shattered) {
        super(shattered, "ItemUtils");
    }

    public boolean isSimilar (ItemStack stack, ItemStack other) {
        return stack != null && (stack == other || other.getType() == stack.getType() && other.hasItemMeta() == stack.hasItemMeta() && (!other.hasItemMeta() || Bukkit.getItemFactory().equals(other.getItemMeta(), stack.getItemMeta())));
    }
}
