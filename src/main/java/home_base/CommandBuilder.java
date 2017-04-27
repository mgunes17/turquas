package home_base;

import command.Command;
import command.CommandSet;
import command.crawler_command.AddCommand;
import command.crawler_command.SetCommand;
import command.crawler_command.StartCommand;
import command.db_updater_command.ExitCommand;
import command.db_updater_command.MorphologicCommand;
import command.db_updater_command.TfidfCommand;
import command.w2v_creator_command.ConvertCommand;
import command.w2v_creator_command.CreateCommand;
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
        commandMap.put("add", new AddCommand());
        commandMap.put("set", new SetCommand());
        commandMap.put("start", new StartCommand());
        return new CommandSet(commandMap);
    }

    public static CommandSet getQuestionGeneratorCommandSet() {
        Map<String, Command> commandMap = new HashMap<String, Command>();
        //eklenecek
        return new CommandSet(commandMap);
    }

    public static CommandSet getW2VCreatorAdminCommandSet(){
        Map<String, Command> commandMap = new HashMap<String, Command>();
        commandMap.put("set", new command.w2v_creator_command.SetCommand());
        commandMap.put("create", new CreateCommand());
        commandMap.put("convert", new ConvertCommand());
        return new CommandSet(commandMap);
    }

}
