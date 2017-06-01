package command.user_interface_command;

import command.AbstractCommand;
import command.Command;
import component.user_interface.answerer.AnswererWithDeepLearning;
import component.user_interface.answerer.AnswererWithVectorSimilarity;
import zemberek.langid.LanguageIdentifier;

import java.io.IOException;
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
        AnswererWithDeepLearning answererWithDeepLearning = new AnswererWithDeepLearning();
        AnswererWithVectorSimilarity answererWithVectorSimilarity = new AnswererWithVectorSimilarity();

        while(!question.equals("change")) {
            if(validateQuestion(question)) { //girdi cevaplanabilir bir soru ise
                long start_time = System.nanoTime();
                answererWithDeepLearning.answer(question);
                answererWithVectorSimilarity.answer(question);
                long end_time = System.nanoTime();
                double difference = (end_time - start_time)/1e6;
                System.out.println("toplam geçen süre:" + difference);
            }

            System.out.print("answer=>");
            question = in.nextLine().toLowerCase();
        }

        return true;
    }

    public boolean validateQuestion(String question){
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
