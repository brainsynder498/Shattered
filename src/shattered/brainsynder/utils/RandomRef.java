package shattered.brainsynder.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.util.Vector;
import shattered.brainsynder.Shattered;
import shattered.brainsynder.modules.IModule;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RandomRef extends IModule{
    public RandomRef(Shattered shattered) {
        super(shattered, "RandomRef");
    }

    public List<Location> getStraightLine(LivingEntity player, int length) {
        List<Location> list = new ArrayList<>();
        for (int amount = length; amount > 0; amount--)
            list.add(player.getTargetBlock(null, amount).getLocation());
        return list;
    }

    public Location getEyeLocation(Entity player) {
        return player.getLocation().add(0.0D, 1.1D, 0.0D);
    }

    public Location getTargetLocation(Entity player) {
        return player.getLocation().clone().add(player.getLocation().getDirection().multiply(100.0));
    }

    public Vector calculatePath(Player player) {
        double yaw = Math.toRadians((double) (-player.getLocation().getYaw() - 90.0F));
        double pitch = Math.toRadians((double) (-player.getLocation().getPitch()));
        double x = Math.cos(pitch) * Math.cos(yaw) + (0.0D);
        double y = Math.sin(pitch) + (0.0D);
        double z = -Math.sin(yaw) * Math.cos(pitch) + (0.0D);
        return new Vector(x, y, z);
    }

    public void noArc(final Projectile proj, final org.bukkit.util.Vector direction) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(getShattered(), () -> {
            if (!proj.isDead()) {
                proj.setVelocity(direction);
                noArc(proj, direction);
            }
        }, 1L);
    }

    public Entity[] getNearbyEntities(Location l, int radius) {
        int chunk = radius < 16 ? 1 : (radius - radius % 16) / 16;
        ArrayList<Entity> radiusEntities = new ArrayList<>();

        for (int ent = 0 - chunk; ent <= chunk; ++ent) {
            for (int chZ = 0 - chunk; chZ <= chunk; ++chZ) {
                int x = (int) l.getX();
                int y = (int) l.getY();
                int z = (int) l.getZ();
                Location loc = new Location(l.getWorld(), (double) (x + ent * 16), (double) y, (double) (z + chZ * 16));

                for (Entity e : loc.getChunk().getEntities()) {
                    if (Objects.equals(e.getLocation().getWorld().getName(), l.getWorld().getName()) && e.getLocation().distance(l) <= (double) radius && e.getLocation().getBlock() != l.getBlock()) {
                        radiusEntities.add(e);
                    }
                }
            }
        }

        return radiusEntities.toArray(new Entity[radiusEntities.size()]);
    }
}