package home_base;

import admin.Admin;
import admin.CrawlerAdmin;
import admin.DBUpdaterAdmin;
import admin.QuestionGeneratorAdmin;
import command.Command;
import command.CommandSet;
import command.turquas_command.TurquasGetNSCommand;
import command.turquas_command.TurquasHelpCommand;
import command.turquas_command.TurquasSetNSCommand;

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

    public Turquas() {
        adminMap = new HashMap<String, Admin>();
        adminMap.put("crawler", new CrawlerAdmin(CommandBuilder.getCrawlerCommandSet()));
        adminMap.put("qgenerator", new QuestionGeneratorAdmin(CommandBuilder.getQuestionGeneratorCommandSet()));
        adminMap.put("updater", new DBUpdaterAdmin(CommandBuilder.getUpdaterCommandSet()));

        Map<String, Command> commandMap = new HashMap<String, Command>();
        commandMap.put("help", new TurquasHelpCommand());
        commandMap.put("set", new TurquasSetNSCommand());
        commandMap.put("get", new TurquasGetNSCommand());

        commandSet = new CommandSet(commandMap);
    }

    public static void main(String[] args) {
        System.out.println("Namespace crawler olarak tanımlı.");
        System.out.println("Set komutu ile değiştirebilirsiniz.");

        Turquas turquas = new Turquas();
        turquas.run();
    }

    public void run() {
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
}
