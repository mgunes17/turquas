package home_base;

import admin.*;
import command.Command;
import command.CommandSet;
import command.turquas_command.TurquasGetNSCommand;
import command.turquas_command.TurquasHelpCommand;
import command.turquas_command.TurquasSetNSCommand;
import properties.TurquasProperties;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by mustafa on 27.04.2017.
 */
public class Turquas {
    public static Map<String, Admin> adminMap;
    public static Socket pythonSocket;
    private CommandSet commandSet;
    public static String namespace = "crawler";
    public static final Map<String, String> QUESTION_TYPE = new HashMap<>();

    static {
        QUESTION_TYPE.put("nereden", "abl");
        QUESTION_TYPE.put("neyi", "acc");
        QUESTION_TYPE.put("kaçı", "acc");
        QUESTION_TYPE.put("kaçının", "cc");
        QUESTION_TYPE.put("nereye", "dat");
        QUESTION_TYPE.put("nerede", "loc");
        QUESTION_TYPE.put("kaçta", "loc");
        QUESTION_TYPE.put("nedir", "cop");
        QUESTION_TYPE.put("neyin", "gen");
        QUESTION_TYPE.put("nasıl", "inst");
        QUESTION_TYPE.put("ne", "inst");
        QUESTION_TYPE.put("kim", "inst");
        QUESTION_TYPE.put("ne zaman", "time");
    }

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
        new TurquasProperties().loadProperties();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ServerSocket serverSocket = new ServerSocket(5555);
                    pythonSocket = serverSocket.accept();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
            }
        }).start();
        //startPythonScript();
        System.out.println("python-java iletişimi kuruldu.");
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

    private void startPythonScript(){
        try {
            String command = UserInterfaceAdmin.pathMap.get("python") + " " + UserInterfaceAdmin.pathMap.get("script");
            Runtime.getRuntime().exec(command);
            //Process p = Runtime.getRuntime().exec(command);
            //p.waitFor();
            //p.destroy();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String findQuestionType(String question) {
        String questionType = "inst"; //nasıl - ne - kim
        String[] words = question.split("( )+");


        for(String word: words){
            if(Turquas.QUESTION_TYPE.containsKey(word)){
                questionType = Turquas.QUESTION_TYPE.get(word);
            }
        }

        if(question.contains("ne zaman")){
            questionType = Turquas.QUESTION_TYPE.get("ne zaman");
        }

        /*for(String questionWord: Turquas.QUESTION_TYPE.keySet()) {
            if(question.contains(questionWord))
                questionType = Turquas.QUESTION_TYPE.get(questionWord);
        }*/

        return questionType;
    }
}
