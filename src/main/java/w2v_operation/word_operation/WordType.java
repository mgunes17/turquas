package w2v_operation.word_operation;

import db.dao.SentenceDAO;
import model.Sentence;

import java.util.List;
import java.util.Map;

/**
 * Created by mustafa on 11.05.2017.
 */
public abstract class WordType {
    private List<Sentence> sentences;

    public abstract void prepareWord(Map<String, List<String>> convertedSentences);

    public WordType() {
        sentences = new SentenceDAO().getAllSentences();
    }

    public List<Sentence> getSentences() {
        return sentences;
    }
}
