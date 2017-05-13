package db.configuration;

import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import model.*;

/**
 * Created by mustafa on 13.05.2017.
 */
public class MappingManagerConfiguration {
    private static MappingManager manager = null;
    private Mapper<Question> questionMapper;
    private Mapper<Sentence> sentenceMapper;
    private Mapper<Source> sourceMapper;
    private Mapper<Token> tokenMapper;
    private Mapper<W2VToken> w2VTokenMapper;
    private Mapper<UniqueWord> uniqueWordMapper;
    private static MappingManagerConfiguration mappingManagerConfiguration = null;

    private MappingManagerConfiguration() {
        manager = new MappingManager(ConnectionConfiguration.getSession());
        questionMapper = manager.mapper(Question.class);
        sentenceMapper = manager.mapper(Sentence.class);
        sourceMapper = manager.mapper(Source.class);
        tokenMapper = manager.mapper(Token.class);
        w2VTokenMapper = manager.mapper(W2VToken.class);
        uniqueWordMapper = manager.mapper(UniqueWord.class);
    }

    public static MappingManager getMappingManager() {
        if(manager == null)
            mappingManagerConfiguration = new MappingManagerConfiguration();

        return manager;
    }

    public Mapper<Question> getQuestionMapper() {
        return questionMapper;
    }

    public Mapper<Sentence> getSentenceMapper() {
        return sentenceMapper;
    }

    public Mapper<Source> getSourceMapper() {
        return sourceMapper;
    }

    public Mapper<Token> getTokenMapper() {
        return tokenMapper;
    }

    public Mapper<W2VToken> getW2VTokenMapper() {
        return w2VTokenMapper;
    }

    public Mapper<UniqueWord> getUniqueWordMapper() {
        return uniqueWordMapper;
    }
}
