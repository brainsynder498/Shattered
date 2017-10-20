package shattered.brainsynder.utils;

import org.bukkit.block.Block;

import java.util.LinkedHashMap;

public class BlockStorage {
    private LinkedHashMap<String, BlockInfo> compoundList = new LinkedHashMap<>();
    
    public void addBlock(Block block) {
        compoundList.put(new BlockLocation(block.getLocation()).toDataString(), new BlockInfo(block));
    }
    
    public BlockInfo getBlockInfo(Block block) {
        return compoundList.getOrDefault(new BlockLocation(block.getLocation()).toDataString(), new BlockInfo(block));
    }

    public boolean contains (Block block) {
        return compoundList.containsKey(new BlockLocation(block.getLocation()).toDataString());
    }

    public void reset() {
        if (compoundList.isEmpty()) return;
        for (BlockInfo save : compoundList.values()) save.placeOriginal();

        compoundList.clear();
    }

    public void reset(Block block) {
        if (compoundList.isEmpty()) return;
        if (!contains(block)) return;
        BlockInfo save = getBlockInfo(block);
        save.placeOriginal();

        compoundList.remove(save.getLocation().toDataString());
    }
}
