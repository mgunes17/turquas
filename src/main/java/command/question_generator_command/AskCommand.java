package command.question_generator_command;

import command.AbstractCommand;
import command.Command;
import component.question_generator.generate.MainGenerator;
import model.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by mustafa on 09.05.2017.
 */
public class AskCommand extends AbstractCommand implements Command {
    private MainGenerator mainGenerator;
    private List<Question> questionList;

    public boolean execute(String[] parameter) {
        mainGenerator = new MainGenerator();

        Scanner in = new Scanner(System.in);
        System.out.print("sentence=>");
        String sentence = in.nextLine();

        while(!sentence.equals("change")) {
            questionList = new ArrayList<>();
            questionList = mainGenerator.convertQuestions(sentence);
            showPairs();
            System.out.print("sentence=>");
            sentence = in.nextLine();
        }

        return true;
    }

    public void showPairs() {
        for(Question question: questionList) {
            System.out.println(question.getQuestion());
            System.out.println(question.getAnswer());
            System.out.println();
        }
    }

    protected boolean validateParameter(String[] parameter) {
        return false;
    }
}
