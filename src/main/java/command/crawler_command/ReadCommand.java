package command.crawler_command;

import command.AbstractCommand;
import command.Command;

/**
 * Created by mustafa on 10.05.2017.
 */
public class ReadCommand extends AbstractCommand implements Command {
    public boolean execute(String[] parameter) {
        return false;
    }

    protected boolean validateParameter(String[] parameter) {
        return parameter.length == 2;
    }
}
