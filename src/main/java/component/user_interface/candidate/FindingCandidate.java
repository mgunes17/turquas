package component.user_interface.candidate;

import db.dao.CandidateDAO;
import model.Question;
import model.QuestionForCompare;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mustafa on 11.05.2017.
 */
public class FindingCandidate {
    private CandidateDAO candidateDAO;

    public FindingCandidate() {
        candidateDAO = new CandidateDAO();
    }

    public List<QuestionForCompare> findCandidateList(String userQuestion, String w2vType) {
        //soruyu işle, etiket gerekirse bul, tokenlara ayır vs
        //önce ilgili dokümanlar, sonra ilgili cümleler (cümleler nasıl etiketlenebilir?)
        String[] words = userQuestion.split(" ");
        List<Question> questionList = candidateDAO.getQuestions(words);

        return createQuestionForCompares(questionList, w2vType); // sentence ve source işlemleri ortak olarak candidate daoda olacak
    }

    private List<QuestionForCompare> createQuestionForCompares(List<Question> questionList, String w2vType){
        List<QuestionForCompare> questionForCompareList = new ArrayList<>();

        for(Question question: questionList){
            QuestionForCompare qfc = new QuestionForCompare();
            qfc.setQuestion(question.getQuestion());
            qfc.setQuestionVector(question.getQuestionW2vValueMap().get(w2vType).
                    stream().mapToDouble(Double::doubleValue).toArray());
            qfc.setAnswer(question.getAnswer());
            qfc.setAnswerVector(question.getAnswerW2vValueMap().get(w2vType).
                    stream().mapToDouble(Double::doubleValue).toArray());
            questionForCompareList.add(qfc);
        }

        return questionForCompareList;
    }
}
