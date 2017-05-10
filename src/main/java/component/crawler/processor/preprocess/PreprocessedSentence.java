package component.crawler.processor.preprocess;

import java.util.List;
import java.util.Map;

/**
 * Created by ercan on 09.04.2017.
 */
public class PreprocessedSentence {
    private String originalSentence;
    private List<String> stemList;
    private List<String> tokenList;
    private Map<String, Integer> wordCountMap;

    PreprocessedSentence(String sentence){
        this.originalSentence = sentence;
    }

    public PreprocessedSentence(){

    }

    // getters and setters
    public String getOriginalSentence() {
        return originalSentence;
    }

    public void setOriginalSentence(String originalSentence) {
        this.originalSentence = originalSentence;
    }

    public List<String> getStemList() {
        return stemList;
    }

    public void setStemList(List<String> stemList) {
        this.stemList = stemList;
    }

    public Map<String, Integer> getWordCountMap() {
        return wordCountMap;
    }

    public void setWordCountMap(Map<String, Integer> wordCountMap) {
        this.wordCountMap = wordCountMap;
    }

    public List<String> getTokenList() {
        return tokenList;
    }

    public void setTokenList(List<String> tokenList) {
        this.tokenList = tokenList;
    }
}
