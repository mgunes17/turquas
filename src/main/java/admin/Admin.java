package admin;

import command.CommandSet;

/**
 * Created by mustafa on 27.04.2017.
 */
public abstract class Admin {
    private CommandSet commandSet;

    Admin(CommandSet commandSet) {
        this.commandSet = commandSet;
    }

    public boolean run(String inputCommand) {
        return commandSet.run(inputCommand);
    }

}
