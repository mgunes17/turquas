package db.dao;

import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.Result;
import db.accessor.QuestionAccessor;
import db.configuration.ConnectionConfiguration;
import db.configuration.MappingManagerConfiguration;
import model.Question;

import java.util.List;

/**
 * Created by mustafa on 14.05.2017.
 */
public class QuestionDAO {
    private Session session;
    private static final QuestionAccessor questionAccessor = MappingManagerConfiguration
            .getMappingManager()
            .createAccessor(QuestionAccessor.class);

    public QuestionDAO(){
        session = ConnectionConfiguration.getSession();
    }

    public void saveQuestionList(List<Question> questionList) {
        Mapper<Question> questionMapper = MappingManagerConfiguration.getQuestionMapper();

        for(Question question: questionList) {
            questionMapper.save(question);
        }
    }

    public List<Question> getAllQuestions() {
        Result<Question> result = questionAccessor.getAll();
        return result.all();
    }

    public List<Question> getUnprocessedQuestions(int limit) {
        Result<Question> result = questionAccessor.getUnprocessedQuestionsByLimit(limit);
        return result.all();
    }

    public List<Question> getQuestionsByLimit(int limit) {
        try {
            Result<Question> result = questionAccessor.getQuestionsByLimit(limit);
            return result.all();
        } catch(Exception ex){
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        return null;
    }

    public void updateQuestionProcessed(List<Question> questionList, boolean isProcessed) {
        try {
            for(Question question: questionList){
                Statement statement = questionAccessor.updateQuestionProcessed(isProcessed, question.getSourceName(),
                        question.isNounClause(), question.getQuestionType(), question.getQuestion());
                session.execute(statement);
            }
        } catch(Exception ex){
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public List<Question> getProcessedQuestionsByLimit(int limit){
        Result<Question> result = questionAccessor.getProcessedQuestionsByLimit(limit);
        return result.all();
    }
}
