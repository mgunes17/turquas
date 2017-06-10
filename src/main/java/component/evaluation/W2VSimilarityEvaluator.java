package component.evaluation;

import component.user_interface.answerer.AnswererWithVectorSimilarity;
import model.Question;
import model.QuestionForCompare;

import java.util.List;

/**
 * Created by ercan on 01.06.2017.
 */
public class W2VSimilarityEvaluator extends Evaluator {
    public AnswererWithVectorSimilarity answererWithVectorSimilarity = new AnswererWithVectorSimilarity();

    @Override
    public double evaluate(List<Question> questionList) {

        return processQuestions(answererWithVectorSimilarity, questionList);
    }
}
