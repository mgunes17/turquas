package command.user_interface_command;

import command.AbstractCommand;
import command.Command;
import component.user_interface.answerer.AnswererWithDeepLearning;
import component.user_interface.answerer.AnswererWithVectorSimilarity;
import zemberek.langid.LanguageIdentifier;

import java.io.*;
import java.util.Scanner;

/**
 * Created by mustafa on 10.05.2017.
 */
public class AnswerCommand extends AbstractCommand implements Command {
    public boolean execute(String[] parameter) {
        if(!validateParameter(parameter)) {
            return false;
        }

        Scanner in = new Scanner(System.in);
        System.out.print("answer=>");
        String question = in.nextLine().toLowerCase();

        while(!question.equals("change")) {
            if(validateQuestion(question)) { //girdi cevaplanabilir bir soru ise
                AnswererWithDeepLearning answererWithDeepLearning = new AnswererWithDeepLearning();
                answererWithDeepLearning.answer(question);
                AnswererWithVectorSimilarity answererWithVectorSimilarity = new AnswererWithVectorSimilarity();
                answererWithVectorSimilarity.answer(question);
            }

            System.out.print("answer=>");
            question = in.nextLine().toLowerCase();
        }

        return true;
    }

    private boolean validateQuestion(String question){
        try{
            LanguageIdentifier languageIdentifier = LanguageIdentifier.fromInternalModelGroup("tr_group");
            String[] words = question.split(" ");

            return words.length >= 2 && languageIdentifier.identify(question).equals("tr");
        } catch(IOException ex){
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            return false;
        }
    }

    protected boolean validateParameter(String[] parameter) {
        return parameter.length == 1;
    }


}
