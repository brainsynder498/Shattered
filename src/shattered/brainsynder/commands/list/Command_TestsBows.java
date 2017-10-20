package shattered.brainsynder.commands.list;

import org.bukkit.ChatColor;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import shattered.brainsynder.Shattered;
import shattered.brainsynder.arena.Arena;
import shattered.brainsynder.arena.ArenaManager;
import shattered.brainsynder.bow.Bow;
import shattered.brainsynder.bow.BowManager;
import shattered.brainsynder.commands.Command;
import shattered.brainsynder.commands.annotations.CommandDescription;
import shattered.brainsynder.commands.annotations.CommandName;
import shattered.brainsynder.commands.annotations.CommandUsage;
import shattered.brainsynder.utils.BlockLocation;
import simple.brainsynder.api.ParticleMaker;

@CommandName(value = "testbows")
@CommandUsage(value = "<id>")
@CommandDescription(value = "Test Command")
public class Command_TestsBows extends Command<Player> {
    public Command_TestsBows(Shattered shattered) {
        super(shattered);
    }

    @Override
    public void onCommand(Player player, String[] args) {
        if (args.length == 0) {
            sendUsage(player);
        } else {
            ArenaManager manager = getShattered().getModuleManager().getModule("ArenaManager");
            BowManager bowManager = getShattered().getModuleManager().getModule("BowManager");
            Arena arena = manager.getArena(args[0]);
            if (arena == null) {
                player.sendMessage(ChatColor.RED + "Arena does not exists with this ID");
                return;
            }

            BlockLocation location = arena.getBowSpawns().next();
            if (!manager.containsBow(arena, location)) {
                Class<? extends Bow> bowClass = bowManager.getSpecialBows().next();
                ItemStack stack = bowManager.getSpecialBow(bowClass).getItem();
                Item item = location.getWorld().dropItem(location.toLocation(), stack);
                item.setVelocity(new Vector(0, 0, 0));
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (item == null) {
                            cancel();
                            return;
                        }
                        if (item.isDead()) {
                            cancel();
                            return;
                        }
                        if (!item.isValid()) {
                            cancel();
                            return;
                        }
                        try {
                            ParticleMaker maker = new ParticleMaker(ParticleMaker.Particle.CRIT, 10, 0.5);
                            maker.sendToLocation(item.getLocation());
                        }catch (Throwable t) {
                            cancel();
                        }
                    }
                }.runTaskTimer(getShattered(), 0, 2);
            }
        }
    }
}
