package shattered.brainsynder.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import simple.brainsynder.utils.Valid;

public class BlockLocation {
    private World world;
    private int x, y, z;

    public BlockLocation(World world, int x, int y, int z) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public BlockLocation(Location location) {
        this(location.getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    public static BlockLocation fromString(String str) {
        str = str.replace("BlockLocation:[world=", "");
        str = str.replace("x=", "");
        str = str.replace("y=", "");
        str = str.replace("z=", "");
        str = str.replace("]", "");
        String[] args = str.split(",");
        try {
            World world = Bukkit.getWorld(args[0]);
            int x = Integer.parseInt(args[1]);
            int y = Integer.parseInt(args[2]);
            int z = Integer.parseInt(args[3]);
            return new BlockLocation(world, x, y, z);
        } catch (Exception e) {
            return null;
        }
    }

    public String toDataString() {
        return "BlockLocation:[world=" + world.getName() + ",x=" + x + ",y=" + y + ",z=" + z + ']';
    }

    public Location toLocation() {
        return new Location(world, x, y, z);
    }

    public boolean atLocation(BlockLocation location) {
        Valid.notNull(location);
        return ((location.world.getName().equals(world.getName()))
                && (location.x == x)
                && (location.y == y)
                && (location.z == z)
        );
    }

    public World getWorld() {
        return this.world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return this.z;
    }

    public void setZ(int z) {
        this.z = z;
    }
}
