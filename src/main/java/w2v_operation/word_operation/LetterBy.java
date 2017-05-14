package w2v_operation.word_operation;

import org.antlr.v4.runtime.Token;
import zemberek.tokenization.TurkishTokenizer;

import java.util.Iterator;

/**
 * Created by mustafa on 11.05.2017.
 */
public class LetterBy extends WordType {
    private TurkishTokenizer tokenizer = TurkishTokenizer.DEFAULT;

    public LetterBy() {
        super();
    }

    public String rebuildSentence(String sentence) {
        Iterator<Token> tokenIterator = tokenizer.getTokenIterator(sentence);
        StringBuilder newSentence = new StringBuilder();

        while (tokenIterator.hasNext()) {
            Token token = tokenIterator.next();
            newSentence.append(getFirst5Letter(token.getText())).append(" ");
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
