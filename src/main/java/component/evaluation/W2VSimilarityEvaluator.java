package component.evaluation;

import component.user_interface.answerer.AnswererWithVectorSimilarity;
import model.Question;
import model.QuestionForCompare;

import java.util.List;

/**
 * Created by ercan on 01.06.2017.
 */
public class W2VSimilarityEvaluator extends Evaluator {

    @Override
    public double evaluate(List<Question> questionList) {
        double score = 0.0;
        AnswererWithVectorSimilarity answererWithVectorSimilarity = new AnswererWithVectorSimilarity();

        for(Question question: questionList){
            answererWithVectorSimilarity.answer(question.getQuestion());
            QuestionForCompare userQuestion = answererWithVectorSimilarity.getUserQuestion();
            score += findScore(question.getAnswer(), userQuestion);
        }

        return score / questionList.size();
    }
}
