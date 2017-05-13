package model;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import db.configuration.ModelVariables;

import java.util.Map;
import java.util.Set;

/**
 * Created by mustafa on 26.04.2017.
 */
@Table(keyspace = ModelVariables.KEYSPACE, name = ModelVariables.UNIQUE_WORD_TABLE_NAME)
public class UniqueWord {
    @PartitionKey
    @Column(name = "word")
    private String word;

    @Column(name = "value")
    private Map<String, Double> valueMap;

    @Column(name = "source")
    private Set<String> documentSet;

    public UniqueWord(){
        // non-args
    }

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
