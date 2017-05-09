package command.question_generator_command;

import command.AbstractCommand;
import command.Command;
import component.question_generator.factory.QuestionFactory;
import component.question_generator.generate.MainGenerator;

import java.util.Scanner;

/**
 * Created by mustafa on 09.05.2017.
 */
public class AskCommand extends AbstractCommand implements Command {
    private QuestionFactory factory;
    private MainGenerator mainGenerator;

    public boolean execute(String[] parameter) {
        mainGenerator = new MainGenerator();

        Scanner in = new Scanner(System.in);
        System.out.print("sentence=>");
        String sentence = in.nextLine();

        while(!sentence.equals("change")) {
            mainGenerator.convertQuestions(sentence);
            System.out.print("sentence=>");
            sentence = in.nextLine();
        }

        return true;
    }

    protected boolean validateParameter(String[] parameter) {
        return false;
    }
}
