package db.accessor;

import com.datastax.driver.core.Statement;
import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Query;
import model.UniqueWord;

import java.util.Map;
import java.util.Set;

/**
 * Created by mustafa on 13.05.2017.
 */
@Accessor
public interface UniqueWordAccessor {
    @Query("SELECT * FROM unique_word")
    Result<UniqueWord> getAllWords();

    @Query("UPDATE unique_word SET value = ? WHERE word = ?")
    Statement update(Map<String, Double> value, String word);

    @Query("UPDATE unique_word SET source = source + ? WHERE word = ?")
    Statement updateSources(Set<String> sourceSet, String word);
}
