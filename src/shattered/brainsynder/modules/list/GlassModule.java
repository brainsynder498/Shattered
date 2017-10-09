package shattered.brainsynder.modules.list;

import org.bukkit.Material;
import org.bukkit.block.Block;
import shattered.brainsynder.Shattered;
import shattered.brainsynder.modules.IModule;
import shattered.brainsynder.utils.BlockStorage;

import java.util.HashMap;
import java.util.Map;

public class GlassModule extends IModule {
    private BlockStorage blockSave = null;
    private Map<Integer, Integer> order = null;

    public GlassModule(Shattered shattered) {
        super(shattered, "GlassModule");
    }

    @Override
    public void onLoad() {
        super.onLoad();
        order = new HashMap<>();
        order.put(15, 7);
        order.put(7, 8);
        order.put(8, 0);
        order.put(0, -1);
        blockSave = new BlockStorage();
    }
    
    public void onArrowLand (Block block) {
        if (block == null) return;
        if ((block.getType() != Material.STAINED_GLASS)
                && (block.getType() != Material.STAINED_GLASS_PANE)
                && (block.getType() != Material.GLASS)
                && (block.getType() != Material.THIN_GLASS)) return;
        if (!blockSave.contains(block)) blockSave.addBlock(block);
        if ((block.getType() == Material.GLASS)
                || (block.getType() == Material.THIN_GLASS)) {
            block.setType(Material.AIR);
            return;
        }
        
        int var = order.getOrDefault(((int)block.getData()), 1000);
        if (var == 1000) {
            block.setData((byte) 15);
        }else if (var == -1) {
            if (block.getType().name().contains("PANE")) {
                block.setType(Material.THIN_GLASS);
                return;
            }
            block.setType(Material.GLASS);
        }else{
            block.setData((byte) var);
        }
        
    }

    public BlockStorage getBlockSave() {
        return blockSave;
    }
}
