package command.turquas_command;

import command.AbstractCommand;
import command.Command;
import home_base.Turquas;

/**
 * Created by mustafa on 27.04.2017.
 */
public class TurquasGetNSCommand extends AbstractCommand implements Command {
    public boolean execute(String[] parameter) {
        if(!validateParameter(parameter))
            return false;

        System.out.println(Turquas.namespace);
        return true;
    }

    protected boolean validateParameter(String[] parameter) {
        if(parameter.length != 1) {
            System.out.println("Eksik/Fazla parametre");
            return false;
        }

        return true;
    }
}
