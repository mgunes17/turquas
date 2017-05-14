package db.dao;

import model.Question;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by ercan on 14.05.2017.
 */
public class CandidateDAOTest {
    @Test
    public void getSentences() throws Exception {
        CandidateDAO candidateDAO = new CandidateDAO();
        String[] words = new String[2];
        words[0] = "sokmak";
        words[1] = "kocaman";
        List<Question> sentenceList = candidateDAO.getQuestions(words);

        assertEquals(137, sentenceList.size());
    }

}