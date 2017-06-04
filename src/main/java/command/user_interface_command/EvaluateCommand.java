package command.user_interface_command;

import command.AbstractCommand;
import command.Command;
import component.evaluation.DeepLearningEvaluator;
import component.evaluation.W2VSimilarityEvaluator;
import db.dao.QuestionDAO;
import model.Question;

import java.util.List;

/**
 * Created by ercan on 01.06.2017.
 */
public class EvaluateCommand extends AbstractCommand implements Command {
    @Override
    public boolean execute(String[] parameter) {
        if(!validateParameter(parameter)) {
            return false;
        }

        try{
            QuestionDAO questionDAO = new QuestionDAO();
            DeepLearningEvaluator deepLearningEvaluator = new DeepLearningEvaluator();
            W2VSimilarityEvaluator w2VSimilarityEvaluator = new W2VSimilarityEvaluator();

            int limit = parseLimitCount(parameter[1]);
            List<Question> questionList = questionDAO.getQuestionsByLimit(limit);
            double dlScore = deepLearningEvaluator.evaluate(questionList);
            double w2vScore = w2VSimilarityEvaluator.evaluate(questionList);

            System.out.println("Deep Learning score:" + dlScore);
            System.out.println("W2V Similarity score:" + w2vScore);
        } catch(Exception ex){
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    protected boolean validateParameter(String[] parameter) {
        return parameter.length == 2;
    }

    private int parseLimitCount(String parameter){
        try {
            return Integer.parseInt(parameter);
        } catch (NumberFormatException ex){
            System.out.println("Lütfen sayısal bir değer giriniz.");
            ex.printStackTrace();
            return -1;
        } catch (Exception ex){
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            return -1;
        }
    }
}
