package shattered.brainsynder.commands.list;

import org.bukkit.entity.Player;
import shattered.brainsynder.Shattered;
import shattered.brainsynder.commands.Command;
import shattered.brainsynder.commands.annotations.CommandDescription;
import shattered.brainsynder.commands.annotations.CommandName;
import shattered.brainsynder.modules.list.GlassModule;

@CommandName(name = "reset")
@CommandDescription(description = "Resets the Broken Blocks (Testing Purposes)")
public class Command_Reset extends Command {
    public Command_Reset(Shattered shattered) {
        super(shattered);
    }

    @Override
    public void onPlayerCommand(Player player, String[] args) {
        GlassModule manager = getShattered().getModuleManager().getModule("GlassModule");
        manager.getBlockSave().reset();
    }
}
