package w2v_operation.vector_operation;

import admin.W2VCreatorAdmin;
import model.W2VToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by mustafa on 10.05.2017.
 */
public class AverageBy implements VectorType {

    public void prepareVector(Map<String, List<String>> convertedSentences, Map<List<Double>, List<List<Double>>> w2vValues,
                        Map<String, W2VToken> w2VTokens) {
        for(String sentence: convertedSentences.keySet()) { //her bir cümle için
            List<Double> sentenceValue = findAverageValue(sentence, w2VTokens);
            List<List<Double>> questionsValues = new ArrayList<List<Double>>();

            for(String question: convertedSentences.get(sentence)) { // her bir soru için
                List<Double> value = findAverageValue(question, w2VTokens);
                questionsValues.add(value);
            }

            w2vValues.put(sentenceValue, questionsValues);
        }
    }

    protected List<Double> findAverageValue(String sentence, Map<String, W2VToken> w2VTokens) {
        List<Double> values = new ArrayList<Double>();
        String[] words = sentence.split(" ");
        List<List<Double>> wordValues = new ArrayList<List<Double>>();

        for(String word : words) {
            if (w2VTokens.containsKey(word)) {
                wordValues.add(w2VTokens.get(word).getValue());
            }
        }

        for(int i = 0; i < W2VCreatorAdmin.w2vParameterMap.get("layer_size"); i++) {
            Double sum = 0.0d;

            for(List<Double> xx: wordValues) {
                sum += xx.get(i);
            }

            values.add(sum / wordValues.size());
        }

        return values;
    }
}
