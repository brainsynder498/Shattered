package shattered.brainsynder;

import org.bukkit.plugin.java.JavaPlugin;
import shattered.brainsynder.modules.ModuleManager;

public class Shattered extends JavaPlugin {
    private ModuleManager moduleManager = null;

    @Override
    public void onEnable() {
        moduleManager = new ModuleManager(this);
    }

    public ModuleManager getModuleManager() {
        return moduleManager;
    }

    @Override
    public void onDisable() {
        moduleManager.unload();
        moduleManager = null;
    }
}
