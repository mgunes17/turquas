package w2v_operation.vector_operation;

import admin.W2VCreatorAdmin;
import model.W2VToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by mustafa on 10.05.2017.
 */
public class AverageBy extends VectorType {

    public List<Double> findValue(String sentence, String tokenType, Map<String, W2VToken> w2VTokens) {
        List<Double> values = new ArrayList<Double>();
        String[] words = sentence.split(" ");
        List<List<Double>> wordValues = new ArrayList<List<Double>>();
        //Map<String, W2VToken> w2VTokens = super.w2VTokens.get(tokenType);

        for(String word : words) {
            if (w2VTokens.containsKey(word)) {
                wordValues.add(w2VTokens.get(word).getValue());
            }
        }

        int layerSize = W2VCreatorAdmin.w2vParameterMap.get("layer_size");
        int wordValuesSize = wordValues.size();

        for(int i = 0; i < layerSize; i++) {
            Double sum = 0.0d;

            for(List<Double> xx: wordValues) {
                sum += xx.get(i);
            }

            values.add(sum / wordValuesSize);
        }

        return values;
    }
}
