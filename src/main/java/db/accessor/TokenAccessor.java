package db.accessor;

import com.datastax.driver.core.Statement;
import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Query;
import model.Token;

import java.util.Set;

/**
 * Created by mustafa on 13.05.2017.
 */
@Accessor
public interface TokenAccessor {
    @Query("SELECT token_name FROM token_morph_analysis WHERE analysis_null = true LIMIT ?")
    Result<Token> getUnlabeledToken(int count);

    @Query("INSERT INTO token_morph_analysis (analysis_null, token_name, analysis) values (false, ?, ?)")
    Statement saveTMA(String tokenName, Set<String> analysis);

    @Query("DELETE FROM token_morph_analysis WHERE token_name = ?")
    Statement deleteTMA(String tokenName);
}
