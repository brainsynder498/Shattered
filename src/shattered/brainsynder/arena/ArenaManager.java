package shattered.brainsynder.arena;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import shattered.brainsynder.Shattered;
import shattered.brainsynder.bow.Bow;
import shattered.brainsynder.bow.BowManager;
import shattered.brainsynder.modules.IModule;
import shattered.brainsynder.utils.BlockLocation;
import simple.brainsynder.nbt.CompressedStreamTools;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArenaManager extends IModule {
    public ArenaManager(Shattered shattered) {
        super(shattered, "ArenaManager");
    }
    
    private List<Arena> arenas = null;
    
    @Override public void onLoad() {
        arenas = new ArrayList<>();
    
        File folder = new File(getShattered().getDataFolder().toString() + File.separator + "Arenas");
        if (!folder.exists()) folder.mkdirs();
        
        List<File> files = Arrays.asList(folder.listFiles());
        if (!files.isEmpty()) {
            files.forEach(file -> {
                if (file.getName().endsWith(".stc")) {
                    Arena arena = Arena.loadArena(file);
                    if (arena != null) arenas.add(arena);
                }
            });
        }
    }

    public void deleteArena (Arena arena) {
        File folder = new File(getShattered().getDataFolder().toString() + File.separator + "Arenas");
        if (!folder.exists()) folder.mkdirs();
        File file = new File(folder, arena.getId() + ".stc");
        file.delete();
        arenas.remove(arena);
    }

    public List<Arena> getArenas() {
        return arenas;
    }

    @Override public void unLoad() {
        File folder = new File(getShattered().getDataFolder().toString() + File.separator + "Arenas");
        if (!folder.exists()) folder.mkdirs();
        for (Arena arena : arenas) {
            File file = new File(folder, arena.getId() + ".stc");
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (Exception ignored) {}
            }
            try {
                FileOutputStream stream = new FileOutputStream(file);
                CompressedStreamTools.writeCompressed(arena.serialize(), stream);
            }catch (Exception ignored) {}
        }


        arenas = null;
    }

    public void addArena (Arena arena) {
        arenas.add(arena);
    }

    public Arena getArena (String id) {
        for (Arena arena : arenas) {
            if (arena.getId().equalsIgnoreCase(id)) return arena;
        }
        return null;
    }

    public boolean containsBow (Arena arena, BlockLocation location) {
        BowManager manager = getShattered().getModuleManager().getModule("BowManager");
        final boolean[] contains = {false};

        arena.getCuboid().getChunks().forEach(chunk -> {
            for (Entity entity : chunk.getEntities()) {
                BlockLocation entityLocation = new BlockLocation(entity.getLocation());
                if (entityLocation.atLocation(location)) {
                    if (entity instanceof Item) {
                        Item item = (Item)entity;
                        Bow bow = manager.getBow(item.getItemStack());
                        contains[0] = (bow != null);
                    }
                    break;
                }
            }
        });

        return contains[0];
    }
}
