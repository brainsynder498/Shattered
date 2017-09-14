package shattered.brainsynder.commands;

import shattered.brainsynder.Shattered;
import shattered.brainsynder.commands.list.Command_Bows;
import shattered.brainsynder.modules.IModule;

import java.util.ArrayList;

public class CommandHandler extends IModule {
    static ArrayList<Command> commands;

    public CommandHandler(Shattered shattered) {
        super(shattered, "CommandHandler");
    }

    @Override
    public void onLoad() {
        new CommandBase ().registerCommand(getShattered());
        commands = new ArrayList<>();
        commands.add(new Command_Bows(getShattered()));
    }

    @Override
    public void unLoad() {
        commands.clear();
        commands = null;
    }
}
