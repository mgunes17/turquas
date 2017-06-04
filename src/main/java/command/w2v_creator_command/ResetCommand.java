package command.w2v_creator_command;

import command.Command;
import db.dao.QuestionDAO;
import model.Question;

import java.util.List;

/**
 * Created by ercan on 04.06.2017.
 */
public class ResetCommand implements Command {
    @Override
    public boolean execute(String[] parameter) {
        int count = 0;
        int size = 1000;
        QuestionDAO questionDAO = new QuestionDAO();
        List<Question> questionList = questionDAO.getProcessedQuestionsByLimit(size);
        while(questionList.size() > 0){
            questionList = questionDAO.getProcessedQuestionsByLimit(size);
            questionDAO.updateQuestionProcessed(questionList, false);
            count = count + size;
            System.out.println(count + " kadar işlendi.");
        }

        return false;
    }

}
