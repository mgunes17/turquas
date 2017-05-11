package w2v_operation.vector_operation;

import admin.CrawlerAdmin;
import admin.W2VCreatorAdmin;
import home_base.Turquas;
import model.QuestionForCompare;
import model.W2VToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by mustafa on 11.05.2017.
 */
public class NearBy implements VectorType {
    public void prepareVector(Map<String, List<String>> convertedSentences, Map<List<Double>, List<List<Double>>> w2vValues,
                              String tokenType) {
        for(String sentence: convertedSentences.keySet()) { //her bir cümle için
            List<Double> sentenceValue = findNearValue(sentence, tokenType);
            List<List<Double>> questionsValues = new ArrayList<List<Double>>();

            for(String question: convertedSentences.get(sentence)) { // her bir soru için
                List<Double> value = findNearValue(question, tokenType);
                questionsValues.add(value);
            }

            w2vValues.put(sentenceValue, questionsValues);
        }
    }

    public void prepareQuestionVector(List<QuestionForCompare> questionList, String tokenType) {
        for (QuestionForCompare question : questionList) {
            List<Double> vectorList = findNearValue(question.getQuestion(), tokenType);
            double[] vector = vectorList.stream().mapToDouble(Double::doubleValue).toArray();
            question.setVector(vector);
        }
    }

    private List<Double> findNearValue(String sentence, String tokenType) {
        int maxWordSize = CrawlerAdmin.crawlerParameterMap.get("max_word_size");
        int layerSize = W2VCreatorAdmin.w2vParameterMap.get("layer_size");

        List<Double> values = new ArrayList<Double>();
        String[] words = sentence.split(" ");
        Map<String, W2VToken> w2VTokens = Turquas.getW2VToken(tokenType);

        for(String word: words) {
            if (w2VTokens.containsKey(word)) {
                for(Double value: w2VTokens.get(word).getValue()) {
                    values.add(value);
                }
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
