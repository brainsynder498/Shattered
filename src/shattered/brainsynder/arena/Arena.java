package shattered.brainsynder.arena;

import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import shattered.brainsynder.utils.BlockLocation;
import shattered.brainsynder.utils.Cuboid;
import shattered.brainsynder.utils.RandomCollection;
import simple.brainsynder.nbt.CompressedStreamTools;
import simple.brainsynder.nbt.StorageTagCompound;
import simple.brainsynder.utils.Base64Wrapper;

import java.io.File;
import java.io.FileInputStream;

public class Arena {
    private String id = null;
    private Cuboid cuboid = null;
    private JSONArray bowSpawns = null;

    public Arena(String id) {
        this.id = id;
        bowSpawns = new JSONArray();
    }

    static Arena loadArena(File file) {
        try {
            Arena arena = new Arena(file.getName().replace(".stc", ""));
            FileInputStream stream = new FileInputStream(file);
            StorageTagCompound compound = CompressedStreamTools.readCompressed(stream);
            if (compound.hasKey("region"))
                arena.cuboid = new Cuboid(compound.getCompoundTag("region"));
            if (compound.hasKey("bowspawns"))
                arena.bowSpawns = (JSONArray) JSONValue.parse(Base64Wrapper.decodeString(compound.getString("bowspawns")));

            return arena;
        } catch (Throwable ignored) {
        }
        return null;
    }

    StorageTagCompound serialize() {
        StorageTagCompound compound = new StorageTagCompound();
        compound.setString("id", id);
        if (cuboid != null) compound.setTag("region", cuboid.serialize());
        if (bowSpawns != null) compound.setString("bowspawns", Base64Wrapper.encodeString(bowSpawns.toJSONString()));
        return compound;
    }

    public void setCuboid(Cuboid cuboid) {
        this.cuboid = cuboid;
    }

    public void addBowSpawns(BlockLocation location) {
        bowSpawns.add(location.toDataString());
    }

    public Cuboid getCuboid() {
        return cuboid;
    }

    public RandomCollection<BlockLocation> getBowSpawns() {
        RandomCollection<BlockLocation> collection = new RandomCollection<>();
        for (Object obj : bowSpawns) {
            BlockLocation location = BlockLocation.fromString(String.valueOf(obj));
            collection.add(50, location);
        }
        return collection;
    }

    public String getId() {
        return id;
    }
}
