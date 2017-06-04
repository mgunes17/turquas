package db.accessor;

import com.datastax.driver.core.Statement;
import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Query;
import model.Sentence;

import java.util.List;
import java.util.Set;

/**
 * Created by ercan on 13.05.2017.
 */
@Accessor
public interface SentenceAccessor {
    @Query("SELECT * FROM sentence")
    Result<Sentence> getAll();

    @Query("SELECT original_sentence FROM sentence")
    Result<Sentence> getAllOnly();

    @Query("SELECT original_sentence FROM sentence where original_sentence = ?")
    Result<Sentence> getSentence(String originalSentence);

    @Query("SELECT original_sentence FROM sentence LIMIT ?")
    Result<Sentence> getOnlySentenceWithLimit(int limit);

    @Query("SELECT * FROM sentence LIMIT ?")
    Result<Sentence> getSentencesByLimit(int limit);

    @Query("INSERT INTO sentence (original_sentence, source_name, " +
            "stemmed_words_list, tags, token_list) VALUES (?, ?, ?, ?, ?)")
    Statement insertBatch(String originalSentence, String sourceName,
                          List<String> stemmedWordsList, Set<String> tags, List<String> tokenList);
}
