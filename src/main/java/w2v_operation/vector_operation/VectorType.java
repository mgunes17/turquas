package w2v_operation.vector_operation;

import model.QuestionForCompare;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by mustafa on 11.05.2017.
 */
public abstract class VectorType {
    public abstract List<Double> findValue(String sentence, String tokenType);
    public abstract void prepareQuestionVector(List<QuestionForCompare> questionList, String tokenType);

    public void prepareVector(Map<String, List<String>> convertedSentences, Map<List<Double>, List<List<Double>>> w2vValues,
                              String tokenType) {
        for(String sentence: convertedSentences.keySet()) { //her bir cümle için
            List<Double> sentenceValue = findValue(sentence, tokenType);
            List<List<Double>> questionsValues = new ArrayList<List<Double>>();

            for(String question: convertedSentences.get(sentence)) { // her bir soru için
                List<Double> value = findValue(question, tokenType);
                questionsValues.add(value);
            }

            w2vValues.put(sentenceValue, questionsValues);
        }
    }
}
