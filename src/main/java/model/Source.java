package model;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import com.datastax.driver.mapping.annotations.Transient;
import db.configuration.ModelVariables;

import java.util.*;

/**
 * Created by ercan on 09.04.2017.
 */
@Table(keyspace = ModelVariables.KEYSPACE, name = ModelVariables.SOURCE_TABLE_NAME)
public class Source {
    @PartitionKey
    @Column(name = "source_name")
    private String sourceName;

    @Column(name = "best_words")
    private Set<String> bestWords;

    @Column(name = "last_updated_date")
    private Date lastUpdatedDate;

    @Column(name = "word_count_map")
    private Map<String, Integer> wordCountMap;

    @Transient
    private Set<Sentence> sentenceSet;

    {
        sentenceSet = new HashSet<Sentence>();
    }

    public Source(){
        // non-args
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

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
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
