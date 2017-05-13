package db.accessor;

import com.datastax.driver.core.Statement;
import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Query;
import model.Source;

import java.util.Date;
import java.util.Map;

/**
 * Created by ercan on 13.05.2017.
 */
@Accessor
public interface SourceAccessor {
    @Query("SELECT * FROM source")
    Result<Source> getAll();

    @Query("INSERT INTO source (source_name, last_updated_date, word_count_map) values (?, ?, ?)")
    Statement insertBatch(String sourceName, Date updatedDate, Map<String, Integer> wordCountMap);
}
