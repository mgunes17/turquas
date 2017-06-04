package db.accessor;

import com.datastax.driver.core.Statement;
import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Query;
import model.Question;

import java.util.List;

/**
 * Created by mustafa on 14.05.2017.
 */
@Accessor
public interface QuestionAccessor {
    @Query("SELECT * FROM question WHERE source_name IN ? AND noun_clause = ?")
    Result<Question> getQuestionsWithInClause(List<String> sources, boolean nounClause);

    @Query("SELECT * FROM question")
    Result<Question> getAll();

    @Query("SELECT * FROM question LIMIT ?")
    Result<Question> getQuestionsByLimit(int limit);

    @Query("SELECT * FROM question where processed = false LIMIT ?")
    Result<Question> getUnprocessedQuestionsByLimit(int limit);

    @Query("SELECT * FROM question where processed = true LIMIT ?")
    Result<Question> getProcessedQuestionsByLimit(int limit);

    @Query("UPDATE question SET processed = ? where source_name = ? and noun_clause = ? and question = ?")
    Statement updateQuestionProcessed(boolean isProcessed, String sourceName, boolean nounClause, String question);
}
