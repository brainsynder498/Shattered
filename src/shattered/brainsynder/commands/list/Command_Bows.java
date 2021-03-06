package shattered.brainsynder.commands.list;

import org.bukkit.entity.Player;
import shattered.brainsynder.Shattered;
import shattered.brainsynder.bow.BowManager;
import shattered.brainsynder.commands.Command;
import shattered.brainsynder.commands.annotations.CommandDescription;
import shattered.brainsynder.commands.annotations.CommandName;

@CommandName(value = "bows")
@CommandDescription(value = "Gives you all the Bows (Testing Purposes)")
public class Command_Bows extends Command<Player> {
    public Command_Bows(Shattered shattered) {
        super(shattered);
    }

    @Override
    public void onCommand(Player player, String[] args) {
        BowManager manager = getShattered().getModuleManager().getModule("BowManager");
        if (manager.getBows().isEmpty()) return;
        manager.getBows().forEach(bow -> player.getInventory().addItem(bow.getItem()));
    }
}
