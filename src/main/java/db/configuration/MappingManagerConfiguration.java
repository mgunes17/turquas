package db.configuration;

import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import model.*;

/**
 * Created by mustafa on 13.05.2017.
 */
public class MappingManagerConfiguration {
    private static MappingManager manager = null;
    private static Mapper<Question> questionMapper;
    private static Mapper<Sentence> sentenceMapper;
    private static Mapper<Source> sourceMapper;
    private static Mapper<Token> tokenMapper;
    private static Mapper<W2VToken> w2VTokenMapper;
    private static Mapper<UniqueWord> uniqueWordMapper;
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

    public static Mapper<Question> getQuestionMapper() {
        return questionMapper;
    }

    public static Mapper<Sentence> getSentenceMapper() {
        return sentenceMapper;
    }

    public static Mapper<Source> getSourceMapper() {
        return sourceMapper;
    }

    public static Mapper<Token> getTokenMapper() {
        return tokenMapper;
    }

    public static Mapper<W2VToken> getW2VTokenMapper() {
        return w2VTokenMapper;
    }

    public static Mapper<UniqueWord> getUniqueWordMapper() {
        return uniqueWordMapper;
    }
}
