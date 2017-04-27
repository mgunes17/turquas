package db.dao;

import com.datastax.driver.core.*;
import db.configuration.ModelVariables;
import model.Sentence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by ercan on 09.04.2017.
 */
public class SentenceDAO {
    private final static Logger logger = LoggerFactory.getLogger(SentenceDAO.class);
    private String keyspace;
    private String tableName;
    private Session session;
    private PreparedStatement preparedStatement;

    public SentenceDAO(){
        this.keyspace = ModelVariables.KEYSPACE;
        this.tableName = ModelVariables.SENTENCE_TABLE_NAME;
        session = createSession();
    }

    public void insert(Sentence sentence){
        try{
            BoundStatement bound = preparedStatement.bind(sentence.getOriginalSentence(), sentence.getSourceName(),
                    sentence.getQuestions(), sentence.getStemmedWordsList(),
                    sentence.getTags(), sentence.getTokenList());
            session.execute(bound);
            logger.info("SentenceDAO insert başarıyla tamamlandı.");
        } catch(Exception ex){
            logger.warn("SentenceDAO insert hata verdi.");
        }
    }

    public void update(Sentence sentence){
        try{
            BoundStatement bound = preparedStatement.bind(sentence.getSourceName(), sentence.getQuestions(),
                    sentence.getStemmedWordsList(), sentence.getTags(), sentence.getTokenList());
            session.execute(bound);
            logger.info("SentenceDAO update başarıyla tamamlandı.");
        } catch(Exception ex){
            logger.warn("SentenceDAO update hata verdi.");
        }
    }

    public void delete(Sentence sentence){
        try{
            BoundStatement bound = preparedStatement.bind(sentence.getOriginalSentence());
            session.execute(bound);
            logger.info("SentenceDAO delete başarıyla tamamlandı.");
        } catch(Exception ex){
            logger.warn("SentenceDAO delete hata verdi.");
        }
    }

    public void insertBatch(List<Sentence> sentenceList){
        try{
            BatchStatement batch = new BatchStatement();
            prepareForInsert();

            for(Sentence sentence: sentenceList){
                BoundStatement bound = preparedStatement.bind(sentence.getOriginalSentence(), sentence.getSourceName(),
                        sentence.getQuestions(), sentence.getStemmedWordsList(), sentence.getTags(),
                        sentence.getTokenList());
                batch.add(bound);
            }

            session.execute(batch);
            logger.info("SentenceDAO insertBatch başarıyla tamamlandı.");
        } catch(Exception ex){
            logger.warn("SentenceDAO insertBatch hata verdi.");
        }

    }

    public void prepareForInsert(){
        preparedStatement = session.prepare(
                "INSERT INTO " + tableName + " (original_sentence, source_name, questions, " +
                        "stemmed_words_list, tags, token_list) values (?, ?, ?, ?, ?, ?)");
    }

    public void prepareForUpdate(){
        preparedStatement = session.prepare("UPDATE " + tableName +" " +
                "SET source_name=? questions= ?, stemmed_words_list=?, tags=?, token_list=?" +
                "WHERE original_sentence=?");
    }

    public void prepareForDelete(){
        preparedStatement = session.prepare(
                "DELETE FROM " + tableName + " WHERE original_sentence=?");
    }

    private Session createSession(){
        Cluster.Builder clusterBuilder = Cluster.builder();
        Cluster cluster = clusterBuilder.addContactPoint("127.0.0.1").build();

        return cluster.connect(keyspace);
    }

}
