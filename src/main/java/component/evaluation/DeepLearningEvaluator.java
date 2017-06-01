package component.evaluation;

import component.user_interface.answerer.AnswererWithDeepLearning;
import model.Question;
import model.QuestionForCompare;

import java.util.List;

/**
 * Created by ercan on 01.06.2017.
 */
public class DeepLearningEvaluator extends Evaluator {

    @Override
    public double evaluate(List<Question> questionList) {
        double score = 0.0;
        AnswererWithDeepLearning answererWithDeepLearning = new AnswererWithDeepLearning();

        for(Question question: questionList){
            answererWithDeepLearning.answer(question.getQuestion());
            QuestionForCompare userQuestion = answererWithDeepLearning.getUserQuestion();
            score += findScore(question.getAnswer(), userQuestion);
        }

        return score / questionList.size();
    }
}
