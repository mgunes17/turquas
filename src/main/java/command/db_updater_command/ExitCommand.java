package command.db_updater_command;

import command.AbstractCommand;
import command.Command;

/**
 * Created by mustafa on 26.04.2017.
 */
public class ExitCommand extends AbstractCommand implements Command {
    public boolean execute(String[] parameter) {
        return validateParameter(parameter);
    }

    protected boolean validateParameter(String[] parameter) {
        if(parameter.length == 1)
            return true;
        else
            System.out.println("Eksik/Fazla parametre");

        return false;
    }
}
