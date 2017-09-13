package shattered.brainsynder.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import shattered.brainsynder.Shattered;

public abstract class Command {
    private Shattered shattered = null;
    public Command (Shattered shattered) {
        this.shattered = shattered;
    }

    protected Shattered getShattered() {
        return shattered;
    }

    public void onPlayerCommand(Player p, String[] args) {}
    public void onConsoleCommand(CommandSender sender, String[] args) {}
}
