package w2v_operation.vector_operation;

import component.user_interface.w2vtoken.W2VTokenMap;
import model.QuestionForCompare;
import model.W2VToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by mustafa on 11.05.2017.
 */
public abstract class VectorType {
    protected Map<String, Map<String, W2VToken>> w2VTokens;

    public abstract List<Double> findValue(String sentence, String tokenType);
    public abstract void prepareQuestionVector(List<QuestionForCompare> questionList, String tokenType);

    public VectorType() {
        w2VTokens = W2VTokenMap.getW2VTokenMap();
    }

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
