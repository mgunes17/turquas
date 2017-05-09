package model;

import java.util.List;

/**
 * Created by mustafa on 09.05.2017.
 */
public class W2VToken {
    private String tokenName;
    private boolean isStem;
    private List<Float> value;

    public W2VToken() {
        //non - args
    }

    public W2VToken(String tokenName, List<Float> value) {
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

    public List<Float> getValue() {
        return value;
    }

    public void setValue(List<Float> value) {
        this.value = value;
    }
}
