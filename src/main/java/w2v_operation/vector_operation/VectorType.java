package w2v_operation.vector_operation;

import model.W2VToken;

import java.util.List;
import java.util.Map;

/**
 * Created by mustafa on 11.05.2017.
 */
public abstract class VectorType {
    public abstract List<Double> findValue(String sentence, String tokenType, Map<String, W2VToken> w2VTokens);

}
