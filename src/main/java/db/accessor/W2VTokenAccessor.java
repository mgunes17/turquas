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
    @Query("INSERT INTO w2v_token (token_name, is_stem, value) values (?, ?, ?)")
    Statement updateTable(String tokenName, boolean stem, List<Double> values);

    @Query("select token_name, value from w2v_token WHERE stem = ? ")
    Result<W2VToken> getToken(boolean stem);
}
