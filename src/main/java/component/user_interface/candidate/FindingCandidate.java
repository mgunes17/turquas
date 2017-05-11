package component.user_interface.candidate;

import db.dao.CandidateDAO;
import db.dao.SentenceDAO;
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
        return new ArrayList<QuestionForCompare>(); // sentence ve source işlemleri ortak olarak candidate daoda olacak
    }
}
