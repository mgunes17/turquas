package model;

import java.util.List;
import java.util.Set;

/**
 * Created by ercan on 09.04.2017.
 */
public class Sentence {
    private String originalSentence;
    private String sourceName;
    private Set<String> questions;
    private List<String> stemmedWordsList;
    private List<String> tokenList;
    private Set<String> tags;

    public Sentence(String sentence){
        this.originalSentence = sentence;
    }

    public Sentence(){
        // non-args
    }

    // getters and setters

    public String getOriginalSentence() {
        return originalSentence;
    }

    public void setOriginalSentence(String originalSentence) {
        this.originalSentence = originalSentence;
    }

    public Set<String> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<String> questions) {
        this.questions = questions;
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
