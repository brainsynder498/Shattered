package shattered.brainsynder.modules;

import shattered.brainsynder.Shattered;
import shattered.brainsynder.bows.BowManager;
import shattered.brainsynder.commands.CommandHandler;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    private List<IModule> moduleList = null;

    public ModuleManager(Shattered shattered) {
        moduleList = new ArrayList<>();
        moduleList.add(new BowManager(shattered));
        moduleList.add(new CommandHandler(shattered));
        moduleList.forEach(IModule::onLoad);
    }

    public void unload() {
        moduleList.forEach(IModule::unLoad);
        moduleList.clear();
        moduleList = null;
    }

    public <T extends IModule> T getModule(String name) {
        if (moduleList.isEmpty()) return null;
        for (IModule module : moduleList) if (module.getName().equalsIgnoreCase(name)) return (T) module;
        return null;
    }
}
