package component.crawler.processor.preprocess;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ercan on 09.04.2017.
 */
public class UniqueWordPreprocessor extends Preprocessor{

    public PreprocessedSentence process(PreprocessedSentence preprocessedSentence) {
        Map<String, Integer> wordCountMap = createMapFromStemList(preprocessedSentence.getStemList());
        preprocessedSentence.setWordCountMap(wordCountMap);

        return proceedToNext(preprocessedSentence);
    }

    private Map<String, Integer> createMapFromStemList(List<String> stemList){
        Map<String, Integer> wordCountMap = new HashMap<String, Integer>();

        for(String word: stemList){
            if(wordCountMap.get(word) != null){
                int newCount = wordCountMap.get(word) + 1;
                wordCountMap.put(word, newCount);
            } else {
                wordCountMap.put(word, 1);
            }
        }

        return wordCountMap;
    }
}
