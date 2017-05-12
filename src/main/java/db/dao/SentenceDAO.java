package db.dao;

import com.datastax.driver.core.*;
import db.configuration.ConnectionConfiguration;
import db.configuration.ModelVariables;
import model.Sentence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
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
    }

    public List<Sentence> readForGenerateQuestions(int count) {
        List<Sentence> sentenceList = new ArrayList<Sentence>();
        String query = "SELECT source_name, original_sentence, questions FROM sentence;";
        session = ConnectionConfiguration.getCLuster().connect("turquas");
        ResultSet result = session.execute(query);

        for(Row row: result) {
            sentenceList.add(new Sentence(row.getString(0), row.get(1, String.class), row.getSet(2, String.class)));
        }

        session.close();
        return sentenceList;
    }

    public List<Sentence> getAllSentences() {
        List<Sentence> sentenceList = new ArrayList<Sentence>();
        String query = "select original_sentence, questions, stemmed_words_list from sentence ;";
        session = ConnectionConfiguration.getCLuster().connect("turquas");
        ResultSet result = session.execute(query);

        for(Row row: result) {
            sentenceList.add(new Sentence(
                    row.get(0, String.class), row.getSet(1, String.class), row.getList(2, String.class)));
        }

        session.close();
        return sentenceList;
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

    public boolean updateQuestions(List<Sentence> sentenceList){
        try {
            session = ConnectionConfiguration.getCLuster().connect("turquas");
            //BatchStatement batch = new BatchStatement();
            prepareForQuestionUpdate();

            for(Sentence sentence: sentenceList) {
                BoundStatement bound = preparedStatement.bind(sentence.getQuestions(), sentence.getSourceName(), sentence.getOriginalSentence());
                session.execute(bound);
            }

            System.out.println(("SentenceDAO insertBatch başarıyla tamamlandı."));
            session.close();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public void delete(Sentence sentence){
        try{
            session = ConnectionConfiguration.getCLuster().connect("turquas");
            BoundStatement bound = preparedStatement.bind(sentence.getOriginalSentence());
            session.execute(bound);
            logger.info("SentenceDAO delete başarıyla tamamlandı.");
        } catch(Exception ex){
            logger.warn("SentenceDAO delete hata verdi.");
        } finally {
            session.close();
        }
    }

    public void insertBatch(List<Sentence> sentenceList){
        try{
            session = ConnectionConfiguration.getCLuster().connect("turquas");
            //BatchStatement batch = new BatchStatement();
            prepareForInsert();

            for(Sentence sentence: sentenceList){
                BoundStatement bound = preparedStatement.bind(sentence.getOriginalSentence(), sentence.getSourceName(),
                        sentence.getQuestions(), sentence.getStemmedWordsList(), sentence.getTags(),
                        sentence.getTokenList());
                session.execute(bound);
            }

            logger.info("SentenceDAO insertBatch başarıyla tamamlandı.");
        } catch(Exception ex){
            logger.warn("SentenceDAO insertBatch hata verdi.");
        } finally {
            session.close();
        }

    }

    private void prepareForInsert(){
        preparedStatement = session.prepare(
                "INSERT INTO " + tableName + " (original_sentence, source_name, questions, " +
                        "stemmed_words_list, tags, token_list) values (?, ?, ?, ?, ?, ?)");
    }

    private void prepareForUpdate(){
        preparedStatement = session.prepare("UPDATE " + tableName +" " +
                "SET source_name=? questions= ?, stemmed_words_list=?, tags=?, token_list=?" +
                "WHERE original_sentence=?");
    }

    private void prepareForQuestionUpdate(){
        preparedStatement = session.prepare("UPDATE " + tableName +" " +
                "SET questions= ?" +
                "WHERE source_name = ? and original_sentence=?");
    }

    private void prepareForDelete(){
        preparedStatement = session.prepare(
                "DELETE FROM " + tableName + " WHERE original_sentence=?");
    }

    /*private Session createSession(){
        Cluster.Builder clusterBuilder = Cluster.builder();
        Cluster cluster = clusterBuilder.addContactPoint("127.0.0.1").build();

        return cluster.connect(keyspace);
    }*/
}
