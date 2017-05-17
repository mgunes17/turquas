package home_base;

import command.Command;
import command.CommandSet;
import command.crawler_command.AddCommand;
import command.crawler_command.ReadCommand;
import command.crawler_command.SetCommand;
import command.crawler_command.StartCommand;
import command.db_updater_command.ExitCommand;
import command.db_updater_command.MorphologicCommand;
import command.db_updater_command.TfidfCommand;
import command.db_updater_command.W2VTokenCommand;
import command.question_generator_command.AskCommand;
import command.question_generator_command.ChangeCommand;
import command.question_generator_command.SaveCommand;
import command.user_interface_command.AnswerCommand;
import command.user_interface_command.SetParameterCommand;
import command.w2v_creator_command.*;
import db.dao.TokenDAO;
import nlp_tool.itu.LabelMorph;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mustafa on 27.04.2017.
 */
public class CommandBuilder {
    static CommandSet getUpdaterCommandSet() {
        Map<String, Command> commandMap = new HashMap<String, Command>();
        commandMap.put("exit", new ExitCommand());
        commandMap.put("morpha", new MorphologicCommand(new TokenDAO(), new LabelMorph()));
        commandMap.put("tfidf", new TfidfCommand());
        commandMap.put("w2vtoken", new W2VTokenCommand());
        return new CommandSet(commandMap);
    }

    static CommandSet getCrawlerCommandSet() {
        Map<String, Command> commandMap = new HashMap<String, Command>();
        commandMap.put("add", new AddCommand());
        commandMap.put("set", new SetCommand());
        commandMap.put("start", new StartCommand());
        commandMap.put("read", new ReadCommand());
        return new CommandSet(commandMap);
    }

    static CommandSet getQuestionGeneratorCommandSet() {
        Map<String, Command> commandMap = new HashMap<String, Command>();
        commandMap.put("ask", new AskCommand());
        commandMap.put("change", new ChangeCommand());
        commandMap.put("convert", new ConvertCommand());
        commandMap.put("save", new SaveCommand());
        return new CommandSet(commandMap);
    }

    static CommandSet getW2VCreatorAdminCommandSet(){
        Map<String, Command> commandMap = new HashMap<String, Command>();
        commandMap.put("set", new command.w2v_creator_command.SetCommand());
        commandMap.put("create", new CreateCommand());
        commandMap.put("convert", new ConvertCommand());
        commandMap.put("sentence", new Sentence2W2VCommand());
        commandMap.put("load", new LoadCommand());
        return new CommandSet(commandMap);
    }

    static CommandSet getUserInterfaceCommandSet() {
        Map<String, Command> commandMap = new HashMap<String, Command>();
        commandMap.put("answer", new AnswerCommand());
        commandMap.put("set", new SetParameterCommand());
        return new CommandSet(commandMap);
    }

}
