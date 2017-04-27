package admin;

import command.CommandSet;

/**
 * Created by mustafa on 27.04.2017.
 */
public class CrawlerAdmin extends Admin {
    private CommandSet commandSet;

    public CrawlerAdmin(CommandSet commandSet) {
        this.commandSet = commandSet;
    }

    public boolean run(String inputCommand) {
        return false;
    }
}
