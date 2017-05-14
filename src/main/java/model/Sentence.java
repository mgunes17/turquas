package model;

import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import db.configuration.ModelVariables;

import java.util.List;
import java.util.Set;

/**
 * Created by ercan on 09.04.2017.
 */
@Table(keyspace = ModelVariables.KEYSPACE, name = ModelVariables.SENTENCE_TABLE_NAME)
public class Sentence {
    @PartitionKey
    @Column(name = "source_name")
    private String sourceName;

    @ClusteringColumn
    @Column(name = "original_sentence")
    private String originalSentence;

    @Column(name = "stemmed_words_list")
    private List<String> stemmedWordsList;

    @Column(name = "tags")
    private Set<String> tags;

    @Column(name = "token_list")
    private List<String> tokenList;

    public Sentence(){
        // non-args
    }

    public Sentence(String sentence){
        this.originalSentence = sentence;
    }

    // getters and setters

    public String getOriginalSentence() {
        return originalSentence;
    }

    public void setOriginalSentence(String originalSentence) {
        this.originalSentence = originalSentence;
    }

    public List<String> getStemmedWordsList() {
        return stemmedWordsList;
    }

    public void setStemmedWordsList(List<String> stemmedWordsList) {
        this.stemmedWordsList = stemmedWordsList;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public List<String> getTokenList() {
        return tokenList;
    }

    public void setTokenList(List<String> tokenList) {
        this.tokenList = tokenList;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }
}
