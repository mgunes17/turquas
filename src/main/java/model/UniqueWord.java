package model;

import java.util.Map;
import java.util.Set;

/**
 * Created by mustafa on 26.04.2017.
 */
public class UniqueWord {
    private String word;
    private Map<String, Double> valueMap;
    private Set<String> documentSet;

    public UniqueWord(String word) {
        this.word = word;
    }

    public UniqueWord(String word, Set<String> documentSet) {
        this.word = word;
        this.documentSet = documentSet;
    }

    //getter - setter
    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Map<String, Double> getValueMap() {
        return valueMap;
    }

    public void setValueMap(Map<String, Double> valueMap) {
        this.valueMap = valueMap;
    }

    public Set<String> getDocumentSet() {
        return documentSet;
    }

    public void setDocumentSet(Set<String> documentSet) {
        this.documentSet = documentSet;
    }
}
