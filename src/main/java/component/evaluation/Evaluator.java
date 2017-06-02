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
        int size = userQuestion.getSimilarityList().size();

        maxCount = maxCount >= size ? size : maxCount;
        for(int i = 0; i < maxCount; i++){
            QuestionForCompare qfc = userQuestion.getSimilarityList().get(i).getQuestionForCompare();
            //System.out.println("----" + answer.toLowerCase());
            //System.out.println("++++" + qfc.getAnswer().toLowerCase());
            if(answer.toLowerCase().equals(qfc.getAnswer().toLowerCase())){
                System.out.println("found " + (i+1));
                return 1.0 / (i+1);
            }
        }

        return 0;
    }
}
