package db.accessor;

import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Query;
import model.Sentence;

/**
 * Created by ercan on 13.05.2017.
 */
@Accessor
public interface SentenceAccessor {

    @Query("SELECT * FROM sentence")
    Result<Sentence> getAll();
}
