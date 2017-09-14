package shattered.brainsynder.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import shattered.brainsynder.commands.annotations.*;
import simple.brainsynder.commands.BaseCommand;

import java.util.ArrayList;
import java.util.Collections;

public class CommandBase extends BaseCommand {
    CommandBase() {
        super("shattered");
    }

    @Override
    public void consoleSendCommandEvent(CommandSender sender, String[] args) {
        if (args.length == 0) {
            for (Command gcmd : CommandHandler.commands) {
                String name = "", description = "", usage = "";
                if (gcmd.getClass().isAnnotationPresent(CommandName.class)) {
                    name = gcmd.getClass().getAnnotation(CommandName.class).name();
                }
                if (gcmd.getClass().isAnnotationPresent(CommandUsage.class)) {
                    description = gcmd.getClass().getAnnotation(CommandUsage.class).usage();
                }
                if (gcmd.getClass().isAnnotationPresent(CommandDescription.class)) {
                    usage = gcmd.getClass().getAnnotation(CommandDescription.class).description();
                }
                if (gcmd.getClass().isAnnotationPresent(Console.class)) {
                    sender.sendMessage("§6-§e shattered " + name + ' ' + usage + " - §7" + description);
                }
            }
        } else {
            Command base = null;
            for (Command gcmd : CommandHandler.commands) {
                String name = "";
                if (gcmd.getClass().isAnnotationPresent(CommandName.class)) {
                    name = gcmd.getClass().getAnnotation(CommandName.class).name();
                }
                if (name.equalsIgnoreCase(args[0])) {
                    if (gcmd.getClass().isAnnotationPresent(Console.class)) {
                        base = gcmd;
                        break;
                    }
                }
            }

            if (base == null) {
                return;
            }

            ArrayList<String> newArgs = new ArrayList<>();
            Collections.addAll(newArgs, args);
            newArgs.remove(0);
            args = newArgs.toArray(new String[newArgs.size()]);

            base.onConsoleCommand(sender, args);

        }
    }

    @Override
    public void playerSendCommandEvent(Player p, String[] args) {
        if (args.length == 0) {
            for (Command gcmd : CommandHandler.commands) {
                String name = "", description = "", usage = "";
                if (gcmd.getClass().isAnnotationPresent(CommandName.class)) {
                    name = gcmd.getClass().getAnnotation(CommandName.class).name();
                }
                if (gcmd.getClass().isAnnotationPresent(CommandUsage.class)) {
                    description = ' ' + gcmd.getClass().getAnnotation(CommandUsage.class).usage();
                }
                if (gcmd.getClass().isAnnotationPresent(CommandDescription.class)) {
                    usage = " - " + gcmd.getClass().getAnnotation(CommandDescription.class).description();
                }
                if (!gcmd.getClass().isAnnotationPresent(Console.class)) {
                    if (gcmd.getClass().isAnnotationPresent(CommandPermission.class)) {
                        if (!p.hasPermission("Pet." + gcmd.getClass().getAnnotation(CommandPermission.class).permission()))
                            continue;
                    }
                    p.sendMessage("§b/shattered " + name + ChatColor.GRAY + usage + description);
                }
            }
        } else {
            Command base = null;
            for (Command gcmd : CommandHandler.commands) {
                String name = "";
                if (gcmd.getClass().isAnnotationPresent(CommandName.class)) {
                    name = gcmd.getClass().getAnnotation(CommandName.class).name();
                }
                if (name.equalsIgnoreCase(args[0])) {
                    if (!gcmd.getClass().isAnnotationPresent(Console.class)) {
                        base = gcmd;
                        break;
                    }
                }
            }

            if (base == null) {
                return;
            }

            if ((base.getClass().isAnnotationPresent(CommandPermission.class)) &&
                    (!p.hasPermission("Pet." + base.getClass().getAnnotation(CommandPermission.class).permission()))) {
                //p.sendMessage(PetCore.get().getMessages().getString("No-Permission", true));
                return;
            }

            ArrayList<String> newArgs = new ArrayList<>();
            Collections.addAll(newArgs, args);
            newArgs.remove(0);
            args = newArgs.toArray(new String[newArgs.size()]);

            base.onPlayerCommand(p, args);

        }
    }
}
