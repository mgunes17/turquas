package db.accessor;

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
    @Query("SELECT * FROM question WHERE source_name IN ?")
    Result<Question> getQuestionsWithInClause(List<String> sources);

    @Query("SELECT * FROM question")
    Result<Question> getAll();

    @Query("SELECT * FROM question LIMIT ?")
    Result<Question> getQuestionsByLimit(int limit);
}
