package w2v_operation.vector_operation;

import admin.CrawlerAdmin;
import admin.W2VCreatorAdmin;
import model.W2VToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by mustafa on 11.05.2017.
 */
public class NearBy extends VectorType {

    public List<Double> findValue(String sentence, String tokenType, Map<String, W2VToken> w2VTokens) {
        int maxWordSize = CrawlerAdmin.crawlerParameterMap.get("max_word_size");
        int layerSize = W2VCreatorAdmin.w2vParameterMap.get("layer_size");

        List<Double> values = new ArrayList<Double>();
        String[] words = sentence.split(" ");
        //Map<String, W2VToken> w2VTokens = super.w2VTokens.get(tokenType);

        for(String word: words) {
            if (w2VTokens.containsKey(word)) {
                values.addAll(w2VTokens.get(word).getValue());
            } else {
                for(int i = 0; i < layerSize; i++) {
                    values.add((double) 0);
                }
            }
        }

        //max kelime sayısına kadar 0 koy
        for(int i = words.length; i < maxWordSize; i++) {
            for(int j = 0; j < layerSize; j++) {
                values.add((double) 0);
            }
        }

        return values;
    }
}
