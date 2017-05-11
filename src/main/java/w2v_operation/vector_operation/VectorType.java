package w2v_operation.vector_operation;

import model.W2VToken;

import java.util.List;
import java.util.Map;

/**
 * Created by mustafa on 11.05.2017.
 */
public interface VectorType {
    void prepareVector(Map<String, List<String>> convertedSentences, Map<List<Double>, List<List<Double>>> w2vValues,
                       String wordType);
}
