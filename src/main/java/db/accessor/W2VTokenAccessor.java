package db.accessor;

import com.datastax.driver.core.Statement;
import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Query;
import model.W2VToken;

import java.util.List;

/**
 * Created by mustafa on 13.05.2017.
 */
@Accessor
public interface W2VTokenAccessor {
    @Query("INSERT INTO w2v_token (token_name, type, value) values (?, ?, ?)")
    Statement insert(String tokenName, String type, List<Double> values);

    @Query("select token_name, value from w2v_token WHERE type = ? ")
    Result<W2VToken> getToken(String type);

    @Query("select * from w2v_token WHERE token_name IN ? AND type = ? ")
    Result<W2VToken> getW2VTokensByType(List<String> words, String type);

    @Query("select * from w2v_token WHERE token_name IN ?")
    Result<W2VToken> getW2VTokensForAllTypes(List<String> words);
}
