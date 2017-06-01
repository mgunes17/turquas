package component.evaluation;

import admin.UserInterfaceAdmin;
import model.Question;
import model.QuestionForCompare;

import java.util.List;

/**
 * Created by ercan on 01.06.2017.
 */
public abstract class Evaluator {

    public abstract double evaluate(List<Question> questions);

    double findScore(String answer, QuestionForCompare userQuestion){
        int maxCount = UserInterfaceAdmin.parameterMap.get("evaluate_count");

        for(int i = 0; i < maxCount; i++){
            if(answer.equals(userQuestion.getSimilarityList().get(i).getQuestionForCompare().getAnswer())){
                return (maxCount - i) / maxCount;
            }
        }

        return 0;
    }
}
