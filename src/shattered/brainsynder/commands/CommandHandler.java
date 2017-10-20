package shattered.brainsynder.commands;

import shattered.brainsynder.Shattered;
import shattered.brainsynder.commands.list.*;
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
        commands.add(new Command_Reset(getShattered()));
        commands.add(new Command_Create(getShattered()));
        commands.add(new Command_AddBow(getShattered()));
        commands.add(new Command_Delete(getShattered()));
        commands.add(new Command_List(getShattered()));
        commands.add(new Command_TestsBows(getShattered()));
    }

    @Override
    public void unLoad() {
        commands.clear();
        commands = null;
    }
}
