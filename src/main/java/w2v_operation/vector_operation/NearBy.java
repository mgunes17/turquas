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
public class NearBy implements VectorType {
    public void prepareVector(Map<String, List<String>> convertedSentences, Map<List<Float>, List<List<Float>>> w2vValues,
                              Map<String, W2VToken> w2VTokens) {
        for(String sentence: convertedSentences.keySet()) { //her bir cümle için
            List<Float> sentenceValue = findNearValue(sentence, w2VTokens);
            List<List<Float>> questionsValues = new ArrayList<List<Float>>();

            for(String question: convertedSentences.get(sentence)) { // her bir soru için
                List<Float> value = findNearValue(question, w2VTokens);
                questionsValues.add(value);
            }

            w2vValues.put(sentenceValue, questionsValues);
        }
    }

    private List<Float> findNearValue(String sentence, Map<String, W2VToken> w2VTokens) {
        int maxWordSize = CrawlerAdmin.crawlerParameterMap.get("max_word_size");
        int layerSize = W2VCreatorAdmin.w2vParameterMap.get("layer_size");

        List<Float> values = new ArrayList<Float>();
        String[] words = sentence.split(" ");

        for(String word: words) {
            if (w2VTokens.containsKey(word)) {
                for(Float value: w2VTokens.get(word).getValue()) {
                    values.add(value);
                }
            } else {
                for(int i = 0; i < layerSize; i++) {
                    values.add((float) 0);
                }
            }
        }

        //max kelime sayısına kadar 0 koy
        for(int i = words.length; i < maxWordSize; i++) {
            for(int j = 0; j < layerSize; j++) {
                values.add((float) 0);
            }
        }

        return values;
    }
}
