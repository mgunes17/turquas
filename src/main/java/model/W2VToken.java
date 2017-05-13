package model;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import db.configuration.ModelVariables;

import java.util.List;

/**
 * Created by mustafa on 09.05.2017.
 */

@Table(keyspace = ModelVariables.KEYSPACE, name = ModelVariables.W2V_TOKEN_TABLE_NAME)
public class W2VToken {
    @PartitionKey
    @Column(name = "token_name")
    private String tokenName;

    @Column(name = "stem")
    private boolean stem;

    @Column(name = "value")
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
        return stem;
    }

    public void setStem(boolean stem) {
        this.stem = stem;
    }

    public List<Double> getValue() {
        return value;
    }

    public void setValue(List<Double> value) {
        this.value = value;
    }
}
