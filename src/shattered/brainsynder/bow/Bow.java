package shattered.brainsynder.bow;

import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.inventory.ItemStack;
import shattered.brainsynder.Shattered;
import shattered.brainsynder.player.IGamePlayer;

public abstract class Bow {
    private String name;
    private Shattered shattered;
    private IGamePlayer shooter;

    public Bow (Shattered shattered, String name) {
        this.name = name;
        this.shattered = shattered;
    }

    public IGamePlayer getShooter() {
        return shooter;
    }

    public void setShooter(IGamePlayer shooter) {
        this.shooter = shooter;
    }

    public abstract ItemStack getItem ();
    
    public void onHit(Location location){}
    public void onHit(Location location, float force){
        onHit(location);
    }
    
    public void onLaunch(Location location){}
    public void onLaunch(Location location, float force){
        onLaunch(location);
    }
    
    public void whileInAir(Arrow arrow){}
    public void whileInAir(Arrow arrow, float force){
        whileInAir(arrow);
    }

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
