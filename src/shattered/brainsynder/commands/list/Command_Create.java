package shattered.brainsynder.commands.list;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.Selection;
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
import shattered.brainsynder.utils.Cuboid;

@CommandName(value = "create")
@CommandUsage(value = "<id>")
@CommandPermission(value = "create")
@CommandDescription(value = "Creates a Shattered Arena")
public class Command_Create extends Command<Player> {
    public Command_Create(Shattered shattered) {
        super(shattered);
    }

    @Override
    public void onCommand(Player player, String[] args) {
        WorldEditPlugin we = (WorldEditPlugin) getShattered().getServer().getPluginManager().getPlugin("WorldEdit");
        Selection sel = we.getSelection(player);
        if(sel == null){
            player.sendMessage(ChatColor.RED+"You must make a WorldEdit Selection of the arena first.");
            return;
        }
        BlockLocation min = new BlockLocation (sel.getMinimumPoint());
        BlockLocation max = new BlockLocation (sel.getMaximumPoint());

        if (args.length == 0){
            sendUsage(player);
        }else{
            ArenaManager manager = getShattered().getModuleManager().getModule("ArenaManager");
            Arena arena = manager.getArena(args[0]);
            if (arena != null) {
                player.sendMessage(ChatColor.RED+"An arena already exists with this ID");
                return;
            }
            arena = new Arena(args[0]);
            arena.setCuboid(new Cuboid(min, max));
            manager.addArena(arena);
            player.sendMessage(ChatColor.GREEN+"Successfully Created arena.");
        }
    }
}
