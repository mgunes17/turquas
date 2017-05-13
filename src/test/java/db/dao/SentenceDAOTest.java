package db.dao;

import model.Sentence;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by ercan on 13.05.2017.
 */
public class SentenceDAOTest {
    @Test
    public void getAllSentences() throws Exception {
        SentenceDAO sentenceDAO = new SentenceDAO();
        List<Sentence> sentenceList = sentenceDAO.getAllSentences();

        assertEquals(250, sentenceList.size());
    }

}