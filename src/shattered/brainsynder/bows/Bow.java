package shattered.brainsynder.bows;

import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.inventory.ItemStack;

public abstract class Bow {
    public abstract ItemStack getItem ();

    public void onHit(Location location){}

    public void onFire(Arrow arrow){}
}
