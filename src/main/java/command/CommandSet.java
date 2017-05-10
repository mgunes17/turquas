package command;

import java.util.Map;

/**
 * Created by mustafa on 27.04.2017.
 */
public class CommandSet {
    private Map<String, Command> commandMap;

    public CommandSet(Map<String, Command> commandMap) {
        this.commandMap = commandMap;
    }

    public boolean run(String command) {
        String[] parseList = command.split(" ");

        if(commandMap.get(parseList[0]) == null) {
            System.out.println(false);
            return false;
        } else {
            boolean result = commandMap.get(parseList[0]).execute(parseList);
            System.out.println(result);
            return result;
        }
    }
}
