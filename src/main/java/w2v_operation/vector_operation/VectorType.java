package w2v_operation.vector_operation;

import model.QuestionForCompare;

import java.util.List;
import java.util.Map;

/**
 * Created by mustafa on 11.05.2017.
 */
public interface VectorType {
    void prepareVector(Map<String, List<String>> convertedSentences, Map<List<Double>, List<List<Double>>> w2vValues,
                       String wordType);
    void prepareQuestionVector(List<QuestionForCompare> questionList, String tokenType);
}
