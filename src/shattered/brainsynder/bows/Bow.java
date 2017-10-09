package shattered.brainsynder.bows;

import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.inventory.ItemStack;
import shattered.brainsynder.Shattered;

public abstract class Bow {
    private String name;
    private Shattered shattered;
    public Bow (Shattered shattered, String name) {
        this.name = name;
        this.shattered = shattered;
    }

    public abstract ItemStack getItem ();

    public void onHit(Location location){}

    public void whileInAir(Arrow arrow){}

    public int getMaxUses(){
        return (bowUses() - 2);
    }

    protected int bowUses () {
        return 385;
    }

    protected Shattered getShattered () {
        return shattered;
    }

    protected String getName() {
        return name;
    }
}
