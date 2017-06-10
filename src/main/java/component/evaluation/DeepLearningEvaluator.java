package component.evaluation;

import component.user_interface.answerer.AnswererWithDeepLearning;
import model.Question;
import model.QuestionForCompare;

import java.util.List;

/**
 * Created by ercan on 01.06.2017.
 */
public class DeepLearningEvaluator extends Evaluator {
    public AnswererWithDeepLearning answererWithDeepLearning = new AnswererWithDeepLearning();

    @Override
    public double evaluate(List<Question> questionList) {

        return processQuestions(answererWithDeepLearning, questionList);
    }
}
