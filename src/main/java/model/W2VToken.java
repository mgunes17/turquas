package model;

import java.util.List;

/**
 * Created by mustafa on 09.05.2017.
 */
public class W2VToken {
    private String tokenName;
    private boolean isStem;
    private List<Double> value;

    public W2VToken() {
        //non - args
    }

    public W2VToken(String tokenName, List<Double> value) {
        this.tokenName = tokenName;
        this.value = value;
    }

    //getter - setter
    public String getTokenName() {
        return tokenName;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    public boolean isStem() {
        return isStem;
    }

    public void setStem(boolean stem) {
        isStem = stem;
    }

    public List<Double> getValue() {
        return value;
    }

    public void setValue(List<Double> value) {
        this.value = value;
    }
}
