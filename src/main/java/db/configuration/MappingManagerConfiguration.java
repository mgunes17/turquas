package db.configuration;

import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import model.Question;

/**
 * Created by mustafa on 13.05.2017.
 */
public class MappingManagerConfiguration {
    private Session session;
    private MappingManager manager;
    private Mapper<Question> questionMapper;

    public MappingManagerConfiguration() {
        manager = new MappingManager(
                ConnectionConfiguration.getSession());
        questionMapper = manager.mapper(Question.class);
    }

    public Mapper<Question> getQuestionMapper() {
        return questionMapper;
    }
}
