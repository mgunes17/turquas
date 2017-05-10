package w2v_operation.word_operation;

import model.Sentence;
import org.antlr.v4.runtime.Token;
import zemberek.tokenization.TurkishTokenizer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by mustafa on 11.05.2017.
 */
public class LetterBy extends WordType {
    private TurkishTokenizer tokenizer = TurkishTokenizer.DEFAULT;

    public LetterBy() {
        super();
    }

    public void prepareWord(Map<String, List<String>> convertedSentences) {
        for(Sentence sentence: getSentences()) {
            String sentenceWord = rebuildSentenceByLetter(sentence.getOriginalSentence());
            List<String> questions = new ArrayList<String>();

            for (String question : sentence.getQuestions()) {
                questions.add(rebuildSentenceByLetter(question));
            }

            convertedSentences.put(sentenceWord, questions);
        }
    }

    private String rebuildSentenceByLetter(String sentence) {
        Iterator<Token> tokenIterator = tokenizer.getTokenIterator(sentence);
        StringBuilder newSentence = new StringBuilder();

        while (tokenIterator.hasNext()) {
            Token token = tokenIterator.next();
            newSentence.append(getFirst5Letter(token.getText()) + " ");
        }

        return newSentence.toString();
    }

    private String getFirst5Letter(String text) {
        if (text.length() >= 5)
            return text.substring(0, 5);
        else
            return text;
    }
}
