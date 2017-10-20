package shattered.brainsynder.commands.list;

import org.bukkit.entity.Player;
import shattered.brainsynder.Shattered;
import shattered.brainsynder.arena.ArenaManager;
import shattered.brainsynder.commands.Command;
import shattered.brainsynder.commands.annotations.CommandDescription;
import shattered.brainsynder.commands.annotations.CommandName;
import shattered.brainsynder.modules.list.GlassModule;

@CommandName(value = "reset")
@CommandDescription(value = "Resets the Broken Blocks (Testing Purposes)")
public class Command_Reset extends Command<Player> {
    public Command_Reset(Shattered shattered) {
        super(shattered);
    }

    @Override
    public void onCommand(Player player, String[] args) {
        GlassModule manager = getShattered().getModuleManager().getModule("GlassModule");
        ArenaManager arenaModule = getShattered().getModuleManager().getModule("ArenaManager");
        arenaModule.getArenas().forEach(arena -> manager.getBlockSave(arena).reset());
    }
}
