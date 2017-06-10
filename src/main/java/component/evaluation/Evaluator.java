package component.evaluation;

import admin.UserInterfaceAdmin;
import component.user_interface.answerer.QuestionAnswerer;
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
            //System.out.println(i + "gerçek----" + answer.toLowerCase());
            //System.out.println(i + "tahmin++++" + qfc.getAnswer().toLowerCase());
            if(answer.toLowerCase().equals(qfc.getAnswer().toLowerCase())){
                System.out.println("found " + (i+1));
                return 1.0 / (i+1);
            }
        }

        return 0;
    }

    public double processQuestions(QuestionAnswerer questionAnswerer, List<Question> questionList){
        double score = 0.0;
        int unansweredQuestions = 0;

        for(Question question: questionList){
            questionAnswerer.answer(question.getQuestion());
            QuestionForCompare userQuestion = questionAnswerer.getUserQuestion();
            double questionScore = findScore(question.getAnswer(), userQuestion);
            if(questionScore == 0){
                unansweredQuestions++;
            } else {
                score += questionScore;
            }

        }
        System.out.println("BULUNAMAYIN SORU SAYISI  " + unansweredQuestions + "/" + questionList.size());

        return score / questionList.size();
    }
}
