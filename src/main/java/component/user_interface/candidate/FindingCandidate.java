package component.user_interface.candidate;

import db.dao.CandidateDAO;
import model.QuestionForCompare;
import model.Sentence;

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

    public List<QuestionForCompare> findCandidateList(String userQuestion) {
        //soruyu işle, etiket gerekirse bul, tokenlara ayır vs
        //önce ilgili dokümanlar, sonra ilgili cümleler (cümleler nasıl etiketlenebilir?)
        List<Sentence> sentenceList = candidateDAO.getSentences();

        return createQuestionForCompares(sentenceList); // sentence ve source işlemleri ortak olarak candidate daoda olacak
    }

    private List<QuestionForCompare> createQuestionForCompares(List<Sentence> sentenceList){
        List<QuestionForCompare> questionForCompareList = new ArrayList<>();

        for(Sentence sentence: sentenceList){
            if(sentence.getQuestions() != null){
                for(String question: sentence.getQuestions()){
                    QuestionForCompare qfc = new QuestionForCompare();
                    qfc.setAnswer(sentence.getOriginalSentence());
                    qfc.setQuestion(question);
                    questionForCompareList.add(qfc);
                }
            }
        }

        return questionForCompareList;
    }
}
