package shattered.brainsynder.commands;

import org.bukkit.command.CommandSender;
import shattered.brainsynder.Shattered;
import shattered.brainsynder.commands.annotations.CommandName;
import shattered.brainsynder.commands.annotations.CommandUsage;
import shattered.brainsynder.commands.annotations.Console;

public abstract class Command<T extends CommandSender> {
    private Shattered shattered = null;
    public Command (Shattered shattered) {
        this.shattered = shattered;
    }

    protected Shattered getShattered() {
        return shattered;
    }

    public void onCommand(T sender, String[] args) {}

    protected void sendUsage(T sender) {
        String name = "", usage = "";
        if (getClass().isAnnotationPresent(CommandName.class)) {
            name = getClass().getAnnotation(CommandName.class).value();
        }
        if (getClass().isAnnotationPresent(CommandUsage.class)) {
            usage = getClass().getAnnotation(CommandUsage.class).value();
        }
        if (getClass().isAnnotationPresent(Console.class)) {
            sender.sendMessage("Shattered | shattered " + name + ' ' + usage);
        }else{
            sender.sendMessage("§3Shattered | §b/shattered " + name + ' ' + usage);
        }
    }
}
