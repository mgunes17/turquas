package db.dao;

import com.datastax.driver.core.Session;
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
}
