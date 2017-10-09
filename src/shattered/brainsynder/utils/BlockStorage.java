package shattered.brainsynder.utils;

import org.bukkit.block.Block;

import java.util.LinkedHashMap;

public class BlockStorage {
    private LinkedHashMap<String, BlockSave> compoundList = new LinkedHashMap<>();

    public void addBlock(Block block) {
        compoundList.put(new BlockLocation(block.getLocation()).toDataString(), new BlockSave(block));
    }

    public boolean contains (Block block) {
        return compoundList.containsKey(new BlockLocation(block.getLocation()).toDataString());
    }

    public void reset() {
        if (compoundList.isEmpty()) return;
        for (BlockSave save : compoundList.values()) save.placeOriginal();

        compoundList.clear();
    }
}
