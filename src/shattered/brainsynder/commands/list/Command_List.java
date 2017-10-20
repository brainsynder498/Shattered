package shattered.brainsynder.commands.list;

import org.bukkit.entity.Player;
import shattered.brainsynder.Shattered;
import shattered.brainsynder.arena.ArenaManager;
import shattered.brainsynder.commands.Command;
import shattered.brainsynder.commands.annotations.CommandDescription;
import shattered.brainsynder.commands.annotations.CommandName;

@CommandName(value = "list")
@CommandDescription(value = "Lists all the Arenas")
public class Command_List extends Command<Player> {
    public Command_List(Shattered shattered) {
        super(shattered);
    }

    @Override
    public void onCommand(Player player, String[] args) {
        ArenaManager manager = getShattered().getModuleManager().getModule("ArenaManager");
        if (manager.getArenas().isEmpty()) {
            player.sendMessage("§cThere are no created arenas.");
            return;
        }

        player.sendMessage("§3Arena List:");
        manager.getArenas().forEach(arena -> player.sendMessage("§b- §7" + arena.getId()));
    }
}
