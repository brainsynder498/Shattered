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
                    name = gcmd.getClass().getAnnotation(CommandName.class).value();
                }
                if (gcmd.getClass().isAnnotationPresent(CommandUsage.class)) {
                    usage = gcmd.getClass().getAnnotation(CommandUsage.class).value();
                }
                if (gcmd.getClass().isAnnotationPresent(CommandDescription.class)) {
                    description = gcmd.getClass().getAnnotation(CommandDescription.class).value();
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
                    name = gcmd.getClass().getAnnotation(CommandName.class).value();
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

            base.onCommand(sender, args);

        }
    }

    @Override
    public void playerSendCommandEvent(Player p, String[] args) {
        if (args.length == 0) {
            for (Command gcmd : CommandHandler.commands) {
                String name = "", description = "", usage = "";
                if (gcmd.getClass().isAnnotationPresent(CommandName.class)) {
                    name = gcmd.getClass().getAnnotation(CommandName.class).value();
                }
                if (gcmd.getClass().isAnnotationPresent(CommandUsage.class)) {
                    usage = ' ' + gcmd.getClass().getAnnotation(CommandUsage.class).value();
                }
                if (gcmd.getClass().isAnnotationPresent(CommandDescription.class)) {
                    description = " - " + gcmd.getClass().getAnnotation(CommandDescription.class).value();
                }
                if (!gcmd.getClass().isAnnotationPresent(Console.class)) {
                    if (gcmd.getClass().isAnnotationPresent(CommandPermission.class)) {
                        if (!p.hasPermission("Shattered.command." + gcmd.getClass().getAnnotation(CommandPermission.class).value()))
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
                    name = gcmd.getClass().getAnnotation(CommandName.class).value();
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
                    (!p.hasPermission("Shattered.command." + base.getClass().getAnnotation(CommandPermission.class).value()))) {
                //p.sendMessage(PetCore.get().getMessages().getString("No-Permission", true));
                return;
            }

            ArrayList<String> newArgs = new ArrayList<>();
            Collections.addAll(newArgs, args);
            newArgs.remove(0);
            args = newArgs.toArray(new String[newArgs.size()]);

            base.onCommand(p, args);

        }
    }
}
