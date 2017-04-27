package command.turquas_command;

import command.AbstractCommand;
import command.Command;
import home_base.Turquas;

/**
 * Created by mustafa on 27.04.2017.
 */
public class TurquasSetNSCommand extends AbstractCommand implements Command {
    public boolean execute(String[] parameter) {
        if(!validateParameter(parameter))
            return false;

        if(!Turquas.adminMap.containsKey(parameter[1])) {
            System.out.println(parameter[1] + " isminde namespace yok!");
            return false;
        } else {
            Turquas.namespace = parameter[1];
            System.out.println(true);
            return true;
        }
    }

    protected boolean validateParameter(String[] parameter) {
        if(parameter.length != 2) {
            System.out.println("Eksik/Fazla parametre");
            return false;
        }

        return true;
    }
}
