package shattered.brainsynder.modules;

import org.bukkit.Bukkit;
import shattered.brainsynder.Shattered;
import shattered.brainsynder.bows.BowManager;
import shattered.brainsynder.commands.CommandHandler;
import shattered.brainsynder.listeners.BowListener;
import shattered.brainsynder.modules.list.GlassModule;
import shattered.brainsynder.utils.ItemUtils;
import shattered.brainsynder.utils.RandomRef;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    private List<IModule> moduleList = null;

    public ModuleManager(Shattered shattered) {
        moduleList = new ArrayList<>();
        moduleList.add(new GlassModule(shattered));
        moduleList.add(new BowManager(shattered));
        moduleList.add(new CommandHandler(shattered));
        moduleList.add(new BowListener(shattered));
        moduleList.add(new ItemUtils(shattered));
        moduleList.add(new RandomRef(shattered));
        load ();
    }

    private void load () {
        Bukkit.getLogger().info("Preparing to loading Modules:");
        moduleList.forEach(module -> {
            module.onLoad();
            Bukkit.getLogger().info("- Loading module: " + module.getName());
        });
    }

    public void unload() {
        moduleList.forEach(module -> {
            module.unLoad();
            Bukkit.getLogger().info("- Unloading module: " + module.getName());
        });
        moduleList.clear();
        moduleList = null;
    }

    public <T extends IModule> T getModule(String name) {
        if (moduleList.isEmpty()) return null;
        for (IModule module : moduleList) if (module.getName().equalsIgnoreCase(name)) return (T) module;
        return null;
    }
}
