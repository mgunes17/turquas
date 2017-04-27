package admin;

import command.CommandSet;

/**
 * Created by mustafa on 27.04.2017.
 */
public class DBUpdaterAdmin extends Admin {
    private CommandSet commandSet;

    public DBUpdaterAdmin(CommandSet commandSet) {
       this.commandSet = commandSet;
    }

    public boolean run(String inputCommand) {
        return commandSet.run(inputCommand);
    }
}
