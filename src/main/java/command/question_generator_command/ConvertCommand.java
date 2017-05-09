package command.question_generator_command;

import command.AbstractCommand;
import command.Command;

/**
 * Created by mustafa on 09.05.2017.
 */
public class ConvertCommand extends AbstractCommand implements Command {
    public boolean execute(String[] parameter) {
        return false;
    }

    protected boolean validateParameter(String[] parameter) {
        return false;
    }
}
