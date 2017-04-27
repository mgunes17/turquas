package home_base;

import command.Command;
import command.CommandSet;
import command.db_updater_command.ExitCommand;
import command.db_updater_command.MorphologicCommand;
import command.db_updater_command.TfidfCommand;
import db.dao.TokenDAO;
import nlp_tool.itu.LabelMorph;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mustafa on 27.04.2017.
 */
public class CommandBuilder {
    public static CommandSet getUpdaterCommandSet() {
        Map<String, Command> commandMap = new HashMap<String, Command>();
        commandMap.put("exit", new ExitCommand());
        commandMap.put("morpha", new MorphologicCommand(new TokenDAO(), new LabelMorph()));
        commandMap.put("tfidf", new TfidfCommand());
        return new CommandSet(commandMap);
    }

    public static CommandSet getCrawlerCommandSet() {
        Map<String, Command> commandMap = new HashMap<String, Command>();
        //eklenecek
        return new CommandSet(commandMap);
    }

    public static CommandSet getQuestionGeneratorCommandSet() {
        Map<String, Command> commandMap = new HashMap<String, Command>();
        //eklenecek
        return new CommandSet(commandMap);
    }
}
