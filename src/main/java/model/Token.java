package model;

import java.util.Set;

/**
 * Created by mustafa on 26.04.2017.
 */
public class Token {
    private String token;
    private Set<String> analysisSet;

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
}
