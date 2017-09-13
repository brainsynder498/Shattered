package shattered.brainsynder.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import shattered.brainsynder.Shattered;
import shattered.brainsynder.commands.annotations.*;
import shattered.brainsynder.commands.list.Command_Bows;
import shattered.brainsynder.modules.IModule;

import java.util.ArrayList;
import java.util.Collections;

public class CommandHandler extends IModule implements CommandExecutor {
    private static ArrayList<Command> commands;

    public CommandHandler(Shattered shattered) {
        super(shattered, "CommandHandler");
    }

    @Override
    public void onLoad() {
        getShattered().getCommand("shattered").setExecutor(this);
        commands = new ArrayList<>();
        commands.add(new Command_Bows(getShattered()));
    }

    @Override
    public void unLoad() {
        commands.clear();
        commands = null;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] args) {
        if (command.getName().equalsIgnoreCase("pet")) {
            if (!(commandSender instanceof Player)) {
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
                            commandSender.sendMessage("§6-§e shattered " + name + ' ' + usage + " - §7" + description);
                        }
                    }
                } else {
                    Command base = null;
                    for (Command gcmd : commands) {
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
                        return true;
                    }

                    ArrayList<String> newArgs = new ArrayList<>();
                    Collections.addAll(newArgs, args);
                    newArgs.remove(0);
                    args = newArgs.toArray(new String[newArgs.size()]);

                    base.onConsoleCommand(commandSender, args);

                }
                return true;
            } else {
                Player p = (Player) commandSender;

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
                    for (Command gcmd : commands) {
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
                        return true;
                    }

                    if ((base.getClass().isAnnotationPresent(CommandPermission.class)) &&
                            (!p.hasPermission("Pet." + base.getClass().getAnnotation(CommandPermission.class).permission()))) {
                        //p.sendMessage(PetCore.get().getMessages().getString("No-Permission", true));
                        return true;
                    }

                    ArrayList<String> newArgs = new ArrayList<>();
                    Collections.addAll(newArgs, args);
                    newArgs.remove(0);
                    args = newArgs.toArray(new String[newArgs.size()]);

                    base.onPlayerCommand(p, args);

                }
            }
        }

        return true;
    }
}
