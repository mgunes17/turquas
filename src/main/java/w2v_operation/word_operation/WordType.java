package w2v_operation.word_operation;

import db.dao.SentenceDAO;
import model.Sentence;

import java.util.List;

/**
 * Created by mustafa on 11.05.2017.
 */
public abstract class WordType {
    private List<Sentence> sentences;

    public abstract String rebuildSentence(String sentence);

    List<Sentence> getSentences() {
        sentences = new SentenceDAO().getAllSentences();

        return sentences;
    }
}
