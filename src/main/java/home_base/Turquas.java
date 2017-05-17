package home_base;

import admin.*;
import command.Command;
import command.CommandSet;
import command.turquas_command.TurquasGetNSCommand;
import command.turquas_command.TurquasHelpCommand;
import command.turquas_command.TurquasSetNSCommand;
import db.dao.W2VTokenDAO;
import model.W2VToken;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by mustafa on 27.04.2017.
 */
public class Turquas {
    public static Map<String, Admin> adminMap;
    private CommandSet commandSet;
    public static String namespace = "crawler";

    private Turquas() {
        adminMap = new HashMap<String, Admin>();
        adminMap.put("crawler", new CrawlerAdmin(CommandBuilder.getCrawlerCommandSet()));
        adminMap.put("qgenerator", new QuestionGeneratorAdmin(CommandBuilder.getQuestionGeneratorCommandSet()));
        adminMap.put("updater", new DBUpdaterAdmin(CommandBuilder.getUpdaterCommandSet()));
        adminMap.put("w2v", new W2VCreatorAdmin(CommandBuilder.getW2VCreatorAdminCommandSet()));
        adminMap.put("ui", new UserInterfaceAdmin(CommandBuilder.getUserInterfaceCommandSet()));

        Map<String, Command> commandMap = new HashMap<String, Command>();
        commandMap.put("help", new TurquasHelpCommand());
        commandMap.put("set-ns", new TurquasSetNSCommand());
        commandMap.put("get-ns", new TurquasGetNSCommand());

        commandSet = new CommandSet(commandMap);
    }

    public static void main(String[] args) {
        System.out.println("Namespace crawler olarak tanımlı.");
        System.out.println("Set komutu ile değiştirebilirsiniz.");

        Turquas turquas = new Turquas();
        turquas.run();
    }

    private void run() {
        boolean next = true;

        while(next) {
            Scanner in = new Scanner(System.in);
            System.out.print("Komut giriniz=>");
            String commandInput = in.nextLine();

            if(!commandInput.equals("exit")) {
                if(!commandSet.run(commandInput))
                    adminMap.get(namespace).run(commandInput);
            } else
                next = false;
        }
    }

    public static Map<String, W2VToken> getW2VToken(String type) { //stem ya da letter ya da token
        W2VTokenDAO w2VTokenDAO = new W2VTokenDAO();
        Map<String, W2VToken> w2VTokens = w2VTokenDAO.getTokens(type);
        return w2VTokens;
    }
}
