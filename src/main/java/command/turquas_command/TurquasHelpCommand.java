package command.turquas_command;

import command.AbstractCommand;
import command.Command;

/**
 * Created by mustafa on 27.04.2017.
 */
public class TurquasHelpCommand extends AbstractCommand implements Command {
    protected boolean validateParameter(String[] parameter) {
        return false;
    }

    public boolean execute(String[] parameter) {
        return false;
    }
}
