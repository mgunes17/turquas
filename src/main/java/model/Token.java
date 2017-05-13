package model;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import db.configuration.ModelVariables;

import java.util.Set;

/**
 * Created by mustafa on 26.04.2017.
 */

@Table(keyspace = ModelVariables.KEYSPACE, name = ModelVariables.TOKEN_MORPH_ANALYSIS_TABLE_NAME)
public class Token {
    @PartitionKey
    @Column(name = "token_name")
    private String token;

    @Column(name = "analysis_null")
    private boolean analysisNull;

    @Column(name = "analysis")
    private Set<String> analysisSet;

    public Token() {
        //non - arg
    }

    public Token(String token) {
        this.token = token;
    }

    public void setAnalysisSet(Set<String> analysisSet) {
        this.analysisSet = analysisSet;
    }

    public String getToken() {
        return token;
    }

    public Set<String> getAnalysisSet() {
        return analysisSet;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isAnalysisNull() {
        return analysisNull;
    }

    public void setAnalysisNull(boolean analysisNull) {
        this.analysisNull = analysisNull;
    }
}
