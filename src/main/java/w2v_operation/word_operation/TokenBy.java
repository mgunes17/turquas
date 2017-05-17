package w2v_operation.word_operation;

import org.antlr.v4.runtime.Token;
import zemberek.tokenization.TurkishTokenizer;

import java.util.Iterator;

/**
 * Created by ercan on 17.05.2017.
 */
public class TokenBy extends WordType {
    private TurkishTokenizer tokenizer = TurkishTokenizer.DEFAULT;

    public TokenBy(){
        super();
    }

    @Override
    public String rebuildSentence(String sentence) {
        Iterator<Token> tokenIterator = tokenizer.getTokenIterator(sentence);
        StringBuilder newSentence = new StringBuilder();

        while (tokenIterator.hasNext()) {
            Token token = tokenIterator.next();
            newSentence.append(token.getText()).append(" ");
        }

        return newSentence.toString();
    }
}
