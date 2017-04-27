package model;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by ercan on 09.04.2017.
 */
public class Source {
    private String sourceName;
    private Set<String> bestWords;
    private Timestamp lastUpdatedDate;
    private Map<String, Integer> wordCountMap;
    private Set<Sentence> sentenceSet;

    {
        sentenceSet = new HashSet<Sentence>();
    }

    public Source(String sourceName, Map<String, Integer> wordCount){
        this.sourceName = sourceName;
        wordCountMap = wordCount;

    }

    public Source(String sourceName){
        this.sourceName = sourceName;
        wordCountMap = new HashMap<String, Integer>();
    }


    public void updateWordCountMap(Map<String, Integer> map){
        for(String word: map.keySet()){
                if(wordCountMap.get(word) != null){
                    int newCount = wordCountMap.get(word) + 1;
                    wordCountMap.put(word, newCount);
                } else {
                    wordCountMap.put(word, 1);
                }
        }
    }


    // getters and setters
    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public Set<String> getBestWords() {
        return bestWords;
    }

    public void setBestWords(Set<String> bestWords) {
        this.bestWords = bestWords;
    }

    public Timestamp getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Timestamp lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Map<String, Integer> getWordCountMap() {
        return wordCountMap;
    }

    public void setWordCountMap(Map<String, Integer> wordCountMap) {
        this.wordCountMap = wordCountMap;
    }

    public Set<Sentence> getSentenceSet() {
        return sentenceSet;
    }

    public void setSentenceSet(Set<Sentence> sentenceSet) {
        this.sentenceSet = sentenceSet;
    }
}
