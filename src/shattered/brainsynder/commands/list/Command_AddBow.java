package shattered.brainsynder.commands.list;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import shattered.brainsynder.Shattered;
import shattered.brainsynder.arena.Arena;
import shattered.brainsynder.arena.ArenaManager;
import shattered.brainsynder.commands.Command;
import shattered.brainsynder.commands.annotations.CommandDescription;
import shattered.brainsynder.commands.annotations.CommandName;
import shattered.brainsynder.commands.annotations.CommandPermission;
import shattered.brainsynder.commands.annotations.CommandUsage;
import shattered.brainsynder.utils.BlockLocation;

@CommandName(value = "addbow")
@CommandUsage(value = "<id>")
@CommandPermission(value = "addbow")
@CommandDescription(value = "Adds a Bow Spawn point for the Arena")
public class Command_AddBow extends Command<Player> {
    public Command_AddBow(Shattered shattered) {
        super(shattered);
    }

    @Override
    public void onCommand(Player player, String[] args) {
        if (args.length == 0) {
            sendUsage(player);
        } else {
            ArenaManager manager = getShattered().getModuleManager().getModule("ArenaManager");
            Arena arena = manager.getArena(args[0]);
            if (arena == null) {
                player.sendMessage(ChatColor.RED + "Arena does not exists with this ID");
                return;
            }
            arena.addBowSpawns(new BlockLocation(player.getLocation()));
            player.sendMessage(ChatColor.GREEN + "Successfully added Bow spawn point.");
        }
    }
}
