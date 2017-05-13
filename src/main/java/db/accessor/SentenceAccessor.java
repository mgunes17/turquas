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

    @Query("SELECT * FROM sentence LIMIT ?")
    Result<Sentence> getSentencesByLimit(int limit);

    @Query("UPDATE sentence SET questions= ? WHERE source_name = ? and original_sentence = ?")
    Statement updateQuestions(Set<String> questions, String sourceName, String originalSentence);

    @Query("INSERT INTO sentence (original_sentence, source_name, questions, " +
            "stemmed_words_list, tags, token_list) VALUES (?, ?, ?, ?, ?, ?)")
    Statement insertBatch(String originalSentence, String sourceName, Set<String> questions,
                          List<String> stemmedWordsList, Set<String> tags, List<String> tokenList);

    @Query("SELECT * FROM sentence WHERE source_name IN ?")
    Result<Sentence> getSentencesWithInClause(List<String> sources);
}
